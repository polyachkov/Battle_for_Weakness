package ru.nsu.fit.battle_fw.responses.info;

public class GameInfo {
    private String name_player1;
    private String name_player2;

    public GameInfo(String name_player1, String name_player2) {
        this.name_player1 = name_player1;
        this.name_player2 = name_player2;
    }

    public String getName_player1() {
        return name_player1;
    }

    public void setName_player1(String name_player1) {
        this.name_player1 = name_player1;
    }

    public String getName_player2() {
        return name_player2;
    }

    public void setName_player2(String name_player2) {
        this.name_player2 = name_player2;
    }
}
