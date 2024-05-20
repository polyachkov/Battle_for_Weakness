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
import ru.nsu.fit.battle_fw.requests.get.GetHandRequest;
import ru.nsu.fit.battle_fw.requests.post.*;
import ru.nsu.fit.battle_fw.responses.AllUsersResponse;
import ru.nsu.fit.battle_fw.responses.info.UserInfo;
import ru.nsu.fit.battle_fw.services.CardService;
import ru.nsu.fit.battle_fw.services.GameService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.nsu.fit.battle_fw.helpers.GetFromHeaders.getUsernameFromJWT;

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

    @PostMapping("/person/add")
    @Deprecated
    public void addPerson(@RequestBody Person person) throws PersonAlreadyExistsException {
        logger.info("POST /person/add");
        logger.info("name " + person.getName());
        logger.info("password " + person.getPassword());
        gameService.addPerson(person);
    }

//    @Deprecated
//    @PostMapping("/init")
//    public void init(@RequestHeader Map<String, String> headers, @RequestBody InitGameRequest req) {
//        logger.info("POST /init");
//        logger.info("fraction1 " + req.getFraction1());
//        logger.info("fraction2 " + req.getFraction2());
//        logger.info("player1 " + req.getPlayer1());
//        logger.info("player2 " + req.getPlayer2());
//        gameService.initializeGameAndLibraries(req);
//    }

    @PostMapping("/putCardInCell")
    public void putCardInCell(@RequestHeader Map<String, String> headers, @RequestBody PutCardInCellRequest req)
            throws NoBabosException, BadCellException, CollectorsLimitException {
        String nameOwner = getUsernameFromJWT(headers, jwtUtils);
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
        String nameOwner = getUsernameFromJWT(headers, jwtUtils);
        logger.info("POST /putCollectorInCell");
        logger.info("GameId " + req.getGameId());
        logger.info("PlayerId " + nameOwner);
        logger.info("CellId " + req.getCellId());
        cardService.putCollectorInCell(req, nameOwner);
    }

    @PostMapping("/moveCard")
    public void moveCardRequest(@RequestHeader Map<String, String> headers, @RequestBody MoveCardRequest req) {
        String nameOwner = getUsernameFromJWT(headers, jwtUtils);
        logger.info("POST /moveCard");
        logger.info("GameId " + req.getGameId());
        logger.info("PlayerId " + nameOwner);
        logger.info("CellId1 " + req.getCellId1());
        logger.info("CellId2 " + req.getCellId2());
        cardService.moveCard(req, nameOwner);
    }

    @PostMapping("/nextTurn")
    public void nextTurn(@RequestHeader Map<String, String> headers, @RequestBody NextTurnRequest req) {
        String nameOwner = getUsernameFromJWT(headers, jwtUtils);
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
        String nameOwner = getUsernameFromJWT(headers, jwtUtils);
        return gameService.getGameByPlayers(req, nameOwner);
    }

    @GetMapping("/get/all/games")
    public ResponseEntity<?> getAllGames(@RequestHeader Map<String, String> headers) {
        logger.info("GET /get/all/games");

        String nameOwner = getUsernameFromJWT(headers, jwtUtils);
        return gameService.getAllGames(nameOwner);
    }

    @GetMapping("/get/game/byid")
    public Optional<Game> getGameById(@RequestBody GameIdRequest req) {
        logger.info("GET /get/game");
        logger.info("get game by ID");
        return gameService.getGameById(req.getGameId());
    }

    @GetMapping("/get/field")
    public ResponseEntity<?> getField(@RequestParam("id_game") Integer value) {
        logger.info("GET /get/field");
        return gameService.getFieldByGame(value);
    }

    @GetMapping("/get/hand")
    public ResponseEntity<?> getHand(
            @RequestHeader Map<String, String> headers,
            @RequestParam("id_game") Integer value
    ) {
        logger.info("GET /get/hand");
        String nameOwner = getUsernameFromJWT(headers, jwtUtils);
        logger.info("nameOwner " + nameOwner);
        logger.info("game ID " + value);
        return gameService.getCardsInHand(value, nameOwner);
    }

    @GetMapping("/get/opp/hand")
    public ResponseEntity<?> getOppHand(
            @RequestHeader Map<String, String> headers,
            @RequestParam("id_game") Integer value
    ) {
        logger.info("GET /get/opp/hand");
        String nameOwner = getUsernameFromJWT(headers, jwtUtils);
        logger.info("nameOwner " + nameOwner);
        logger.info("game ID " + value);
        var number = gameService.getOppHand(value, nameOwner);
        logger.info("number " + number);
        return number;
    }

    @GetMapping(value = "/get-headers")
    public ResponseEntity<?> getHeaders(@RequestHeader Map<String, String> headers){//представляет заголовки ввиде мапы,
        //где ключ это наименование заголовка, а значение мапы - это значение заголовка
        return ResponseEntity.ok(headers);
    }
}
