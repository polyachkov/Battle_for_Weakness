package ru.nsu.fit.battle_fw.requests.post;

import java.io.Serializable;
public class PutCardInCellRequest implements Serializable {
    private Integer gameId;
    private Integer cardId;
    private Integer cellId;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getCellId() {
        return cellId;
    }

    public void setCellId(Integer cellId) {
        this.cellId = cellId;
    }
}
