package ru.nsu.fit.battle_fw.database.model;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "invites", schema = "public")
public class Invite {
    @Id
    @Column(name = "invite_id")
    @SequenceGenerator(name = "inviteIdSeq", sequenceName = "invite_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inviteIdSeq")
    private Integer invite_id;

    @Column(name = "inviter_name")
    private String inviter_name;

    @Column(name = "invited_name")
    private String invited_name;

    @Column(name = "inviter_race")
    private String inviter_race;

    public String getInviter_race() {
        return inviter_race;
    }

    public void setInviter_race(String inviter_race) {
        this.inviter_race = inviter_race;
    }

    public Integer getInvite_id() {
        return invite_id;
    }

    public void setInvite_id(Integer invite_id) {
        this.invite_id = invite_id;
    }

    public String getInviter_name() {
        return inviter_name;
    }

    public void setInviter_name(String inviter_name) {
        this.inviter_name = inviter_name;
    }

    public String getInvited_name() {
        return invited_name;
    }

    public void setInvited_name(String invited_name) {
        this.invited_name = invited_name;
    }

    public Invite(String inviter_name, String invited_name, String inviter_race){
        this.inviter_name = inviter_name;
        this.invited_name = invited_name;
        this.inviter_race = inviter_race;
    }
    public Invite(){
    }
}