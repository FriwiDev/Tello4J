package me.friwi.tello4j.api.exception;

/**
 * This exception is thrown when an error occurs while performing network tasks.
 *
 * @author Fritz Windisch
 */
public class TelloNetworkException extends TelloException {
    public TelloNetworkException(String msg, Exception parent) {
        super(msg, parent);
    }

    public TelloNetworkException(String msg) {
        super(msg);
    }
}
