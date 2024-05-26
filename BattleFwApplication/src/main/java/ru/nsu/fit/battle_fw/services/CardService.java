package ru.nsu.fit.battle_fw.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.nsu.fit.battle_fw.database.model.*;
import ru.nsu.fit.battle_fw.database.repo.*;
import ru.nsu.fit.battle_fw.exceptions.*;
import ru.nsu.fit.battle_fw.requests.post.MoveCardRequest;
import ru.nsu.fit.battle_fw.requests.post.PutCardInCellRequest;
import ru.nsu.fit.battle_fw.requests.post.PutCollectorInCellRequest;

import java.util.List;


@Component
public class CardService {
    //Данные поля соответствуют названиям "Название таблицы" + R
    private final PersonRepo personR;
    private final CardRepo cardR;
    private final GameRepo gameR;
    private final LibraryRepo libR;
    private final LibraryCompRepo libCompR;
    private final HandRepo handR;
    private final HandCompRepo handCompR;
    private final CellRepo cellR;
    private final StatusRepo statusR;

    private static final Logger logger = LoggerFactory.getLogger(CardService.class);

    /**
     * Конструктор. Принимает объекты, позволяющие влиять на базу данных.
     * @param personR - Таблица с персонами
     * @param cardR - Таблица карт (описание карт и их id)
     * @param gameR - Таблица игр (id игр и их состояние)
     * @param libR - Таблица библиотеки (редкость)
     * @param libCompR - Таблица состава библиотек
     * @param handR - Таблица рук
     * @param handCompR - Таблица состава рук
     * @param cellR - Таблица клеток игр (полей)
     * @param statusR - Статус каждого игрока в контексте каждой из его игр
     */
    public CardService(PersonRepo personR, CardRepo cardR, GameRepo gameR, LibraryRepo libR, LibraryCompRepo libCompR,
                       HandRepo handR, HandCompRepo handCompR, CellRepo cellR, StatusRepo statusR) {
        this.personR = personR;
        this.cardR = cardR;
        this.gameR = gameR;
        this.libR = libR;
        this.libCompR = libCompR;
        this.handR = handR;
        this.handCompR = handCompR;
        this.cellR = cellR;
        this.statusR = statusR;
    }

    /**
     * Метод ставит карту в клетку.
     * Кидает ошибки при невозможности поставить карту.
     *
     * @param req - Это запрос на постановку карты
     * @throws NoBabosException - Если не хватает бабосов для постановки карты
     * @throws BadCellException - Если в данную клетку нельзя поставить карту
     * Ничего не возвращает
     */
    public void putCardInCell(PutCardInCellRequest req, String playerName)
            throws NoBabosException, BadCellException, NoHandCompException, NotYourTurnException, PutInFightException {
        Integer gameId = req.getGameId();
        Integer cardId = req.getCardId();
        Integer cellId = req.getCellId(); // cellId - это cellNum

        Card card = cardR.getReferenceById(cardId);
        Status status = statusR.getStatus(gameId, playerName);
        Game game = gameR.getReferenceById(gameId);

        if (!game.getName_turn().equals(playerName)) {
            throw new NotYourTurnException("Not your turn, dude...");
        }
        if(game.getIs_fight_phase()){
            throw new PutInFightException("Нельзя ставить карту в fight phase");
        }

        logger.info("Player: {}, Babos$: {}, Card ID {}, Card cost: {}",
                playerName, status.getBabos(), card.getId_card(), card.getCost());

        if (status.getBabos() < card.getCost()) { // Проверка на наличие бабосов
            throw new NoBabosException();
        } else {
            Hand hand = handR.getHand(gameId, playerName);
            hand.setCards_cnt(hand.getCards_cnt() - 1); // Удаление карты из руки

            List<HandComp> handCompList = handCompR.getHandCard(hand.getId_hand(), cardId); // Берём карту с нужным id

            if (handCompList.isEmpty()) {
                throw new NoHandCompException("No card in hand");
            }

            HandComp handComp = handCompList.get(0);

            Cell cell = cellR.getCell(gameId, cellId); // Постановка карты
            checkCell(cell, game, playerName, false);
            setCardInCell(cell, card, playerName);

            status.setBabos(status.getBabos() - card.getCost()); // Убавление бабосов

            handCompR.deleteById(handComp.getId_hand_card()); // Удаляем 1 экземпляр
            statusR.save(status);
            handR.save(hand);
            cellR.save(cell);
        }
    }

