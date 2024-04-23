package ru.nsu.fit.battle_fw.responses;

import ru.nsu.fit.battle_fw.responses.info.GameInfo;

import java.util.List;

public class AllGamesResponse {
    private List<GameInfo> games;

    public AllGamesResponse(List<GameInfo> games) {
        this.games = games;
    }

    public List<GameInfo> getGames() {
        return games;
    }

    public void setGames(List<GameInfo> games) {
        this.games = games;
    }
}
