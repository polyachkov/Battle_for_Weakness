package ru.nsu.fit.battle_fw.dto.hand;


import ru.nsu.fit.battle_fw.database.model.Card;

import java.util.List;

public class HandMapper {
    public static HandDto toDTO(List<Card> cards) {
        return new HandDto(cards);
    }
}
