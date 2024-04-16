package ru.nsu.fit.battle_fw.requests.post;

import java.io.Serializable;

public class InitGameRequest implements Serializable {
    private String fraction1;
    private String fraction2;
    private String player1;
    private String player2;

    public String getFraction1() {
        return fraction1;
    }

    public void setFraction1(String fraction1) {
        this.fraction1 = fraction1;
    }

    public String getFraction2() {
        return fraction2;
    }

    public void setFraction2(String fraction2) {
        this.fraction2 = fraction2;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public InitGameRequest() {}
    public InitGameRequest(String fraction1, String fraction2, String player1, String player2) {
        this.fraction1 = fraction1;
        this.fraction2 = fraction2;
        this.player1 = player1;
        this.player2 = player2;
    }
}
