package ru.nsu.fit.battle_fw.database.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "hand", schema = "public")
public class Hand {
    @Id
    @Column(name = "id_hand")
    @SequenceGenerator(name = "handsIdSeq", sequenceName = "hands_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "handsIdSeq")
    private Integer id_hand;

    @Column(name = "id_game")
    private Integer id_game;

    @Column(name = "name_player")
    private String name_player;

    @Column(name = "cards_cnt")
    private Integer cards_cnt;

    public Integer getId_hand() {
        return id_hand;
    }

    public void setId_hand(Integer id_hand) {
        this.id_hand = id_hand;
    }

    public Integer getId_game() {
        return id_game;
    }

    public void setId_game(Integer id_game) {
        this.id_game = id_game;
    }

    public String getName_player() {
        return name_player;
    }

    public void setName_player(String name_player) {
        this.name_player = name_player;
    }

    public Integer getCards_cnt() {
        return cards_cnt;
    }

    public void setCards_cnt(Integer cards_cnt) {
        this.cards_cnt = cards_cnt;
    }
}

