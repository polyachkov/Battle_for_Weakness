package ru.nsu.fit.battle_fw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static ru.nsu.fit.battle_fw.Speed.*;

public class UncommonCardList implements Serializable {
    private List<Card> card_list = new ArrayList<>();

    public List<Card> getCard_list() {
        return card_list;
    }

    public void setCard_list(List<Card> card_list) {
        this.card_list = card_list;
    }

    public UncommonCardList(String fraction) {
        if (fraction.equals("Mountains")) {
            createMountains();
        }
        if (fraction.equals("Swamp")) {
            createSwamp();
        }
    }
    private void createMountains() {
        for (int i = 0; i < 6; i++) {
            card_list.add(new Card("Watchman", 1, 6,
                    2, 0, Slow, 1, false));
        }

        for (int i = 0; i < 4; i++) {
            card_list.add(new Card("Cannon", 1, 3,
                    2, 0, Medium, 0, true));
        }

        for (int i = 0; i < 4; i++) {
            card_list.add(new Card("Half-grown worm", 1, 3,
                    1, 0, Slow, 1, false));
        }

        for (int i = 0; i < 4; i++) {
            card_list.add(new Card("Full-grown worm", 2, 6,
                    4, 0, Slow, 1, true));
        }

        for (int i = 0; i < 3; i++) {
            card_list.add(new Card("Kobold with a whip", 1, 2,
                    1, 0, Fast, 1, false));
        }

        for (int i = 0; i < 2; i++) {
            card_list.add(new Card("Stone", 1, 5,
                    2, 0, Medium, 1, false));
        }

        for (int i = 0; i < 2; i++) {
            card_list.add(new Card("Worm employee", 0, 2,
                    1, 3, Slow, 2, true));
        }

        for (int i = 0; i < 2; i++) {
            card_list.add(new Card("The middle barrier", 0, 8,
                    4, 0, None, 0, true));
        }

        card_list.add(new Card("The leg", 3, 2,
                3, 0, Medium, 1, false));

        card_list.add(new Card("The lowest Ostap", 3, 1,
                2, 1, Slow, 2, false));

        card_list.add(new Card("The Bald Bard", 0, 3,
                6, 0, Slow, 1, true));
    }
    public void createSwamp() {

    }
}
