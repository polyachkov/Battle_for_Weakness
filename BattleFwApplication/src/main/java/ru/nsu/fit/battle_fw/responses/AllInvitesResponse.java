package ru.nsu.fit.battle_fw.responses;

import ru.nsu.fit.battle_fw.responses.info.InviteInfo;

import java.util.List;
public class AllInvitesResponse {
    private List<InviteInfo> invites;

    public AllInvitesResponse(List<InviteInfo> invites) {
        this.invites = invites;
    }

    public List<InviteInfo> getInvites() {
        return invites;
    }

    public void setInvites(List<InviteInfo> invites) {
        this.invites = invites;
    }
}
