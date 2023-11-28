package ru.nsu.fit.battle_fw;

import java.io.Serializable;

public class InitGameRequest implements Serializable {
    private String fraction1;
    private String fraction2;
    private Integer player1;
    private Integer player2;

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

    public Integer getPlayer1() {
        return player1;
    }

    public void setPlayer1(Integer player1) {
        this.player1 = player1;
    }

    public Integer getPlayer2() {
        return player2;
    }

    public void setPlayer2(Integer player2) {
        this.player2 = player2;
    }
}
