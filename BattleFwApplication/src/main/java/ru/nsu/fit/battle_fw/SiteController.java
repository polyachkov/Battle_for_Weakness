package ru.nsu.fit.battle_fw;

import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.battle_fw.database.model.*;
import ru.nsu.fit.battle_fw.exceptions.PersonAlreadyExistsException;
import ru.nsu.fit.battle_fw.requests.InitGameRequest;
import ru.nsu.fit.battle_fw.requests.nextTurnRequest;
import ru.nsu.fit.battle_fw.requests.putCardInCellRequest;

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
    public void init(@RequestBody putCardInCellRequest req) {
        gameService.putCardInCell(req);
    }

    @PostMapping("/nextTurn")
    public void init(@RequestBody nextTurnRequest req) {
        gameService.nextTurn(req);
    }
}
