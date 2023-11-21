package ru.nsu.fit.battle_fw;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SiteController {

    @PostMapping("/init")
    public ResponseEntity<InitGameResponse> initGame(@RequestBody InitGameRequest object) {
        var fraction = object.getFraction();
        var board = new Board(6, 8);
        var common_library = new CommonLibrary(fraction);
        var uncommon_library = new UncommonLibrary(fraction);
        var rare_library = new RareLibrary(fraction);
        var epic_library = new EpicLibrary(fraction);
        var legendary_library = new LegendaryLibrary(fraction);
        var responce = new InitGameResponse();
        responce.setBoard(board);
        responce.setCommon_library(common_library);
        responce.setUncommon_library(uncommon_library);
        responce.setRare_library(rare_library);
        responce.setEpic_library(epic_library);
        responce.setLegendary_library(legendary_library);


        return ResponseEntity.ok(responce);
    }
}
