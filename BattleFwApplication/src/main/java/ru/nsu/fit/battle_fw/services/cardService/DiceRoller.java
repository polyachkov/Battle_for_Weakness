package ru.nsu.fit.battle_fw.services.cardService;

import java.util.Random;

public class DiceRoller {
    private static final Random random = new Random();

    public static int rollDice() {
        return random.nextInt(6) + 1;
    }
}