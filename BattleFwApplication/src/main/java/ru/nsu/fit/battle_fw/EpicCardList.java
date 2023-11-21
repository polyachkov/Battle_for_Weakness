package ru.nsu.fit.battle_fw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static ru.nsu.fit.battle_fw.Speed.*;

public class EpicCardList implements Serializable {
    private List<Card> card_list = new ArrayList<>();

    public List<Card> getCard_list() {
        return card_list;
    }

    public void setCard_list(List<Card> card_list) {
        this.card_list = card_list;
    }

    public EpicCardList(String fraction) {
        if (fraction.equals("Mountains")) {
            createMountains();
        }
        if (fraction.equals("Swamp")) {
            createSwamp();
        }
    }
    private void createMountains() {
        for (int i = 0; i < 3; i++) {
            card_list.add(new Card("Stone worm", 2, 13,
                    8, 0, Slow, 1, true));
        }

        for (int i = 0; i < 2; i++) {
            card_list.add(new Card("The gnome general", 3, 7,
                    5, 0, Slow, 1, true));
        }

        for (int i = 0; i < 2; i++) {
            card_list.add(new Card("Kobold with explosives", 1, 2,
                    5, 0, Fast, 1, true));
        }

        for (int i = 0; i < 2; i++) {
            card_list.add(new Card("Old watchman", 1, 4,
                    8, 0, Slow, 1, true));
        }

        for (int i = 0; i < 2; i++) {
            card_list.add(new Card("Fat turtle", 1, 30,
                    12, 0, Fast, 1, true));
        }

        card_list.add(new Card("Strogach", 5, 5,
                12, 3, Medium, 1, true));

        card_list.add(new Card("The Bald Mountain", 0, 15,
                12, 0, None, 0, true));

        card_list.add(new Card("The highest Ostap", 4, 14,
                10, 0, Medium, 2, false));

        card_list.add(new Card("The kobold king", 3, 3,
                6, 3, Fast, 1, true));

        card_list.add(new Card("Vanomas sprout", 4, 5,
                7, 0, Medium, 0, true));
    }
    public void createSwamp() {

    }
}
