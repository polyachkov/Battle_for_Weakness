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
            throws NoBabosException, BadCellException, NoHandCompException, NotYourTurnException {
        Integer gameId = req.getGameId();
        Integer cardId = req.getCardId();
        Integer cellId = req.getCellId(); // cellId - это cellNum

        Card card = cardR.getReferenceById(cardId);
        Status status = statusR.getStatus(gameId, playerName);
        Game game = gameR.getReferenceById(gameId);

        if (!game.getName_turn().equals(playerName)) {
            throw new NotYourTurnException("Not your turn, dude...");
        }

        logger.info("Player: {}, Babos$: {}, Card ID {}, Card cost: {}", playerName, status.getBabos(), card.getId_card(), card.getCost());

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
            checkCell(cell, game, playerName);
            setCardInCell(cell, card, playerName);

            status.setBabos(status.getBabos() - card.getCost()); // Убавление бабосов

            handCompR.deleteById(handComp.getId_hand_card()); // Удаляем 1 экземпляр
            statusR.save(status);
            handR.save(hand);
            cellR.save(cell);
        }
    }

    private void checkCell(Cell cell, Game game, String playerName) throws BadCellException {
        if (cell.getId_card() != null || cell.getCell_num() <= 8 || cell.getCell_num() >= 57) {
            throw new BadCellException();
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
            throws NoBabosException, BadCellException, CollectorsLimitException {
        Integer gameId = req.getGameId();
        Integer cellId = req.getCellId();

        if (cellId > 8 && cellId < 57) { // Сборщики нельзя ставить в обычные клетки
            throw new BadCellException();
        } else {
            Card collector = cardR.getReferenceById(49); // 49 - id карты сборщика

            Status status = statusR.getStatus(gameId, playerName);

            if (status.getBabos() < collector.getCost()) { // Проверка на наличие денег
                throw new NoBabosException();
            } else if (status.getCollectors() >= 4) { // Проверка на кол-во сейчас стоящих сборщиков
                throw new CollectorsLimitException();
            } else {
                Cell cell = cellR.getCell(gameId, cellId);
                cell.setId_card(49);
                cell.setName_owner(playerName);
                cell.setSickness(0); // У сборщиков нет болезни выстава

                status.setCollectors(status.getCollectors() + 1); // Увеличиваем кол-во сборщиков

                cellR.save(cell);
                statusR.save(status);
            }
        }
    }

    /**
     * Обработка запроса на перемещение карты
     * @param req - Сам запрос
     * Ничего не возвращает
     */
    public void moveCard(MoveCardRequest req, String playerName) {
        Integer gameId = req.getGameId(); // Начальные данные
        Integer cellId1 = req.getCellId1();
        Integer cellId2 = req.getCellId2();

        Cell cell1 = cellR.getCell(gameId, cellId1);
        Cell cell2 = cellR.getCell(gameId, cellId2);
        cell2.setId_card(cell1.getId_card()); // Установка карты
        cell2.setName_owner(playerName);
        cell1.setId_card(null); // Очищение предыдущей клетки
        cell1.setName_owner(null);

        cellR.save(cell1);
        cellR.save(cell2);
    }
}
