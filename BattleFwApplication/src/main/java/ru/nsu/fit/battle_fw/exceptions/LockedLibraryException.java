package ru.nsu.fit.battle_fw.exceptions;

public class LockedLibraryException extends Throwable {
    public LockedLibraryException() {
        super();
    }

    public LockedLibraryException(String message) {
        super(message);
    }
}