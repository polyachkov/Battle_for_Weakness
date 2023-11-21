package ru.nsu.fit.battle_fw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static ru.nsu.fit.battle_fw.Speed.*;

public class LegendaryCardList implements Serializable {
    private List<Card> card_list = new ArrayList<>();

    public List<Card> getCard_list() {
        return card_list;
    }

    public void setCard_list(List<Card> card_list) {
        this.card_list = card_list;
    }

    public LegendaryCardList(String fraction) {
        if (fraction.equals("Mountains")) {
            createMountains();
        }
        if (fraction.equals("Swamp")) {
            createSwamp();
        }
    }
    private void createMountains() {
        card_list.add(new Card("Father of the Bald Mountain", 1, 11,
                11, 1, Fast, 1, true));

        card_list.add(new Card("Kobold Banner", 0, 10,
                15, 0, None, 0, true));

        card_list.add(new Card("Vanomas", 6, 10,
                12, 0, Medium, 0, true));

        card_list.add(new Card("Creator of golems", 3, 4,
                7, 0, Slow, 1, true));

        card_list.add(new Card("Egondon pah", 6, 40,
                27, 0, Slow, 1, true));
    }
    public void createSwamp() {

    }
}
