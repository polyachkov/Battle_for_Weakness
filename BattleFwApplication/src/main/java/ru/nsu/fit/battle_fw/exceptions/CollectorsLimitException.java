package ru.nsu.fit.battle_fw.exceptions;

public class CollectorsLimitException extends Throwable {
    public CollectorsLimitException() {
        super();
    }

    public CollectorsLimitException(String message) {
        super(message);
    }
}