    /**
     * Установка сборщика бабосов.
     * Отдельным запросом т.к. это особенная карта. Её нет в руке, она открывается по мере открытия библиотек.
     * Данный метод обрабатывает запрос на постановку нового сборщика
     * @param req - Сам запрос
     * @throws NoBabosException - Кидается, если не хватает бабосов
     * @throws BadCellException - Кидается, если клетка некорректна
     * @throws CollectorsLimitException - Кидается, если достигнут лимит сборщиков, и ставить больше нельзя
     * Ничего не возвращает
     */
    public void putCollectorInCell(PutCollectorInCellRequest req, String playerName)
            throws NoBabosException, BadCellException,
            NotYourTurnException, CollectorsLimitException, PutInFightException {
        Integer gameId = req.getGameId();
        Integer cellId = req.getCellId(); // cellId - это cellNum
        Integer collectorId = 49;
        Integer collectorsLimit = 4;

        Card collector = cardR.getReferenceById(collectorId);
        Status status = statusR.getStatus(gameId, playerName);
        Game game = gameR.getReferenceById(gameId);

        if(game.getIs_fight_phase()){
            throw new PutInFightException("Нельзя ставить карту в fight phase");
        }

        if (status.getCollectors() >= collectorsLimit) {
            throw new CollectorsLimitException("Too many collectors");
        }

        if (!game.getName_turn().equals(playerName)) {
            throw new NotYourTurnException("Not your turn, dude...");
        }

        logger.info("Player: {}, Babos$: {}, Collector ID {}, Card cost: {}",
                playerName, status.getBabos(), collector.getId_card(), collector.getCost());

        Integer actualCollectorCost = collector.getCost() + (status.getCollectors() * 2);
        if (status.getBabos() < actualCollectorCost) { // Проверка на наличие бабосов
            throw new NoBabosException();
        } else {
            Cell cell = cellR.getCell(gameId, cellId); // Постановка карты
            checkCell(cell, game, playerName, true);
            setCardInCell(cell, collector, playerName);
            cell.setSickness(0); // У сборщиков нет болезни выстава

            status.setBabos(status.getBabos() - actualCollectorCost); // Убавление бабосов
            status.setCollectors(status.getCollectors() + 1); // Увеличиваем кол-во сборщиков

            cellR.save(cell);
            statusR.save(status);
        }
    }

    private void checkCell(Cell cell, Game game, String playerName, boolean isCollector) throws BadCellException {
        if (!isCollector) {
            if (cell.getId_card() != null || cell.getCell_num() <= 8 || cell.getCell_num() >= 57) {
                throw new BadCellException("Wrong cells, dude...");
            }

            if (game.getNon_reverse().equals(playerName)) {
                if (cell.getCell_num() <= 40 || cell.getCell_num() >= 57) {
                    throw new BadCellException("Not your cells, dude...");
                }
            } else {
                if (cell.getCell_num() <= 8 || cell.getCell_num() >= 25) {
                    throw new BadCellException("Not your cells, dude...");
                }
            }
        } else {
            if (cell.getId_card() != null || (cell.getCell_num() > 8 && cell.getCell_num() < 57)) {
                throw new BadCellException("Wrong cells, dude...");
            }

            if (game.getNon_reverse().equals(playerName)) {
                if (cell.getCell_num() <= 56 || cell.getCell_num() >= 65) {
                    throw new BadCellException("Not your cells, dude...");
                }
            } else {
                if (cell.getCell_num() <= 0 || cell.getCell_num() >= 9) {
                    throw new BadCellException("Not your cells, dude...");
                }
            }
        }
    }

