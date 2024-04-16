package ru.nsu.fit.battle_fw.responses;

import ru.nsu.fit.battle_fw.responses.info.UserInfo;

import java.util.List;

public class AllUsersResponse {
    private List<UserInfo> users;

    public AllUsersResponse(List<UserInfo> users) {
        this.users = users;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }
}
