package ru.nsu.fit.battle_fw.requests;

public class NextTurnRequest {
    private int gameId;
    private int nextTurnId;
    private String rarity;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public int getNextTurnId() {
        return nextTurnId;
    }

    public void setNextTurnId(int nextTurnId) {
        this.nextTurnId = nextTurnId;
    }
}
