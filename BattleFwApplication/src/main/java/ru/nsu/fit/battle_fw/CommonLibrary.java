package ru.nsu.fit.battle_fw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommonLibrary implements Serializable {
    private String fraction;
    private List<Card> card_list = new ArrayList<>();

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }

    public List<Card> getCard_list() {
        return card_list;
    }

    public void setCard_list(List<Card> card_list) {
        this.card_list = card_list;
    }

    public CommonLibrary(){}

    public CommonLibrary(String fraction) {
        this.fraction = fraction;
        for (int i = 0; i < 10; i++) {
            card_list.add(new Card("Kobold", 1 + i, i + 2, 0, i, i, i));
        }
    }
}
