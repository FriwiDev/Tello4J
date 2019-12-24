package me.friwi.tello4j.api.exception;

public class TelloCommandException extends TelloException {
    public TelloCommandException(String msg, Exception parent) {
        super(msg, parent);
    }

    public TelloCommandException(String msg) {
        super(msg);
    }
}
