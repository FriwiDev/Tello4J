package me.friwi.tello4j.api.exception;

public class TelloException extends Exception {
    public TelloException(String msg, Exception parent) {
        super(msg, parent);
    }

    public TelloException(String msg) {
        super(msg);
    }
}
