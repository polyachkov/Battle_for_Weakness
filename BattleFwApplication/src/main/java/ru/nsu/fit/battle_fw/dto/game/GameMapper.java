package ru.nsu.fit.battle_fw.dto.game;

import ru.nsu.fit.battle_fw.database.model.Game;

public class GameMapper {
    public static GameDto toDTO(Game game) {
        GameDto gameDto = new GameDto();
        gameDto.setId_game(game.getId_game());
        gameDto.setName_player1(game.getName_player1());
        gameDto.setName_player2(game.getName_player2());
        gameDto.setName_turn(game.getName_turn());
        gameDto.setIs_ended(game.getIs_ended());
        gameDto.setNon_reverse(game.getNon_reverse());
        gameDto.setTurn_ended(game.getTurn_ended());
        gameDto.setIs_fight_phase(game.getIs_fight_phase());
        return gameDto;
    }
}
