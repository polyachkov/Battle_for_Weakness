package ru.nsu.fit.battle_fw.requests.get;

public class GetGameRequest {
    private Integer playerId1;
    private Integer playerId2;

    public Integer getPlayerId1() {
        return playerId1;
    }

    public void setPlayerId1(Integer playerId1) {
        this.playerId1 = playerId1;
    }

    public Integer getPlayerId2() {
        return playerId2;
    }

    public void setPlayerId2(Integer playerId2) {
        this.playerId2 = playerId2;
    }
}
