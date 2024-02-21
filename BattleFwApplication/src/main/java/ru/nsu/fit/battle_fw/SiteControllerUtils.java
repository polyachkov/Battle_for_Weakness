// GameService.java
package ru.nsu.fit.battle_fw;

import org.springframework.stereotype.Component;
import ru.nsu.fit.battle_fw.database.model.*;
import ru.nsu.fit.battle_fw.database.repo.*;
import ru.nsu.fit.battle_fw.exceptions.BadCellException;
import ru.nsu.fit.battle_fw.exceptions.CollectorsLimitException;
import ru.nsu.fit.battle_fw.exceptions.NoBabosException;
import ru.nsu.fit.battle_fw.exceptions.PersonAlreadyExistsException;
import ru.nsu.fit.battle_fw.requests.get.GetGameRequest;
import ru.nsu.fit.battle_fw.requests.post.*;

import java.util.*;

/**
 * Данный класс содержит методы для обработки GET И POST запросов
 */
@Component
public class SiteControllerUtils {

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
    public SiteControllerUtils(PersonRepo personR, CardRepo cardR, GameRepo gameR, LibraryRepo libR, LibraryCompRepo libCompR,
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
     * Метод инициализирующий игру и библиотеки
     * Используется в запросе /init
     * @param req Принимает объект POST запроса InitGameRequest
     * Ничего не возвращает
     */
    public void initializeGameAndLibraries(InitGameRequest req) {
        String fraction1 = req.getFraction1(); //Получение фракций
        String fraction2 = req.getFraction2();
        Integer player1 = req.getPlayer1(); //Получение игрков
        Integer player2 = req.getPlayer2();

        Game game = initializeGame(player1, player2); //Создание игры

        initializeStatus(game.getId_game(), player1, game.getId_turn()); //Инициализация статусов
        initializeStatus(game.getId_game(), player2, game.getId_turn());

        initializeLibraries(fraction1, game, player1); //Инициализация библиотек
        initializeLibraries(fraction2, game, player2);
        createHands(game, player1, player2); //Создание рук для пользователей
        createField(game.getId_game()); //Создание пустого поля
    }

    /**
     * Инициализация игры, а именно
     * 1) Создаётся объект Game
     * 2) Заполняется объект Game (id игроков, is_ended, также выбирается кто будет ходить первым
     * 3) Объект сохраняется как запись в базу данных
     * Принимает параметры:
     * @param player1  Id игрока 1
     * @param player2  Id игрока 2
     * Возвращает:
     * @return Объект класса Game
     */
    private Game initializeGame(Integer player1, Integer player2) {
        Game game = new Game();
        game.setId_player1(player1); // Задание Id игроков
        game.setId_player2(player2);
        game.setIs_ended(false); // Показать что игра не окончена
        Random isFirstPlayer = new Random(); // Случайным образом выбирается тот, кто будет ходить первым
        if (isFirstPlayer.nextBoolean()) {
            game.setId_turn(player1);
        } else {
            game.setId_turn(player2);
        }
        gameR.save(game); // Сохранение записи в базу данных
        return game;
    }

    /**
     * Инициализация статуса игрока
     * Устанавливает HP игрока и начальное кол-во бабосов
     * Записывает информацию об этом в базу данных
     * @param gameId - Id игры, в которой участвует игрок
     * @param playerId - Id игрока, для которого небходимо задать запись
     * @param turnId - Id игрока, который ходит первым
     */
    private void initializeStatus(Integer gameId, Integer playerId, Integer turnId) {
        Status status = new Status(); // Создание экземпляра и заполнение данных
        status.setId_player(playerId);
        status.setId_game(gameId);
        status.setCollectors(0);
        status.setHealth(25); // Начальное здоровье
        if (Objects.equals(turnId, playerId)) { // Если икрок ходит первым, то его кол-во денег 2, иначе 1
            status.setBabos(2);
        }  else  {
            status.setBabos(1);
        }
        statusR.save(status); // Сохранение информации в базу данных
    }

    /**
     * Создание библиотек. Используется функция createLib
     * @param fraction - имя фракции
     * @param game - Объект игры
     * @param playerId - Id игрока
     * Ничего не возвращает
     */
    private void initializeLibraries(String fraction, Game game, Integer playerId) {
        String[] rarities = new String[]{"common", "uncommon", "rare", "epic", "legendary"}; //Задаются редкости
        for (String rarity : rarities) {
            createLib(rarity, fraction, game, playerId); // Создание библиотек
        }
    }

    /**
     * Создание руки
     * @param game - Объект игры
     * @param player1 - Id второго игрока
     * @param player2 - Id второго игрока
     * Ничего не возвращает
     */
    private void createHands(Game game, Integer player1, Integer player2) {
        createHand(libR.getLibId(player1, game.getId_game(), "common"), game.getId_game(), player1);
        createHand(libR.getLibId(player2, game.getId_game(), "common"), game.getId_game(), player2);
    }

    /**
     * Создание 1й библиотеки определённой редкости
     * @param rarity - редкость библиотеки
     * @param fraction - фракция игрока
     * @param game - Объект игры
     * @param playerId - Id игрока
     * Ничего не возвращает
     */
    private void createLib(String rarity, String fraction, Game game, Integer playerId) {
        List<Card> listC = cardR.getListOfCard(rarity, fraction); // Получение списка карт из базы данных
        List<Integer> listId = new ArrayList<Integer>(); // Создание библиотеки
        for (Card c : listC) {
            for (int i = c.getNumber_of_cards(); i > 0; i--) {
                listId.add(c.getId_card());
            }
        }
        Collections.shuffle(listId); // Перемешивание библиотеки

        Library lib = new Library(); //Заполнение библиотеки
        lib.setCards_cnt(listId.size());
        lib.setGame_id(game.getId_game());
        lib.setPlayer_id(playerId);
        lib.setLocked(!rarity.equals("common")); // Все библиотеки, кроме библиотеки редкости common, заблокированы
        lib.setRarity(rarity);
        libR.save(lib); // Сохранение библиотеки в базу данных

        for (Integer c : listId) { // Сохранение состава библиотеки в базу данных
            LibraryComp libComp = new LibraryComp();
            libComp.setId_card(c);
            libComp.setId_library(lib.getId_library());
            libCompR.save(libComp);
        }
    }

    /**
     * Создать одну руку для определённого игрока
     * @param lib_id - id библиотеки из которой берём руку
     * @param id_game - id игры
     * @param id_player - id игрока
     * Ничего не возвращает
     */
    private void createHand(Integer lib_id, Integer id_game, Integer id_player) {
        Hand hand = new Hand(); // Создание руки и заполнение полей
        hand.setId_game(id_game);
        hand.setCards_cnt(0);
        hand.setId_player(id_player);
        handR.save(hand);
        for (int i = 0; i < 7; i++) { // Взятие карт из библиотеки
            getCardToHand(lib_id, hand.getId_hand());
            hand.setCards_cnt(hand.getCards_cnt() + 1);
        }
    }

    /**
     * Взять карту из библиотеки в руку
     * ВНИМАНИЕ: метод не увеличивает кол-во карт в руке.
     * @param lib_id - id библиотеки откуда брать
     * @param id_hand - id руки куда класть
     * Ничего не возвращает
     */
    private void getCardToHand(Integer lib_id, Integer id_hand) {
        LibraryComp libComp = libCompR.getMinCard(lib_id); // Получить верхнюю карту библиотеки
        HandComp handComp = new HandComp(); // Создать запись о карте в руке
        handComp.setId_hand(id_hand);
        handComp.setId_card(libComp.getId_card());
        libCompR.deleteById(libComp.getId_card_lib()); // Удалить карту из библиотеки
        Library lib = libR.getReferenceById(lib_id);
        lib.setCards_cnt(lib.getCards_cnt() - 1); // Изменение кол-ва карт в билиотеке
        libR.save(lib);
        handCompR.save(handComp);
    }

    /**
     * Создать поле для игры
     * @param gameId - id игры
     * Ничего не возвращает
     */
    private void createField(Integer gameId) {
        for (int i = 1; i <= 56; i++) { // 56 - кол-во ячеек поля
            Cell cell = new Cell(); // Для каждой ячейки создаём запись
            cell.setCell_num(i);
            cell.setId_card(null); // Изначально в ячейке пусто
            cell.setId_game(gameId);
            cellR.save(cell); // сохраняем
        }
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
    public void putCardInCell(PutCardInCellRequest req)
            throws NoBabosException, BadCellException {
        Integer gameId = req.getGameId();
        Integer playerId = req.getPlayerId();
        Integer cardId = req.getCardId();
        Integer cellId = req.getCellId();

        if (cellId <= 8 || cellId >= 49) { // Если клетка постановки только для сборщиков
            throw new BadCellException();
        } else {  // Если нет, то ставим карту
            Card card = cardR.getReferenceById(cardId);
            Status status = statusR.getStatus(gameId, playerId);

            if (status.getBabos() < card.getCost()) { // Проверка на наличие бабосов
                throw new NoBabosException();
            } else {
                Hand hand = handR.getHand(gameId, playerId);
                hand.setCards_cnt(hand.getCards_cnt() - 1); // Удаление карты из руки

                List<HandComp> handCompList = handCompR.getHandCard(hand.getId_hand(), cardId); // Берём карту с нужным id

                HandComp handComp = handCompList.get(0);

                Cell cell = cellR.getCell(gameId, cellId); // Постановка карты
                cell.setId_card(cardId);
                cell.setId_owner(playerId);
                cell.setSickness(1); // Установка болезни выхода

                status.setBabos(status.getBabos() - card.getCost()); // Убавление бабосов

                handCompR.deleteById(handComp.getId_hand_card()); // Удаляем 1 экземпляр
                statusR.save(status);
                handR.save(hand);
                cellR.save(cell);
            }
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
    public void putCollectorInCell(PutCollectorInCellRequest req)
            throws NoBabosException, BadCellException, CollectorsLimitException {
        Integer gameId = req.getGameId();
        Integer playerId = req.getPlayerId();
        Integer cellId = req.getCellId();

        if (cellId > 8 && cellId < 49) { // Сборщики нельзя ставить в обычные клетки
            throw new BadCellException();
        } else {
            Card collector = cardR.getReferenceById(49); // 49 - id карты сборщика

            Status status = statusR.getStatus(gameId, playerId);

            if (status.getBabos() < collector.getCost()) { // Проверка на наличие денег
                throw new NoBabosException();
            } else if (status.getCollectors() >= 4) { // Проверка на кол-во сейчас стоящих сборщиков
                throw new CollectorsLimitException();
            } else {
                Cell cell = cellR.getCell(gameId, cellId);
                cell.setId_card(49);
                cell.setId_owner(playerId);
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
    public void moveCard(MoveCardRequest req) {
        Integer gameId = req.getGameId(); // Начальные данные
        Integer playerId = req.getPlayerId();
        Integer cellId1 = req.getCellId1();
        Integer cellId2 = req.getCellId2();

        Cell cell1 = cellR.getCell(gameId, cellId1);
        Cell cell2 = cellR.getCell(gameId, cellId2);
        cell2.setId_card(cell1.getId_card()); // Установка карты
        cell2.setId_owner(playerId);
        cell1.setId_card(null); // Очищение предыдущей клетки
        cell1.setId_owner(null);

        cellR.save(cell1);
        cellR.save(cell2);
    }

    /**
     * Передача хода другому игроку
     * @param req - Сам запрос
     * Ничего не возвращает
     */
    public void nextTurn(NextTurnRequest req) {
        Integer nextTurnId = req.getNextTurnId(); // Id игрока, который ходит следующим
        Integer gameId = req.getGameId(); // id игры
        String rarity = req.getRarity(); // редкость колоды, из которой следующий игрок берёт карту

        Game game = gameR.getReferenceById(gameId);
        game.setId_turn(nextTurnId); // Задаём ход

        Status status = statusR.getStatus(gameId, nextTurnId);
        status.setBabos(status.getBabos() + 2); // Увеличиваем кол-во денег следующего игрока

        Hand hand = handR.getHand(gameId, nextTurnId); // Даём в руку карту указанной редкости
        hand.setCards_cnt(hand.getCards_cnt() + 1);

        List<Cell> cells = cellR.getCells(gameId); // Устанавливаем болезнь выхода всех карт в 0
        for (Cell c : cells) {
            c.setSickness(0);
        }

        Integer library_id = libR.getLibId(nextTurnId, gameId, rarity);
        getCardToHand(library_id, hand.getId_hand()); // Даём карту в руку

        gameR.save(game);
        statusR.save(status);
        handR.save(hand);
        cellR.saveAll(cells);
    }

    /**
     * Сохранение нового пользователя в базе данных
     * @param person - Объект персоны
     * @throws PersonAlreadyExistsException - Персона уже существует, поэтому нельзя добавить
     * Ничего не возвращает
     */
    public void addPerson(Person person) throws PersonAlreadyExistsException {
        Person reference = personR.findByName(person.getName()); // Проверка на наличие в базе
        if (reference == null) {
            personR.save(person);
        } else {
            throw new PersonAlreadyExistsException("p");
        }
    }

    /**
     * Метод получает id игры по двум id игроков
     * @param req - Сам запрос, содержащий id игроков
     * @return - возвращает контейнер Optional, содержащий Объект игры
     */
    public Optional<Game> getGameByPlayers(GetGameRequest req) {
        Integer playerId1 = req.getPlayerId1();
        Integer playerId2 = req.getPlayerId2();

        Game game = gameR.getGame(playerId1, playerId2); // Используется sql запрос

        return Optional.ofNullable(game);
    }

    /**
     * Получает объект игры по её Id
     * @param gameId - id игры
     * @return - возвращает контейнер Optional, содержащий Объект игры
     */
    public Optional<Game> getGameById(Integer gameId) {
        return gameR.findById(gameId);
    }

    /**
     * Получает список ячеек игры по её id
     * @param gameId - id игры
     * @return - возвращает контейнер Optional, содержащий список ячеек
     */
    public Optional<List<Cell>> getFieldByGame(Integer gameId) {
        return Optional.of(cellR.getCells(gameId));

    }

}
