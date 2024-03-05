package ru.nsu.fit.battle_fw.database.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "status", schema = "public")
public class Status {
    @Id
    @Column(name = "id_status")
    @SequenceGenerator(name = "statusIdSeq", sequenceName = "status_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "statusIdSeq")
    private Integer id_status;

    @Column(name = "id_player")
    private Integer id_player;

    @Column(name = "id_game")
    private Integer id_game;

    @Column(name = "babos")
    private Integer babos;

    @Column(name = "collectors")
    private Integer collectors;

    @Column(name = "health")
    private Integer health;

    public Integer getId_status() {
        return id_status;
    }

    public void setId_status(Integer id_status) {
        this.id_status = id_status;
    }

    public Integer getId_player() {
        return id_player;
    }

    public void setId_player(Integer id_player) {
        this.id_player = id_player;
    }

    public Integer getId_game() {
        return id_game;
    }

    public void setId_game(Integer id_game) {
        this.id_game = id_game;
    }

    public Integer getBabos() {
        return babos;
    }

    public void setBabos(Integer babos) {
        this.babos = babos;
    }

    public Integer getCollectors() {
        return collectors;
    }

    public void setCollectors(Integer collectors) {
        this.collectors = collectors;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }
}
