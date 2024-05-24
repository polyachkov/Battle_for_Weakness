package ru.nsu.fit.battle_fw.exceptions;

public class NotYourTurnException extends Throwable {
    public NotYourTurnException() {
        super();
    }

    public NotYourTurnException(String message) {
        super(message);
    }
}