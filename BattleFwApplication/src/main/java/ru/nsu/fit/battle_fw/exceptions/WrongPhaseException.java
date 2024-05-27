package ru.nsu.fit.battle_fw.exceptions;

public class WrongPhaseException extends Throwable {
    public WrongPhaseException() {
        super();
    }

    public WrongPhaseException(String message) {
        super(message);
    }
}
