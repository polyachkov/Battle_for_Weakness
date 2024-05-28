package ru.nsu.fit.battle_fw.exceptions;

public class GameIsAlreadyEndedException extends Throwable {
    public GameIsAlreadyEndedException() {
        super();
    }

    public GameIsAlreadyEndedException(String message) {
        super(message);
    }
}
