package ru.nsu.fit.battle_fw.controlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.fit.battle_fw.configs.jwt.JwtUtils;
import ru.nsu.fit.battle_fw.database.model.Game;
import ru.nsu.fit.battle_fw.dto.game.GameDto;
import ru.nsu.fit.battle_fw.services.CardService;
import ru.nsu.fit.battle_fw.services.GameService;

@Controller
public class WebSocketController {
    Logger logger = LoggerFactory.getLogger(SiteController.class);
    private final GameService gameService;
    private final CardService cardService;

    @Autowired
    private JwtUtils jwtUtils;

    public WebSocketController(GameService gameService,
                          CardService cardService) {
        this.gameService = gameService;
        this.cardService = cardService;
    }

//    @MessageMapping("/get/game/byId/{id_game}")
//    @SendTo("/topic/{id_game}")
//    public ResponseEntity<?> getGameById(@DestinationVariable Integer id_game) {
//        return gameService.getGameById(id_game);
//    }

    @MessageMapping("/get/game/byId/{id_game}")
    @SendTo("/topic/{id_game}")
    public GameDto getGameById(@DestinationVariable Integer id_game, Integer gameId) {
        logger.info("WEBSOCKET: /get/game/byId/{id_game}");
        return gameService.getGameById1(id_game);
    }

//    @MessageMapping("/getGameById")
//    public ResponseEntity<?> getGameById(@RequestParam("id_game") Integer value) {
//        logger.info("GET getGameById");
//        return gameService.getGameById(value);
//    }
}
