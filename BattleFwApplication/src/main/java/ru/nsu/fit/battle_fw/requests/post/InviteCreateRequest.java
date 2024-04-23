package ru.nsu.fit.battle_fw.requests.post;

public class InviteCreateRequest {

    private String invited_name;

    private String inviter_fraction;

    public String getInviter_fraction() {
        return inviter_fraction;
    }

    public void setInviter_fraction(String inviter_fraction) {
        this.inviter_fraction = inviter_fraction;
    }

    public String getInvited_name() {
        return invited_name;
    }

    public void setInvited_name(String invited_name) {
        this.invited_name = invited_name;
    }
}
