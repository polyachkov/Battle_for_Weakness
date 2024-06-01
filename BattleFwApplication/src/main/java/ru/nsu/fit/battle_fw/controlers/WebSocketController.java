package ru.nsu.fit.battle_fw.controlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.fit.battle_fw.configs.jwt.JwtUtils;
import ru.nsu.fit.battle_fw.database.model.Game;
import ru.nsu.fit.battle_fw.dto.game.GameDto;
import ru.nsu.fit.battle_fw.dto.hand.HandDto;
import ru.nsu.fit.battle_fw.services.CardService;
import ru.nsu.fit.battle_fw.services.GameService;

import java.util.List;
import java.util.Map;

import static ru.nsu.fit.battle_fw.helpers.GetFromHeaders.getUsernameFromJWT;
import static ru.nsu.fit.battle_fw.helpers.GetFromHeaders.getUsernameFromJWTWebsocket;

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

    @MessageMapping("/get/game/byId/{id_game}")
    @SendTo("/topic/{id_game}")
    public GameDto getGameById(@DestinationVariable Integer id_game) {
        logger.info("WEBSOCKET: /get/game/byId/{id_game}");
        return gameService.getGameById1(id_game);
    }

//    @MessageMapping("/get/hand/{id_game}")
//    @SendTo("/topic/hand/{id_game}")
//    public HandDto getHand(
//            @DestinationVariable Integer id_game
//    ) {
//        logger.info("WEBSOCKET: /get/hand/{id_game}");
////        String nameOwner = getUsernameFromJWT(headers, jwtUtils);
////        logger.info("nameOwner " + nameOwner);
////        logger.info("game ID " + id_game);
//        return gameService.getCardsInHand(id_game, "GoodOk");
//    }

//    @MessageMapping("/get/opp/hand/{id_game}")
//    @SendTo("/topic/opp/hand/{id_game}")
//    public ResponseEntity<?> getOppHand(
//            SimpMessageHeaderAccessor headerAccessor,
//            @DestinationVariable Integer id_game
//    ) {
//        logger.info("GET /get/opp/hand");
//        String nameOwner = getUsernameFromJWTWebsocket(headerAccessor, jwtUtils);
//        logger.info("nameOwner " + nameOwner);
//        logger.info("game ID " + id_game);
//        var number = gameService.getOppHand(id_game, nameOwner);
//        logger.info("number " + number);
//        return number;
//    }
}
