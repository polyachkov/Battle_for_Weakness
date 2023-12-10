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

    @Column(name = "id_player1")
    private Integer id_player1;

    @Column(name = "id_player2")
    private Integer id_player2;

    @Column(name = "id_turn")
    private Integer id_turn;

    @Column(name = "is_ended")
    private Boolean is_ended;

    public Integer getId_turn() {
        return id_turn;
    }

    public void setId_turn(Integer id_turn) {
        this.id_turn = id_turn;
    }

    public Integer getId_game() {
        return id_game;
    }

    public void setId_game(Integer id_game) {
        this.id_game = id_game;
    }

    public Integer getId_player1() {
        return id_player1;
    }

    public void setId_player1(Integer id_player1) {
        this.id_player1 = id_player1;
    }

    public Integer getId_player2() {
        return id_player2;
    }

    public void setId_player2(Integer id_player2) {
        this.id_player2 = id_player2;
    }

    public Boolean getIs_ended() {
        return is_ended;
    }

    public void setIs_ended(Boolean is_ended) {
        this.is_ended = is_ended;
    }
}
