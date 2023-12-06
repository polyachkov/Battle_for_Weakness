package ru.nsu.fit.battle_fw.elders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EpicLibrary extends Library implements Serializable {
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
    public EpicLibrary() {

    }

    public EpicLibrary(String fraction) {
        this.fraction = fraction;
        card_list = new EpicCardList(this.fraction).getCard_list();
        Collections.shuffle(this.card_list);
    }
}
