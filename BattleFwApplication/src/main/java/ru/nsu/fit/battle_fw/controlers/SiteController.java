package ru.nsu.fit.battle_fw.controlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.battle_fw.database.model.*;
import ru.nsu.fit.battle_fw.exceptions.*;
import ru.nsu.fit.battle_fw.requests.get.GameIdRequest;
import ru.nsu.fit.battle_fw.requests.get.GetGameRequest;
import ru.nsu.fit.battle_fw.requests.post.*;
import ru.nsu.fit.battle_fw.services.CardService;
import ru.nsu.fit.battle_fw.services.GameService;

import java.util.List;
import java.util.Optional;

@RestController
public class SiteController {
    Logger logger = LoggerFactory.getLogger(SiteController.class);
    private final GameService gameService;
    private final CardService cardService;

    public SiteController(GameService gameService,
                          CardService cardService) {
        this.gameService = gameService;
        this.cardService = cardService;
    }

    @PostMapping("/person/add")
    public void addPerson(@RequestBody Person person) throws PersonAlreadyExistsException {
        logger.info("POST /person/add");
        logger.info("name " + person.getName());
        logger.info("password " + person.getPassword());
        gameService.addPerson(person);
    }

    @PostMapping("/init")
    public void init(@RequestBody InitGameRequest req) {
        logger.info("POST /init");
        logger.info("fraction1 " + req.getFraction1());
        logger.info("fraction2 " + req.getFraction2());
        logger.info("player1 " + req.getPlayer1());
        logger.info("player2 " + req.getPlayer2());
        gameService.initializeGameAndLibraries(req);
    }

    @PostMapping("/putCardInCell")
    public void putCardInCell(@RequestBody PutCardInCellRequest req)
            throws NoBabosException, BadCellException, CollectorsLimitException {
        logger.info("POST /putCardInCell");
        logger.info("GameId " + req.getGameId());
        logger.info("PlayerId " + req.getPlayerId());
        logger.info("CardId " + req.getCardId());
        logger.info("CellId " + req.getCellId());
        cardService.putCardInCell(req);
    }

    @PostMapping("/putCollectorInCell")
    public void putCollectorInCell(@RequestBody PutCollectorInCellRequest req)
            throws NoBabosException, BadCellException, CollectorsLimitException {
        logger.info("POST /putCardInCell");
        logger.info("GameId " + req.getGameId());
        logger.info("PlayerId " + req.getPlayerId());
        logger.info("CellId " + req.getCellId());
        cardService.putCollectorInCell(req);
    }

    @PostMapping("/moveCard")
    public void moveCardRequest(@RequestBody MoveCardRequest req) {
        logger.info("POST /moveCard");
        logger.info("GameId " + req.getGameId());
        logger.info("PlayerId " + req.getPlayerId());
        logger.info("CellId1 " + req.getCellId1());
        logger.info("CellId2 " + req.getCellId2());
        cardService.moveCard(req);
    }

    @PostMapping("/nextTurn")
    public void nextTurn(@RequestBody NextTurnRequest req) {
        logger.info("POST /nextTurn");
        logger.info("GameId " + req.getGameId());
        logger.info("NextTurnId " + req.getNextTurnId());
        logger.info("Rarity " + req.getRarity());
        gameService.nextTurn(req);
    }

    @GetMapping("/get/game/byplayers")
    public Optional<Game> getGameId(@RequestBody GetGameRequest req) {
        logger.info("GET /get/Game");
        logger.info("get game by players");
        return gameService.getGameByPlayers(req);
    }

    @GetMapping("/get/game/byid")
    public Optional<Game> getGameById(@RequestBody GameIdRequest req) {
        logger.info("GET /get/game");
        logger.info("get game by ID");
        return gameService.getGameById(req.getGameId());
    }

    @GetMapping("/get/filed")
    public Optional<List<Cell>> getField(@RequestBody GameIdRequest req) {
        logger.info("GET /get/field");
        return gameService.getFieldByGame(req.getGameId());
    }

    @PostMapping("/invite/create")
    public void InviteCreate(@RequestBody InviteCreateRequest req) {
        logger.info("POST /invite/create");
        gameService.createInvite(req);
    }

    @PostMapping("/invite/delete")
    public void InviteDelete(@RequestBody InviteDeleteRequest req) {
        logger.info("POST /invite/delete");
        gameService.deleteInvite(req);
    }

    @PostMapping("/invite/accept")
    public void InviteAccept(@RequestBody InviteAcceptRequest req) throws InviteIsNullException {
        logger.info("POST /invite/accept");
        gameService.acceptInvite(req);
    }
}
