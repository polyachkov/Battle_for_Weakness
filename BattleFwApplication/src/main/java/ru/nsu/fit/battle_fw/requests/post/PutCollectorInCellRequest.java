package ru.nsu.fit.battle_fw.requests.post;

public class PutCollectorInCellRequest {
    private Integer gameId;
    private Integer cellId;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getCellId() {
        return cellId;
    }

    public void setCellId(Integer cellId) {
        this.cellId = cellId;
    }
}
