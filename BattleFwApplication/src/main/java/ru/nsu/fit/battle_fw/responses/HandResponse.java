package ru.nsu.fit.battle_fw.responses;

import ru.nsu.fit.battle_fw.database.model.Card;

import java.util.List;

public class HandResponse {
    private List<Card> cards;

    public HandResponse(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
