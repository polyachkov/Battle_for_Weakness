package ru.nsu.fit.battle_fw.requests.post;

public class InviteCreateRequest {

    private String invited_name;

    private String inviter_race;

    public String getInviter_race() {
        return inviter_race;
    }

    public void setInviter_race(String inviter_race) {
        this.inviter_race = inviter_race;
    }

    public String getInvited_name() {
        return invited_name;
    }

    public void setInvited_name(String invited_name) {
        this.invited_name = invited_name;
    }
}
