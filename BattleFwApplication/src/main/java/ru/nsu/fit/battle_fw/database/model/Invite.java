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

    @Column(name = "inviter_id")
    private Integer inviter_id;

    @Column(name = "invited_id")
    private Integer invited_id;

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

    public Integer getInviter_id() {
        return inviter_id;
    }

    public void setInviter_id(Integer inviter_id) {
        this.inviter_id = inviter_id;
    }

    public Integer getInvited_id() {
        return invited_id;
    }

    public void setInvited_id(Integer invited_id) {
        this.invited_id = invited_id;
    }

    public Invite(int inviter_id, int invited_id, String inviter_race){
        this.inviter_id = inviter_id;
        this.invited_id = invited_id;
        this.inviter_race = inviter_race;
    }
    public Invite(){
    }
}