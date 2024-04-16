package ru.nsu.fit.battle_fw.database.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "library", schema = "public")
public class Library {
    @Id
    @Column(name = "id_library")
    @SequenceGenerator(name = "librariesIdSeq", sequenceName = "libraries_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "librariesIdSeq")
    private Integer id_library;

    @Column(name = "game_id")
    private Integer game_id;

    @Column(name = "player_name")
    private String player_name;


    @Column(name = "cards_cnt")
    private Integer cards_cnt;

    @Column(name = "rarity")
    private String rarity;

    @Column(name = "locked")
    private Boolean locked;

    public Integer getId_library() {
        return id_library;
    }

    public void setId_library(Integer id_library) {
        this.id_library = id_library;
    }

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    public String getPlayer_id() {
        return player_name;
    }

    public void setPlayer_id(String player_id) {
        this.player_name = player_id;
    }

    public Integer getCards_cnt() {
        return cards_cnt;
    }

    public void setCards_cnt(Integer cards) {
        this.cards_cnt = cards;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
