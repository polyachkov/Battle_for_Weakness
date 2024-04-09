package ru.nsu.fit.battle_fw.requests.post;

public class InviteAcceptRequest {
    private int inviter_id;

    private int invited_id;

    private String invited_race;

    public String getInvited_race() {
        return invited_race;
    }

    public void setInvited_race(String inviter_race) {
        this.invited_race = inviter_race;
    }

    public int getInviter_id() {
        return inviter_id;
    }

    public void setInviter_id(int inviter_id) {
        this.inviter_id = inviter_id;
    }

    public int getInvited_id() {
        return invited_id;
    }

    public void setInvited_id(int invited_id) {
        this.invited_id = invited_id;
    }
}
