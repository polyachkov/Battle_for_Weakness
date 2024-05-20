// GameService.java
package ru.nsu.fit.battle_fw.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.nsu.fit.battle_fw.database.model.*;
import ru.nsu.fit.battle_fw.database.repo.*;
import ru.nsu.fit.battle_fw.exceptions.*;
import ru.nsu.fit.battle_fw.requests.get.GetGameRequest;
import ru.nsu.fit.battle_fw.requests.post.*;
import ru.nsu.fit.battle_fw.responses.AllGamesResponse;
import ru.nsu.fit.battle_fw.responses.CellsResponse;
import ru.nsu.fit.battle_fw.responses.HandResponse;
import ru.nsu.fit.battle_fw.responses.OppHandResponse;
import ru.nsu.fit.battle_fw.responses.info.CellInfo;
import ru.nsu.fit.battle_fw.responses.info.GameInfo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Данный класс содержит методы для обработки GET И POST запросов
 */
@Component
public class GameService {

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
    private final InviteRepo inviteR;
    private final UserRepo userR;

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
     * @param inviteR - Приглашения в игры
     */
    public GameService(PersonRepo personR, CardRepo cardR, GameRepo gameR, LibraryRepo libR, LibraryCompRepo libCompR,
                       HandRepo handR, HandCompRepo handCompR, CellRepo cellR, StatusRepo statusR, InviteRepo inviteR, UserRepo userR) {
        this.personR = personR;
        this.cardR = cardR;
        this.gameR = gameR;
        this.libR = libR;
        this.libCompR = libCompR;
        this.handR = handR;
        this.handCompR = handCompR;
        this.cellR = cellR;
        this.statusR = statusR;
        this.inviteR = inviteR;
        this.userR = userR;
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
        String player1 = req.getPlayer1(); //Получение игрков
        String player2 = req.getPlayer2();

        Game game = initializeGame(player1, player2); //Создание игры

        initializeStatus(game.getId_game(), player1, game.getName_turn()); //Инициализация статусов
        initializeStatus(game.getId_game(), player2, game.getName_turn());

        initializeLibraries(fraction1, game, player1); //Инициализация библиотек
        initializeLibraries(fraction2, game, player2);
        createHands(game, player1, player2); //Создание рук для пользователей
        createField(game.getId_game()); //Создание пустого поля
    }

    /**
     * Инициализация игры, а именно
     * 1) Создаётся объект Game
     * 2) Заполняется объект Game (Name игроков, is_ended, также выбирается кто будет ходить первым
     * 3) Объект сохраняется как запись в базу данных
     * Принимает параметры:
     * @param player1  Name игрока 1
     * @param player2  Name игрока 2
     * Возвращает:
     * @return Объект класса Game
     */
    private Game initializeGame(String player1, String player2) {
        Game game = new Game();
        game.setName_player1(player1); // Задание Id игроков
        game.setName_player2(player2);
        game.setIs_ended(false); // Показать что игра не окончена
        Random isFirstPlayer = new Random(); // Случайным образом выбирается тот, кто будет ходить первым
        if (isFirstPlayer.nextBoolean()) {
            game.setName_turn(player1);
            game.setNon_reverse(player1);
        } else {
            game.setName_turn(player2);
            game.setNon_reverse(player2);
        }
        gameR.save(game); // Сохранение записи в базу данных
        return game;
    }

