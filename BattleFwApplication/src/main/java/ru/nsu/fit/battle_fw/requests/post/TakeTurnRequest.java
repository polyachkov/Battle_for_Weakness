package ru.nsu.fit.battle_fw.requests.post;

public class TakeTurnRequest {
    private int gameId;
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
}
