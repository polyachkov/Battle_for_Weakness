package ru.nsu.fit.battle_fw;

import java.io.Serializable;

public class InitGameRequest implements Serializable {
    private String fraction;

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }
}
