package ru.nsu.fit.battle_fw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.battle_fw.database.model.*;
import ru.nsu.fit.battle_fw.exceptions.PersonAlreadyExistsException;
import ru.nsu.fit.battle_fw.requests.InitGameRequest;
import ru.nsu.fit.battle_fw.requests.MoveCardRequest;
import ru.nsu.fit.battle_fw.requests.NextTurnRequest;
import ru.nsu.fit.battle_fw.requests.PutCardInCellRequest;

import java.util.Optional;


@RestController
public class SiteController {

    Logger logger = LoggerFactory.getLogger(SiteController.class);
    private final SiteControllerUtils gameService;

    public SiteController(SiteControllerUtils gameService) {
        this.gameService = gameService;
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
    public void putCardInCell(@RequestBody PutCardInCellRequest req) {
        logger.info("POST /putCardInCell");
        logger.info("GameId " + req.getGameId());
        logger.info("PlayerId " + req.getPlayerId());
        logger.info("CardId " + req.getCardId());
        logger.info("CellId " + req.getCellId());
        gameService.putCardInCell(req);
    }

    @PostMapping("/moveCard")
    public void moveCardRequest(@RequestBody MoveCardRequest req) {
        logger.info("POST /moveCard");
        logger.info("GameId " + req.getGameId());
        logger.info("PlayerId " + req.getPlayerId());
        logger.info("CellId1 " + req.getCellId1());
        logger.info("CellId2 " + req.getCellId2());
        gameService.moveCard(req);
    }

    @PostMapping("/nextTurn")
    public void nextTurn(@RequestBody NextTurnRequest req) {
        logger.info("POST /nextTurn");
        logger.info("GameId " + req.getGameId());
        logger.info("NextTurnId " + req.getNextTurnId());
        logger.info("Rarity " + req.getRarity());
        gameService.nextTurn(req);
    }

    @GetMapping("/getone")
    public Optional<Card> getOne() {
        logger.info("POST /getone");
        return gameService.getOne();
    }

}