    private void setCardInCell(Cell cell, Card card, String playerName) {
        cell.setId_card(card.getId_card());
        cell.setName_owner(playerName);
        cell.setSickness(1); // Установка болезни выхода
        cell.setCard_name(card.getName());
        cell.setAttack(card.getAttack());
        cell.setHealth(card.getHealth());
        cell.setCost(card.getCost());
        cell.setEvasion(card.getEvasion());
        cell.setAttack_speed(card.getAttack_speed());
        cell.setMovement_speed(card.getMovement_speed());
        cell.setRarity(card.getRarity());
        cell.setFraction(card.getFraction());
        cell.setRevenged(false);
        cell.setAttacked(false);
    }

    private void copyCell(Cell cell1, Cell cell2) {
        cell1.setId_card(cell2.getId_card());
        cell1.setName_owner(cell2.getName_owner());
        cell1.setSickness(cell2.getSickness()); // Установка болезни выхода
        cell1.setCard_name(cell2.getCard_name());
        cell1.setAttack(cell2.getAttack());
        cell1.setHealth(cell2.getHealth());
        cell1.setCost(cell2.getCost());
        cell1.setEvasion(cell2.getEvasion());
        cell1.setAttack_speed(cell2.getAttack_speed());
        cell1.setMovement_speed(cell2.getMovement_speed() - 1);
        cell1.setRarity(cell2.getRarity());
        cell1.setFraction(cell2.getFraction());
        cell1.setRevenged(cell2.isRevenged());
        cell1.setAttacked(cell2.isAttacked());
    }

    /**
     * Обработка запроса на перемещение карты
     * @param req - Сам запрос
     * Ничего не возвращает
     */
    public void moveCard(MoveCardRequest req, String playerName) throws BadCellException {
        Integer gameId = req.getGameId(); // Начальные данные
        Integer cellId1 = req.getCellId1();
        Integer cellId2 = req.getCellId2();
        Game game = gameR.getReferenceById(gameId);

        Cell cell1 = cellR.getCell(gameId, cellId1);
        Cell cell2 = cellR.getCell(gameId, cellId2);

        if(!areNeighbors(cellId1, cellId2)){
            throw new BadCellException("Не соседние клетки");
        }
        if (cell1.getCard_name() == null) {
            throw new BadCellException("В клетке нет карты");
        }
        if(game.getIs_fight_phase()){
            if (cell2.getCard_name() != null) {
                attack(cell1, cell2);
            }
            else {
                throw new BadCellException("Нельзя передвигаться в боевой фазе");
            }
        } else {
            if(cell1.getMovement_speed() > 0 && cell2.getCard_name() == null) {
                copyCell(cell1, cell2);
                Cell newCell = new Cell();
                copyCell(newCell, cell1);
            } else if (cell2.getCard_name() != null) {
                throw new BadCellException("Нельзя перемещать на клетку где что-то есть");
            } else if (cell1.getMovement_speed() <= 0){
                throw new BadCellException("У карты не хватает очков передвижения");
            }
        }



        cellR.save(cell1);
        cellR.save(cell2);
    }

    private void attack(Cell cell1, Cell cell2) {
        int newHp2 = cell2.getHealth() - cell1.getAttack();
        if(newHp2 <= 0){
            Cell newCell = new Cell();
            copyCell(newCell, cell2);
        } else {
            if(!cell2.isRevenged()){
                cell2.setRevenged(true);
                int newHp1 = cell1.getHealth() - cell2.getAttack();
                if(newHp1<=0){
                    Cell newCell = new Cell();
                    copyCell(newCell, cell1);
                }else {
                    cell1.setHealth(newHp1);
                }
            }
            cell2.setHealth(newHp2);
        }
        cell1.setAttacked(true);
    }

    public static boolean areNeighbors(int id1, int id2) {
        // Вычисляем индексы (i, j) для id1
        int i1 = id1 / 8;
        int j1 = id1 % 8;

        // Вычисляем индексы (i, j) для id2
        int i2 = id2 / 8;
        int j2 = id2 % 8;

        // Проверяем условия соседства
        if (i1 == i2 && Math.abs(j1 - j2) == 1) {
            return true;
        }
        if (j1 == j2 && Math.abs(i1 - i2) == 1) {
            return true;
        }

        return false;
    }
}
