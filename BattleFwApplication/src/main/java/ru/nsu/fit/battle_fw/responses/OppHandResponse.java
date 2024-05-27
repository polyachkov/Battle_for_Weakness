package ru.nsu.fit.battle_fw.responses;

public class OppHandResponse {
    Integer cards_cnt;

    public OppHandResponse(Integer cards_cnt) {
        this.cards_cnt = cards_cnt;
    }

    public Integer getCards_cnt() {
        return cards_cnt;
    }

    public void setCards_cnt(Integer cards_cnt) {
        this.cards_cnt = cards_cnt;
    }
}
