package ru.nsu.fit.battle_fw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LegendaryLibrary extends Library implements Serializable {
    private String fraction;
    private List<Card> card_list = new ArrayList<>();
    private boolean locked = true;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

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
    public LegendaryLibrary() {

    }

    public LegendaryLibrary(String fraction) {
        this.fraction = fraction;
        card_list = new LegendaryCardList(this.fraction).getCard_list();
        Collections.shuffle(this.card_list);
    }
}
