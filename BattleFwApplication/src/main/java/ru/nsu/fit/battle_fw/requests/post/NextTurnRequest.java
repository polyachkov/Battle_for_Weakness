package ru.nsu.fit.battle_fw.requests.post;

public class NextTurnRequest {
    private int gameId;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
