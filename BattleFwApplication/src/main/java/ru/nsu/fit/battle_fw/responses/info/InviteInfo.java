package ru.nsu.fit.battle_fw.responses.info;

public class InviteInfo {
    private String inviter_name;

    public InviteInfo(String inviter_name) {
        this.inviter_name = inviter_name;
    }

    public String getInviter_name() {
        return inviter_name;
    }

    public void setInviter_name(String inviter_name) {
        this.inviter_name = inviter_name;
    }
}
