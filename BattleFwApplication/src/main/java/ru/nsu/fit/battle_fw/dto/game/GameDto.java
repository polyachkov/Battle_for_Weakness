package ru.nsu.fit.battle_fw.dto.game;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameDto {
    private Integer id_game;
    private String name_player1;
    private String name_player2;
    private String name_turn;
    private Boolean is_ended;
    private String non_reverse;
    private Boolean turn_ended;
    private Boolean is_fight_phase;
}
