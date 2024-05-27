package ru.nsu.fit.battle_fw.exceptions;

public class AlreadyFightException extends Throwable {
    public AlreadyFightException() {
        super();
    }

    public AlreadyFightException(String message) {
        super(message);
    }
}
