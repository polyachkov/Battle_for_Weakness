package ru.nsu.fit.battle_fw.exceptions;

public class BadCellException extends Throwable {
    public BadCellException() {
        super();
    }

    public BadCellException(String message) {
        super(message);
    }
}