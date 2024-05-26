package ru.nsu.fit.battle_fw.database.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "game", schema = "public")
public class Game {
    @Id
    @Column(name = "id_game")
    @SequenceGenerator(name = "gamesIdSeq", sequenceName = "games_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gamesIdSeq")
    private Integer id_game;

    @Column(name = "name_player1")
    private String name_player1;

    @Column(name = "name_player2")
    private String name_player2;

    @Column(name = "name_turn")
    private String name_turn;

    @Column(name = "is_ended")
    private Boolean is_ended;

    @Column(name = "non_reverse")
    private String non_reverse;

    @Column(name = "turn_ended")
    private Boolean turn_ended;

    public String getName_turn() {
        return name_turn;
    }

    public void setName_turn(String id_turn) {
        this.name_turn = id_turn;
    }

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

    public String getOppName(String name_player) {
        return name_player.equals(name_player1) ? name_player2 : name_player1;
    }
}
