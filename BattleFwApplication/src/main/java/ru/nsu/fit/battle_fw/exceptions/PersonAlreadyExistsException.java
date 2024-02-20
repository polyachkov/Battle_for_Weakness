package ru.nsu.fit.battle_fw.exceptions;

public class PersonAlreadyExistsException extends Throwable {
    public PersonAlreadyExistsException() {
        super();
    }

    public PersonAlreadyExistsException(String message) {
        super(message);
    }
}
