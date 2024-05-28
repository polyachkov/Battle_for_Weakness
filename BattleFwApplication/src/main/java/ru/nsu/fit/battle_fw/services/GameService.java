// GameService.java
package ru.nsu.fit.battle_fw.services;

import org.aspectj.weaver.ast.Not;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Component;
import ru.nsu.fit.battle_fw.database.model.*;
import ru.nsu.fit.battle_fw.database.repo.*;
import ru.nsu.fit.battle_fw.exceptions.*;
import ru.nsu.fit.battle_fw.requests.get.GetGameRequest;
import ru.nsu.fit.battle_fw.requests.post.*;
import ru.nsu.fit.battle_fw.responses.*;
import ru.nsu.fit.battle_fw.responses.info.CellInfo;
import ru.nsu.fit.battle_fw.responses.info.GameInfo;
import ru.nsu.fit.battle_fw.responses.info.LibraryInfo;
import ru.nsu.fit.battle_fw.responses.info.StatusInfo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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


    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

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
        game.setTurn_ended(false);
        Random isFirstPlayer = new Random(); // Случайным образом выбирается тот, кто будет ходить первым
        if (isFirstPlayer.nextBoolean()) {
            game.setName_turn(player1);
            game.setNon_reverse(player1);
        } else {
            game.setName_turn(player2);
            game.setNon_reverse(player2);
        }
        game.setIs_fight_phase(false);
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
        status.setHealth(10); // Начальное здоровье
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
        createHand(libR.getLib(player1, game.getId_game(), "common").getId_library(), game.getId_game(), player1);
        createHand(libR.getLib(player2, game.getId_game(), "common").getId_library(), game.getId_game(), player2);
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
     * @param turnName - name игрока, который ходит сейчас
     * Ничего не возвращает
     */
    public void nextTurn(NextTurnRequest req, String turnName)
            throws NotYourTurnException, WrongPhaseException, GameIsAlreadyEndedException {
        Integer gameId = req.getGameId(); // id игры

        Game game = gameR.getReferenceById(gameId);
        if (game.getIs_ended()) {
            throw new GameIsAlreadyEndedException("Game is ended");
        }
        if (game.getTurn_ended()) {
            logger.error("Please, choose a rarity...");
            throw new WrongPhaseException("Please, choose a rarity...");
        }
        if (!game.getName_turn().equals(turnName)) {
            throw new NotYourTurnException("You cannot pass a turn during an opponent's turn");
        }

        String nextTurnName = game.getOppName(turnName);
        game.setName_turn(nextTurnName); // Задаём ход
        game.setTurn_ended(true);
        game.setIs_fight_phase(false);
        gameR.save(game);
    }

    /**
     * Принятие хода от другого игрока
     * @param req - Сам запрос
     * @param turnName - name игрока, который ходит сейчас
     * Ничего не возвращает
     */
    public void takeTurn(TakeTurnRequest req, String turnName)
            throws NotYourTurnException, LockedLibraryException, GameIsAlreadyEndedException {
        Integer gameId = req.getGameId(); // id игры
        String rarity = req.getRarity(); // редкость колоды, из которой следующий игрок берёт карту

        Game game = gameR.getReferenceById(gameId);
        if (game.getIs_ended()) {
            throw new GameIsAlreadyEndedException("Game is ended");
        }
        if (!game.getName_turn().equals(turnName)) {
            throw new NotYourTurnException("You cannot take a turn during an opponent's turn");
        }

        Library library = libR.getLib(turnName, gameId, rarity);
        if (library.getLocked()) {
            throw new LockedLibraryException("You cannot take a card from a locked library");
        }

        game.setTurn_ended(false);

        Status status = statusR.getStatus(gameId, turnName);
        status.setBabos(status.getBabos() + 2 + status.getCollectors()); // Увеличиваем кол-во денег следующего игрока


        Hand hand = handR.getHand(gameId, turnName); // Даём в руку карту указанной редкости
        if(!(hand.getCards_cnt() >= 10)){
            hand.setCards_cnt(hand.getCards_cnt() + 1);
            getCardToHand(library.getId_library(), hand.getId_hand()); // Даём карту в руку
        }


        List<Cell> cells = cellR.getCells(gameId);
        for (Cell c : cells) {
            if(c.getCard_name() != null){
                c.setSickness(0); // Устанавливаем болезнь выхода всех карт в 0
                c.setRevenged(false); // Все карты не отвечали на атаку
                c.setMovement_speed(cardR.getCardById(c.getId_card()).getMovement_speed()); // Все карты снова могут ходить (movement speed не 0)
            }

        }

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
                        cell.getSickness(),
                        cell.getCard_name(),
                        cell.getAttack(),
                        cell.getHealth(),
                        cell.getCost(),
                        cell.getEvasion(),
                        cell.getAttack_speed(),
                        cell.getMovement_speed()
                    )
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

    public ResponseEntity<?> getStatus(Integer id_game, String namePlayer, boolean isOpponent) {
        Status status;
        if (isOpponent){
            status = statusR.getOppStatus(id_game, namePlayer);
        } else {
            status = statusR.getStatus(id_game, namePlayer);
        }

        StatusResponse statusResponse = new StatusResponse(
                new StatusInfo(
                        status.getBabos(),
                        status.getCollectors(),
                        status.getHealth()
                )
        );
        return ResponseEntity.ok(statusResponse);
    }

    public ResponseEntity<?> getLibraries(Integer id_game, String namePlayer) {
        List<Library> libraries = libR.getLibs(namePlayer, id_game);

        List<LibraryInfo> librariesInfo = libraries.stream()
                .filter(library -> !library.getLocked())
                .map(
                    library -> new LibraryInfo(
                        library.getRarity()
                    )
                ).collect(Collectors.toList());

        LibrariesResponse librariesResponse = new LibrariesResponse(librariesInfo);
        return ResponseEntity.ok(librariesResponse);
    }

    public void changePhase(MoveCombatRequest req, String namePlayer)
            throws AlreadyFightException, NotYourTurnException, GameIsAlreadyEndedException {
        Integer gameId = req.getGameId();
        Game game = gameR.getReferenceById(gameId);
        if (game.getIs_ended()) {
            throw new GameIsAlreadyEndedException("Game is ended");
        }
        if(game.getIs_fight_phase()){
            throw new AlreadyFightException("Вы уже в боевой фазе");
        }
        if(namePlayer.equals(game.getName_turn())) {
            game.setIs_fight_phase(true);
        }
        else{
            throw new NotYourTurnException("Нельзя перейти в боевую фазу в чужой ход");
        }
        gameR.save(game);
    }

    public void openRarity(String playerName, Integer game_id)
            throws NoBabosException, GameIsAlreadyEndedException,
            AlreadyFightException, NotYourTurnException {
        Game game = gameR.getReferenceById(game_id);
        if (game.getIs_ended()) {
            throw new GameIsAlreadyEndedException("Game is ended");
        }
        if(game.getIs_fight_phase()){
            throw new AlreadyFightException("Вы уже в боевой фазе");
        }
        if(!playerName.equals(game.getName_turn())) {
            throw new NotYourTurnException("Нет");
        }
        String[] rarityS = new String[] {"common", "uncommon", "rare", "epic", "legendary"};
        List<Library> libs = libR.getLibs(playerName, game_id);

        // Создание карты для хранения порядка редкостей
        Map<String, Integer> rarityOrder = new HashMap<>();
        for (int i = 0; i < rarityS.length; i++) {
            rarityOrder.put(rarityS[i], i);
        }

        // Сортировка списка libs в соответствии с порядком в rarityS
        libs.sort((l1, l2) -> {
            Integer order1 = rarityOrder.get(l1.getRarity());
            Integer order2 = rarityOrder.get(l2.getRarity());
            return Integer.compare(order1, order2);
        });

        // Обход отсортированного списка
        for (Library l : libs) {
            if (l.getLocked().equals(true)) {
                Status status = statusR.getStatus(game_id, playerName);
                Integer babos = status.getBabos();
                int index = IntStream.range(0, rarityS.length)
                        .filter(i -> rarityS[i].equals(l.getRarity()))
                        .findFirst()
                        .orElse(-1);
                if (babos < (1 + index * 2)){
                    logger.error("не хватает денег, милорд");
                    logger.info("index: {}",index);
                    logger.info("babos: {}",babos);
                    throw new NoBabosException("не хватает денег, милорд");
                }
                status.setBabos(babos - (1 + index * 2));
                l.setLocked(false);
                Hand hand = handR.getHand(game_id, playerName);
                if(!(hand.getCards_cnt() >= 10)){
                    getCardToHand(l.getId_library(), hand.getId_hand());
                    hand.setCards_cnt(hand.getCards_cnt() + 1);
                }
                handR.save(hand);
                libR.save(l);
                statusR.save(status);
                break;
            }
        }
    }
}
