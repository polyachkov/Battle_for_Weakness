package ru.nsu.fit.battle_fw.database.model;

import jakarta.persistence.*;

public class Invites {
    @Id
    @Column(name = "id_invite")
    @SequenceGenerator(name = "invitesIdSeq", sequenceName = "invites_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invitesIdSeq")
    private Integer id_invite;

    private Integer id_player1;
    private Integer id_player2;

    public Integer getId_invite() {
        return id_invite;
    }

    public void setId_invite(Integer id_invite) {
        this.id_invite = id_invite;
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
}
