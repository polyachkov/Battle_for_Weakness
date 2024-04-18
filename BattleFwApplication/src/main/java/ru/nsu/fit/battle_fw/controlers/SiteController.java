package ru.nsu.fit.battle_fw.controlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.battle_fw.configs.jwt.JwtUtils;
import ru.nsu.fit.battle_fw.database.model.*;
import ru.nsu.fit.battle_fw.exceptions.*;
import ru.nsu.fit.battle_fw.requests.get.GameIdRequest;
import ru.nsu.fit.battle_fw.requests.get.GetGameRequest;
import ru.nsu.fit.battle_fw.requests.post.*;
import ru.nsu.fit.battle_fw.services.CardService;
import ru.nsu.fit.battle_fw.services.GameService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class SiteController {

    Logger logger = LoggerFactory.getLogger(SiteController.class);
    private final GameService gameService;
    private final CardService cardService;

    @Autowired
    private JwtUtils jwtUtils;

    public SiteController(GameService gameService,
                          CardService cardService) {
        this.gameService = gameService;
        this.cardService = cardService;
    }

    public String getUsernameFromJWT(Map<String, String> headers){
        String headerAuth = headers.get("authorization");
        String jwt;

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            jwt = headerAuth.substring(7);
        }
        else{
            return null;
        }
        return jwtUtils.getUserNameFromJwtToken(jwt);
    }

    @PostMapping("/person/add")
    @Deprecated
    public void addPerson(@RequestBody Person person) throws PersonAlreadyExistsException {
        logger.info("POST /person/add");
        logger.info("name " + person.getName());
        logger.info("password " + person.getPassword());
        gameService.addPerson(person);
    }

    @Deprecated
    @PostMapping("/init")
    public void init(@RequestHeader Map<String, String> headers, @RequestBody InitGameRequest req) {
        logger.info("POST /init");
        logger.info("fraction1 " + req.getFraction1());
        logger.info("fraction2 " + req.getFraction2());
        logger.info("player1 " + req.getPlayer1());
        logger.info("player2 " + req.getPlayer2());
        gameService.initializeGameAndLibraries(req);
    }

    @PostMapping("/putCardInCell")
    public void putCardInCell(@RequestHeader Map<String, String> headers, @RequestBody PutCardInCellRequest req)
            throws NoBabosException, BadCellException, CollectorsLimitException {
        String nameOwner = getUsernameFromJWT(headers);
        logger.info("POST /putCardInCell");
        logger.info("GameId " + req.getGameId());
        logger.info("PlayerId " + nameOwner);
        logger.info("CardId " + req.getCardId());
        logger.info("CellId " + req.getCellId());
        cardService.putCardInCell(req, nameOwner);
    }

    @PostMapping("/putCollectorInCell")
    public void putCollectorInCell(@RequestHeader Map<String, String> headers, @RequestBody PutCollectorInCellRequest req)
            throws NoBabosException, BadCellException, CollectorsLimitException {
        String nameOwner = getUsernameFromJWT(headers);
        logger.info("POST /putCardInCell");
        logger.info("GameId " + req.getGameId());
        logger.info("PlayerId " + nameOwner);
        logger.info("CellId " + req.getCellId());
        cardService.putCollectorInCell(req, nameOwner);
    }

    @PostMapping("/moveCard")
    public void moveCardRequest(@RequestHeader Map<String, String> headers, @RequestBody MoveCardRequest req) {
        String nameOwner = getUsernameFromJWT(headers);
        logger.info("POST /moveCard");
        logger.info("GameId " + req.getGameId());
        logger.info("PlayerId " + nameOwner);
        logger.info("CellId1 " + req.getCellId1());
        logger.info("CellId2 " + req.getCellId2());
        cardService.moveCard(req, nameOwner);
    }

    @PostMapping("/nextTurn")
    public void nextTurn(@RequestHeader Map<String, String> headers, @RequestBody NextTurnRequest req) {
        String nameOwner = getUsernameFromJWT(headers);
        logger.info("POST /nextTurn");
        logger.info("GameId " + req.getGameId());
        logger.info("NextTurnId " + nameOwner);
        logger.info("Rarity " + req.getRarity());
        gameService.nextTurn(req, nameOwner);
    }

    @GetMapping("/get/game/byplayers")
    public Optional<Game> getGameId(@RequestHeader Map<String, String> headers, @RequestBody GetGameRequest req) {
        logger.info("GET /get/Game");
        logger.info("get game by players");
        String nameOwner = getUsernameFromJWT(headers);
        return gameService.getGameByPlayers(req, nameOwner);
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

    @GetMapping(value = "/get-headers")
    public ResponseEntity<?> getHeaders(@RequestHeader Map<String, String> headers){//представляет заголовки ввиде мапы,
        //где ключ это наименование заголовка, а значение мапы - это значение заголовка
        return ResponseEntity.ok(headers);
    }
    @GetMapping(value = "/get-jwt")
    public String getJWT(@RequestHeader Map<String, String> headers){//представляет заголовки ввиде мапы,
        //где ключ это наименование заголовка, а значение мапы - это значение заголовка
        return getUsernameFromJWT(headers) != null ? getUsernameFromJWT(headers) : "hui";
    }
}
