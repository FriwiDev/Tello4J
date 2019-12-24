package me.friwi.tello4j.api.exception;

public class TelloNetworkException extends TelloException {
    public TelloNetworkException(String msg, Exception parent) {
        super(msg, parent);
    }

    public TelloNetworkException(String msg) {
        super(msg);
    }
}
