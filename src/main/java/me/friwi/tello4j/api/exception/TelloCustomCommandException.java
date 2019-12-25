package me.friwi.tello4j.api.exception;

public class TelloCustomCommandException extends TelloCommandException {
    private String reason;

    public TelloCustomCommandException(String msg, String reason, Exception parent) {
        super(msg, parent);
    }

    public TelloCustomCommandException(String msg, String reason) {
        super(msg);
    }

    public String getReason() {
        return reason;
    }
}