    /**
     * Инициализация статуса игрока
     * Устанавливает HP игрока и начальное кол-во бабосов
     * Записывает информацию об этом в базу данных
     * @param gameId - Id игры, в которой участвует игрок
     * @param playerName - Name игрока, для которого небходимо задать запись
     * @param turnName - Name игрока, который ходит первым
     */
    private void initializeStatus(Integer gameId, String playerName, String turnName) {
        Status status = new Status(); // Создание экземпляра и заполнение данных
        status.setName_player(playerName);
        status.setId_game(gameId);
        status.setCollectors(0);
        status.setHealth(25); // Начальное здоровье
        if (Objects.equals(turnName, playerName)) { // Если икрок ходит первым, то его кол-во денег 2, иначе 1
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
     * @param playerName - Name игрока
     * Ничего не возвращает
     */
    private void initializeLibraries(String fraction, Game game, String playerName) {
        String[] rarities = new String[]{"common", "uncommon", "rare", "epic", "legendary"}; //Задаются редкости
        for (String rarity : rarities) {
            createLib(rarity, fraction, game, playerName); // Создание библиотек
        }
    }

    /**
     * Создание руки
     * @param game - Объект игры
     * @param player1 - name первого игрока
     * @param player2 - name второго игрока
     * Ничего не возвращает
     */
    private void createHands(Game game, String player1, String player2) {
        createHand(libR.getLibId(player1, game.getId_game(), "common"), game.getId_game(), player1);
        createHand(libR.getLibId(player2, game.getId_game(), "common"), game.getId_game(), player2);
    }

    /**
     * Создание 1й библиотеки определённой редкости
     * @param rarity - редкость библиотеки
     * @param fraction - фракция игрока
     * @param game - Объект игры
     * @param playerName - name игрока
     * Ничего не возвращает
     */
    private void createLib(String rarity, String fraction, Game game, String playerName) {
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
        lib.setPlayer_id(playerName);
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
     * @param player_name - name игрока
     * Ничего не возвращает
     */
    private void createHand(Integer lib_id, Integer id_game, String player_name) {
        Hand hand = new Hand(); // Создание руки и заполнение полей
        hand.setId_game(id_game);
        hand.setCards_cnt(0);
        hand.setName_player(player_name);
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
        for (int i = 1; i <= 64; i++) { // 64 - кол-во ячеек поля
            Cell cell = new Cell(); // Для каждой ячейки создаём запись
            cell.setCell_num(i);
            cell.setId_card(null); // Изначально в ячейке пусто
            cell.setId_game(gameId);
            cellR.save(cell); // сохраняем
        }
    }

    /**
     * Передача хода другому игроку
     * @param req - Сам запрос
     * @param nextTurnName - name игрока, который ходит следующим
     * Ничего не возвращает
     */
    public void nextTurn(NextTurnRequest req, String nextTurnName) {
        Integer gameId = req.getGameId(); // id игры
        String rarity = req.getRarity(); // редкость колоды, из которой следующий игрок берёт карту

        Game game = gameR.getReferenceById(gameId);
        game.setName_turn(nextTurnName); // Задаём ход

        Status status = statusR.getStatus(gameId, nextTurnName);
        status.setBabos(status.getBabos() + 2); // Увеличиваем кол-во денег следующего игрока

        Hand hand = handR.getHand(gameId, nextTurnName); // Даём в руку карту указанной редкости
        hand.setCards_cnt(hand.getCards_cnt() + 1);

        List<Cell> cells = cellR.getCells(gameId); // Устанавливаем болезнь выхода всех карт в 0
        for (Cell c : cells) {
            c.setSickness(0);
        }

        Integer library_id = libR.getLibId(nextTurnName, gameId, rarity);
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
     * Устаревший метод, более не нужен
     */
    @Deprecated
    public void addPerson(Person person) throws PersonAlreadyExistsException {
        Person reference = personR.findByName(person.getName()); // Проверка на наличие в базе
        if (reference == null) {
            personR.save(person);
        } else {
            throw new PersonAlreadyExistsException("p");
        }
    }

    /**
     * Метод получает id игры по двум name игроков
     * @param req - Сам запрос, содержащий name игрока
     * @param playerName1 - имя первого игрока (кинувшего запрос)
     * @return - возвращает контейнер Optional, содержащий Объект игры
     */
    public Optional<Game> getGameByPlayers(GetGameRequest req, String playerName1) {
        String playerName2 = req.getPlayerName2();

        Game game = gameR.getGame(playerName1, playerName2); // Используется sql запрос

        return Optional.ofNullable(game);
    }

    /**
     * Получает объект игры по её Id
     * @param gameId - id игры
     * @return - возвращает контейнер Optional, содержащий Объект игры
     */
    public ResponseEntity<?> getGameById(Integer gameId) {
        return ResponseEntity.ok(gameR.findById(gameId));
    }

    /**
     * Получает список ячеек игры по её id
     * @param gameId - id игры
     * @return - возвращает контейнер Optional, содержащий список ячеек
     */
    public ResponseEntity<?> getFieldByGame(Integer gameId) {
        List<Cell> cells = cellR.getCells(gameId);

        List<CellInfo> cellInfoList = cells.stream()
                .map(cell -> new CellInfo(
                        cell.getCell_num(),
                        cell.getId_card(),
                        cell.getName_owner(),
                        cell.getSickness())
                )
                .collect(Collectors.toList());

        CellsResponse cellsResponse = new CellsResponse(cellInfoList);

        return ResponseEntity.ok(cellsResponse);
    }

    /**
     * Создаёт invite по name приглашающего и name приглашённого
     * Игнорит если уже есть такой приглос или игра
     * @param req - содержит имя приглашённого
     * @param inviter_name - имя приглашающего
     */
    public void createInvite(InviteCreateRequest req, String inviter_name) throws EqualsPlayersException, JustNoPersonException {
        String invited_name = req.getInvited_name();
        String inviter_fraction = req.getInviter_fraction();

        Invite inv = new Invite(inviter_name, invited_name, inviter_fraction);

        if(invited_name.equals(inviter_name)){
            throw new EqualsPlayersException("Нельзя пригласить самого себя в игру");
        }

        boolean exists1 = userR.getAllUsers().stream().anyMatch(obj -> obj.getUsername().equals(invited_name));
        boolean exists2 = userR.getAllUsers().stream().anyMatch(obj -> obj.getUsername().equals(inviter_name));


        if(!(exists1 && exists2)){
            throw new JustNoPersonException("Одного из игрков (приглашённого или приглашающего) не существует");
        }

        Invite check = inviteR.getInvite(inviter_name, invited_name);
        Game g1 = gameR.getGame(inviter_name, invited_name);
        Game g2 = gameR.getGame(invited_name, inviter_name);
        if(check == null && g1 == null && g2 == null) {
            inviteR.save(inv);
        }
    }

    /**\
     * Удаляет invite из таблицы.
     * Требует inviter_name и invited_name
     * Удаление происходит когда пользователь invited отклоняет запрос
     * @param req - содержит inviter_name
     * @param invited_name - имя приглашённого
     */
    public void deleteInvite(InviteDeleteRequest req, String invited_name) {
        String inviter_name = req.getInviter_name();

        Invite toDel = inviteR.getInvite(inviter_name, invited_name);
        if(toDel != null) {
            inviteR.delete(toDel);
        }
    }

    /**
     * Обработка приёма запроса (удаление его из БД и создание игры)
     * @param req - содержат name и fraction второго игрока
     * @throws InviteIsNullException - в случае если такого инвайта нет
     */
    public void acceptInvite(InviteAcceptRequest req, String invited_name) throws InviteIsNullException {
        String inviter_name = req.getInviter_name();
        String inviter_fraction;
        String invited_fraction = req.getInvited_fraction();
        Invite toDel = inviteR.getInvite(inviter_name, invited_name);
        if(toDel != null) {
            inviter_fraction = toDel.getInviter_fraction();
            inviteR.delete(toDel);
        }
        else{
            throw new InviteIsNullException("Invite does not exist");
        }

        InitGameRequest req2 = new InitGameRequest(inviter_fraction, invited_fraction, inviter_name, invited_name);
        System.out.println(inviter_fraction);
        System.out.println(invited_fraction);
        System.out.println(inviter_name);
        System.out.println(invited_name);
        initializeGameAndLibraries(req2);
    }

    public ResponseEntity<?> getAllGames(String nameOwner) {
        List<Game> games = gameR.getAllGames(nameOwner);

        List<GameInfo> gameInfoList = games.stream()
                .map(game -> new GameInfo(
                        game.getId_game(),
                        game.getName_player1(),
                        game.getName_player2())
                )
                .collect(Collectors.toList());

        AllGamesResponse gamesResponse = new AllGamesResponse(gameInfoList);
        return ResponseEntity.ok(gamesResponse);
    }

    public ResponseEntity<?> getCardsInHand(Integer id_game, String namePlayer) {
        Hand hand = handR.getHand(id_game, namePlayer);
        List<HandComp> handCompCards= handCompR.getCardsId(hand.getId_hand());

        List<Card> cards = new ArrayList<>();
        for (HandComp handComp : handCompCards) {
            Card card = cardR.getCardById(handComp.getId_card());
            cards.add(card);
        }

        HandResponse handResponse = new HandResponse(cards);
        return ResponseEntity.ok(handResponse);
    }

    public ResponseEntity<?> getOppHand(Integer id_game, String namePlayer) {
        Hand hand = handR.getOppHand(id_game, namePlayer);

        OppHandResponse oppHandResponse = new OppHandResponse(hand.getCards_cnt());
        return ResponseEntity.ok(oppHandResponse);
    }
}
