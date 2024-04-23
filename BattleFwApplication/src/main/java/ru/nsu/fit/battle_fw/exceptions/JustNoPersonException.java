package ru.nsu.fit.battle_fw.exceptions;

public class JustNoPersonException extends Throwable {
    public JustNoPersonException() {
        super();
    }

    public JustNoPersonException(String message) {
        super(message);
    }
}

