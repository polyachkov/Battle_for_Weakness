package ru.nsu.fit.battle_fw.dto.game;

import ru.nsu.fit.battle_fw.database.model.Game;

public class GameMapper {
    public static GameDto toDTO(Game game) {
        return new GameDto(
                game.getId_game(),
                game.getName_player1(),
                game.getName_player2(),
                game.getName_turn(),
                game.getIs_ended(),
                game.getNon_reverse(),
                game.getTurn_ended(),
                game.getIs_fight_phase()
        );
    }
}
