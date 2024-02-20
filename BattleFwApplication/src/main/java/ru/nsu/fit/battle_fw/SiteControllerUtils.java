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

@Component
public class SiteControllerUtils {

    private final PersonRepo personR;
    private final CardRepo cardR;
    private final GameRepo gameR;
    private final LibraryRepo libR;
    private final LibraryCompRepo libCompR;
    private final HandRepo handR;
    private final HandCompRepo handCompR;
    private final CellRepo cellR;
    private final StatusRepo statusR;

    public SiteControllerUtils(PersonRepo personR, CardRepo cardR, GameRepo gameR,
                               LibraryRepo libR, LibraryCompRepo libCompR,
                               HandRepo handR, HandCompRepo handCompR, CellRepo cellR,
                               StatusRepo statusR) {
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

    public void initializeGameAndLibraries(InitGameRequest req) {
        String fraction1 = req.getFraction1();
        String fraction2 = req.getFraction2();
        Integer player1 = req.getPlayer1();
        Integer player2 = req.getPlayer2();

        Game game = initializeGame(player1, player2);

        initializeStatus(game.getId_game(), player1, game.getId_turn());
        initializeStatus(game.getId_game(), player2, game.getId_turn());

        initializeLibraries(fraction1, game, player1);
        initializeLibraries(fraction2, game, player2);
        createHands(game, player1, player2);
        createField(game.getId_game());
    }

    private Game initializeGame(Integer player1, Integer player2) {
        Game game = new Game();
        game.setId_player1(player1);
        game.setId_player2(player2);
        game.setIs_ended(false);
        Random isFirstPlayer = new Random();
        if (isFirstPlayer.nextBoolean()) {
            game.setId_turn(player1);
        } else {
            game.setId_turn(player2);
        }
        gameR.save(game);
        return game;
    }

    private void initializeStatus(Integer gameId, Integer playerId, Integer turnId) {
        Status status = new Status();
        status.setId_player(playerId);
        status.setId_game(gameId);
        status.setCollectors(0);
        status.setHealth(25);
        if (Objects.equals(turnId, playerId)) {
            status.setBabos(2);
        }  else  {
            status.setBabos(1);
        }
        statusR.save(status);
    }

    private void initializeLibraries(String fraction, Game game, Integer playerId) {
        String[] rarities = new String[]{"common", "uncommon", "rare", "epic", "legendary"};
        for (String rarity : rarities) {
            createLib(rarity, fraction, game, playerId);
        }
    }

    private void createHands(Game game, Integer player1, Integer player2) {
        createHand(libR.getLibId(player1, game.getId_game(), "common"), game.getId_game(), player1);
        createHand(libR.getLibId(player2, game.getId_game(), "common"), game.getId_game(), player2);
    }

    private void createLib(String rarity, String fraction, Game game, Integer playerId) {
        List<Card> listC = cardR.getListOfCard(rarity, fraction);
        List<Integer> listId = new ArrayList<Integer>();
        for (Card c : listC) {
            for (int i = c.getNumber_of_cards(); i > 0; i--) {
                listId.add(c.getId_card());
            }
        }
        Collections.shuffle(listId);

        Library lib = new Library();
        lib.setCards_cnt(listId.size());
        lib.setGame_id(game.getId_game());
        lib.setPlayer_id(playerId);
        lib.setLocked(!rarity.equals("common"));
        lib.setRarity(rarity);
        libR.save(lib);

        for (Integer c : listId) {
            LibraryComp libComp = new LibraryComp();
            libComp.setId_card(c);
            libComp.setId_library(lib.getId_library());
            libCompR.save(libComp);
        }
    }

    private void createHand(Integer lib_id, Integer id_game, Integer id_player) {
        Hand hand = new Hand();
        hand.setId_game(id_game);
        hand.setCards_cnt(0);
        hand.setId_player(id_player);
        handR.save(hand);
        for (int i = 0; i < 7; i++) {
            getCardToHand(lib_id, hand.getId_hand());
            hand.setCards_cnt(hand.getCards_cnt() + 1);
        }
    }

    private void getCardToHand(Integer lib_id, Integer id_hand) {
        LibraryComp libComp = libCompR.getMinCard(lib_id);
        HandComp handComp = new HandComp();
        handComp.setId_hand(id_hand);
        handComp.setId_card(libComp.getId_card());
        libCompR.deleteById(libComp.getId_card_lib());
        Library lib = libR.getReferenceById(lib_id);
        lib.setCards_cnt(lib.getCards_cnt() - 1);
        libR.save(lib);
        handCompR.save(handComp);
    }

    private void createField(Integer gameId) {
        for (int i = 1; i <= 56; i++) {
            Cell cell = new Cell();
            cell.setCell_num(i);
            cell.setId_card(null);
            cell.setId_game(gameId);
            cellR.save(cell);
        }
    }

    public void putCardInCell(PutCardInCellRequest req)
            throws NoBabosException, BadCellException {
        Integer gameId = req.getGameId();
        Integer playerId = req.getPlayerId();
        Integer cardId = req.getCardId();
        Integer cellId = req.getCellId();

        if (cellId <= 8 || cellId >= 49) {
            throw new BadCellException();
        } else {
            Card card = cardR.getReferenceById(cardId);
            Status status = statusR.getStatus(gameId, playerId);

            if (status.getBabos() < card.getCost()) {
                throw new NoBabosException();
            } else {
                Hand hand = handR.getHand(gameId, playerId);
                hand.setCards_cnt(hand.getCards_cnt() - 1);

                List<HandComp> handCompList = handCompR.getHandCard(hand.getId_hand(), cardId);

                HandComp handComp = handCompList.get(0);

                Cell cell = cellR.getCell(gameId, cellId);
                cell.setId_card(cardId);
                cell.setId_owner(playerId);
                cell.setSickness(1);

                status.setBabos(status.getBabos() - card.getCost());

                handCompR.deleteById(handComp.getId_hand_card());
                statusR.save(status);
                handR.save(hand);
                cellR.save(cell);
            }
        }
    }

    public void putCollectorInCell(PutCollectorInCellRequest req)
            throws NoBabosException, BadCellException, CollectorsLimitException {
        Integer gameId = req.getGameId();
        Integer playerId = req.getPlayerId();
        Integer cellId = req.getCellId();

        if (cellId > 8 && cellId < 49) {
            throw new BadCellException();
        } else {
            Card collector = cardR.getReferenceById(49);
            //49 - collector`s ID
            Status status = statusR.getStatus(gameId, playerId);

            if (status.getBabos() < collector.getCost()) {
                throw new NoBabosException();
            } else if (status.getCollectors() >= 4) {
                throw new CollectorsLimitException();
            } else {
                Cell cell = cellR.getCell(gameId, cellId);
                cell.setId_card(49);
                cell.setId_owner(playerId);
                cell.setSickness(0);

                status.setCollectors(status.getCollectors() + 1);

                cellR.save(cell);
                statusR.save(status);
            }
        }
    }

    public void moveCard(MoveCardRequest req) {
        Integer gameId = req.getGameId();
        Integer playerId = req.getGameId();
        Integer cellId1 = req.getCellId1();
        Integer cellId2 = req.getCellId2();

        Cell cell1 = cellR.getCell(gameId, cellId1);
        Cell cell2 = cellR.getCell(gameId, cellId2);
        cell2.setId_card(cell1.getId_card());
        cell2.setId_owner(playerId);
        cell1.setId_card(null);
        cell1.setId_owner(null);

        cellR.save(cell1);
        cellR.save(cell2);
    }

    public void nextTurn(NextTurnRequest req) {
        Integer nextTurnId = req.getNextTurnId();
        Integer gameId = req.getGameId();
        String rarity = req.getRarity();

        Game game = gameR.getReferenceById(gameId);
        game.setId_turn(nextTurnId);

        Status status = statusR.getStatus(gameId, nextTurnId);
        status.setBabos(status.getBabos() + 2);

        Hand hand = handR.getHand(gameId, nextTurnId);
        hand.setCards_cnt(hand.getCards_cnt() + 1);

        List<Cell> cells = cellR.getCells(gameId);
        for (Cell c : cells) {
            c.setSickness(0);
        }

        Integer library_id = libR.getLibId(nextTurnId, gameId, rarity);
        getCardToHand(library_id, hand.getId_hand());

        gameR.save(game);
        statusR.save(status);
        handR.save(hand);
        cellR.saveAll(cells);
    }

    public void addPerson(Person person) throws PersonAlreadyExistsException {
        Person reference = personR.findByName(person.getName());
        if (reference == null) {
            personR.save(person);
        } else {
            throw new PersonAlreadyExistsException("p");
        }
    }

    public Optional<Card> getOne() {
        return cardR.findById(1);
    }

    /**
     * Get game by 2 id of players
     */
    public Optional<Game> getGameByPlayers(GetGameRequest req) {
        Integer playerId1 = req.getPlayerId1();
        Integer playerId2 = req.getPlayerId2();

        Game game = gameR.getGame(playerId1, playerId2);

        return Optional.ofNullable(game);
    }
    /**
     * Get game by id of game
     */
    public Optional<Game> getGameById(Integer gameId) {
        return gameR.findById(gameId);
    }

    /**
     * Get field by game id
     */
    public Optional<List<Cell>> getFieldByGame(Integer gameId) {
        return Optional.of(cellR.getCells(gameId));

    }

}
