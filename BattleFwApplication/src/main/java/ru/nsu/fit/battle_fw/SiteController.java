package ru.nsu.fit.battle_fw;

import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.battle_fw.database.model.*;
import ru.nsu.fit.battle_fw.exceptions.PersonAlreadyExistsException;
import ru.nsu.fit.battle_fw.requests.InitGameRequest;
import ru.nsu.fit.battle_fw.requests.MoveCardRequest;
import ru.nsu.fit.battle_fw.requests.NextTurnRequest;
import ru.nsu.fit.battle_fw.requests.PutCardInCellRequest;

@RestController
public class SiteController {
    private final SiteControllerUtils gameService;

    public SiteController(SiteControllerUtils gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/person/add")
    public void addPerson(@RequestBody Person person) throws PersonAlreadyExistsException {
        gameService.addPerson(person);
    }

    @PostMapping("/init")
    public void init(@RequestBody InitGameRequest req) {
        gameService.initializeGameAndLibraries(req);
    }

    @PostMapping("/putCardInCell")
    public void putCardInCell(@RequestBody PutCardInCellRequest req) {
        gameService.putCardInCell(req);
    }

    @PostMapping("/moveCard")
    public void moveCardRequest(@RequestBody MoveCardRequest req) {
        gameService.moveCard(req);
    }

    @PostMapping("/nextTurn")
    public void nextTurn(@RequestBody NextTurnRequest req) {
        gameService.nextTurn(req);
    }
}
