package ru.nsu.fit.battle_fw.controlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.battle_fw.database.model.Invite;
import ru.nsu.fit.battle_fw.database.repo.InviteRepo;
import ru.nsu.fit.battle_fw.exceptions.InviteIsNullException;
import ru.nsu.fit.battle_fw.requests.post.InviteAcceptRequest;
import ru.nsu.fit.battle_fw.requests.post.InviteCreateRequest;
import ru.nsu.fit.battle_fw.requests.post.InviteDeleteRequest;
import ru.nsu.fit.battle_fw.responses.AllInvitesResponse;
import ru.nsu.fit.battle_fw.responses.info.InviteInfo;
import ru.nsu.fit.battle_fw.configs.jwt.JwtUtils;
import ru.nsu.fit.battle_fw.services.GameService;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class InvitesController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final GameService gameService;

    @Autowired
    InviteRepo inviteRepo;

    @Autowired
    private JwtUtils jwtUtils;

    public InvitesController(GameService gameService) {
        this.gameService = gameService;
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

    @GetMapping("/get/all/invites")
    public ResponseEntity<?> getAllUsers(@RequestHeader Map<String, String> headers) {
        logger.info("GET /get/all/invites");

        String invited_name = getUsernameFromJWT(headers);

        List<Invite> invites = inviteRepo.getAllInvitesByInvitedName(invited_name);

        List<InviteInfo> invitesInfoList = invites.stream()
                .map(invite -> new InviteInfo(invite.getInviter_name()))
                .collect(Collectors.toList());

        AllInvitesResponse invitesResponse = new AllInvitesResponse(invitesInfoList);
        return ResponseEntity.ok(invitesResponse);
    }

    @PostMapping("/invite/create")
    public void InviteCreate(@RequestHeader Map<String, String> headers, @RequestBody InviteCreateRequest req) {
        logger.info("POST /invite/create");
        String nameOwner = getUsernameFromJWT(headers);
        gameService.createInvite(req, nameOwner);
    }

    @PostMapping("/invite/delete")
    public void InviteDelete(@RequestHeader Map<String, String> headers, @RequestBody InviteDeleteRequest req) {
        logger.info("POST /invite/delete");
        String nameOwner = getUsernameFromJWT(headers);
        gameService.deleteInvite(req, nameOwner);
    }

    @PostMapping("/invite/accept")
    public void InviteAccept(@RequestHeader Map<String, String> headers, @RequestBody InviteAcceptRequest req) throws InviteIsNullException {
        logger.info("POST /invite/accept");
        String nameOwner = getUsernameFromJWT(headers);
        gameService.acceptInvite(req, nameOwner);
    }
}
