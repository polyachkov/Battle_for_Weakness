package ru.nsu.fit.battle_fw.requests.post;

public class InviteAcceptRequest {
    private String inviter_name;

    private String invited_fraction;

    public String getInvited_fraction() {
        return invited_fraction;
    }

    public void setInvited_fraction(String inviter_fraction) {
        this.invited_fraction = inviter_fraction;
    }

    public String getInviter_name() {
        return inviter_name;
    }

    public void setInviter_name(String inviter_name) {
        this.inviter_name = inviter_name;
    }
}
