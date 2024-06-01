package ru.nsu.fit.battle_fw.dto.hand;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nsu.fit.battle_fw.database.model.Card;

import java.util.List;

@Data
@AllArgsConstructor
public class HandDto {
    private List<Card> cards;
}
