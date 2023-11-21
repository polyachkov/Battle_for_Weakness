package ru.nsu.fit.battle_fw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static ru.nsu.fit.battle_fw.Speed.*;

public class RareCardList implements Serializable {
    private List<Card> card_list = new ArrayList<>();

    public List<Card> getCard_list() {
        return card_list;
    }

    public void setCard_list(List<Card> card_list) {
        this.card_list = card_list;
    }

    public RareCardList(String fraction) {
        if (fraction.equals("Mountains")) {
            createMountains();
        }
        if (fraction.equals("Swamp")) {
            createSwamp();
        }
    }
    private void createMountains() {
        for (int i = 0; i < 3; i++) {
            card_list.add(new Card("Golem", 1, 25,
                    13, 0, Slow, 1, true));
        }

        for (int i = 0; i < 3; i++) {
            card_list.add(new Card("Golem podserun", 4, 1,
                    3, 1, Medium, 2, false));
        }

        for (int i = 0; i < 2; i++) {
            card_list.add(new Card("Mix golem", 3, 10,
                    7, 0, Slow, 1, false));
        }

        for (int i = 0; i < 3; i++) {
            card_list.add(new Card("Golem's head", 0, 3,
                    4, 0, None, 0, true));
        }

        for (int i = 0; i < 3; i++) {
            card_list.add(new Card("Tavern owner", 0, 3,
                    2, 0, None, 1, true));
        }

        for (int i = 0; i < 2; i++) {
            card_list.add(new Card("The highest barrier", 0, 16,
                    8, 0, None, 0, true));
        }

        for (int i = 0; i < 2; i++) {
            card_list.add(new Card("Kobold master", 2, 2,
                    5, 5, Fast, 1, false));
        }

        for (int i = 0; i < 2; i++) {
            card_list.add(new Card("The leader of the Bald Mountain", 2, 3,
                    3, 2, Fast, 1, false));
        }

        card_list.add(new Card("The rat king", 6, 3,
                5, 0, Slow, 1, false));

        card_list.add(new Card("Sigdot", 1, 10,
                8, 1, Medium, 2, true));
    }
    public void createSwamp() {

    }
}
