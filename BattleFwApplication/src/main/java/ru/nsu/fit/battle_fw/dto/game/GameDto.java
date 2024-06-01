package ru.nsu.fit.battle_fw.dto.game;

import jakarta.persistence.Column;

public class GameDto {
    private Integer id_game;
    private String name_player1;
    private String name_player2;
    private String name_turn;
    private Boolean is_ended;
    private String non_reverse;
    private Boolean turn_ended;
    private Boolean is_fight_phase;

    public Integer getId_game() {
        return id_game;
    }

    public void setId_game(Integer id_game) {
        this.id_game = id_game;
    }

    public String getName_player1() {
        return name_player1;
    }

    public void setName_player1(String name_player1) {
        this.name_player1 = name_player1;
    }

    public String getName_player2() {
        return name_player2;
    }

    public void setName_player2(String name_player2) {
        this.name_player2 = name_player2;
    }

    public String getName_turn() {
        return name_turn;
    }

    public void setName_turn(String name_turn) {
        this.name_turn = name_turn;
    }

    public Boolean getIs_ended() {
        return is_ended;
    }

    public void setIs_ended(Boolean is_ended) {
        this.is_ended = is_ended;
    }

    public String getNon_reverse() {
        return non_reverse;
    }

    public void setNon_reverse(String non_reverse) {
        this.non_reverse = non_reverse;
    }

    public Boolean getTurn_ended() {
        return turn_ended;
    }

    public void setTurn_ended(Boolean turn_ended) {
        this.turn_ended = turn_ended;
    }

    public Boolean getIs_fight_phase() {
        return is_fight_phase;
    }

    public void setIs_fight_phase(Boolean is_fight_phase) {
        this.is_fight_phase = is_fight_phase;
    }
}
