package ru.nsu.fit.battle_fw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CommonLibrary extends Library implements Serializable {
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
    public CommonLibrary(){

    }

    public CommonLibrary(String fraction) {
        this.fraction = fraction;
        card_list = new CardList(this.fraction).getCard_list();
        Collections.shuffle(this.card_list);
    }
}
