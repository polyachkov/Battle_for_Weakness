package ru.nsu.fit.battle_fw.requests.post;

public class MoveCardRequest {
    private Integer gameId;
    private Integer playerId;
    private Integer cellId1;
    private Integer cellId2;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getCellId1() {
        return cellId1;
    }

    public void setCellId1(Integer cellId1) {
        this.cellId1 = cellId1;
    }

    public Integer getCellId2() {
        return cellId2;
    }

    public void setCellId2(Integer cellId2) {
        this.cellId2 = cellId2;
    }
}
