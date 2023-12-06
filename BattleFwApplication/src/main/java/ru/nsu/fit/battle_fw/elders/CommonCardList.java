package ru.nsu.fit.battle_fw.elders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static ru.nsu.fit.battle_fw.elders.Speed.*;

public class CommonCardList implements Serializable {
    private List<Card> card_list = new ArrayList<>();

    public List<Card> getCard_list() {
        return card_list;
    }

    public void setCard_list(List<Card> card_list) {
        this.card_list = card_list;
    }

    public CommonCardList(String fraction) {
        if (fraction.equals("Mountains")) {
            createMountains();
        }
        if (fraction.equals("Swamp")) {
            createSwamp();
        }
    }
    private void createMountains() {
        for (int i = 0; i < 8; i++) {
            card_list.add(new Card("Kobold", 1, 2,
                    1, 0, Fast, 1, false));
        }

        for (int i = 0; i < 8; i++) {
            card_list.add(new Card("The Bald Mountain Sectarian", 1, 2,
                    1, 2, Medium, 1, false));
        }

        for (int i = 0; i < 4; i++) {
            card_list.add(new Card("Dwarf", 2, 3,
                    2, 0, Slow, 1, false));
        }

        for (int i = 0; i < 4; i++) {
            card_list.add(new Card("Rat", 2, 1,
                    1, 0, Slow, 1, false));
        }

        for (int i = 0; i < 4; i++) {
            card_list.add(new Card("Mice", 2, 1,
                    2, 2, Fast, 2, false));
        }

        for (int i = 0; i < 2; i++) {
            card_list.add(new Card("Mountain goats", 1, 1,
                    2, 4, Fast, 2, false));
        }

        for (int i = 0; i < 2; i++) {
            card_list.add(new Card("Stone turtle", 0, 4,
                    1, 0, None, 1, true));
        }

        for (int i = 0; i < 2; i++) {
            card_list.add(new Card("The lowest barrier", 0, 4,
                    2, 0, None, 0, true));
        }

        card_list.add(new Card("Egorka the hermit", 1, 2,
                1, 1, Medium, 1, false));

        card_list.add(new Card("Proud of the Mountains", 0, 5,
                1, 0, None, 1, false));

        card_list.add(new Card("Stone Crusher", 2, 5,
                3, 0, Slow, 1, true));

        card_list.add(new Card("The richest", 1, 1,
                0, 0, Slow, 1, true));
    }
    public void createSwamp() {

    }
}
