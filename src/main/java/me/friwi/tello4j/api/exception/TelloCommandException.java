package me.friwi.tello4j.api.exception;

/**
 * Class that unifies all possible exceptions that can occur when executing a command.
 * Please note, that {@link TelloNetworkException}s can also be thrown.
 *
 * @author Fritz Windisch
 */
public class TelloCommandException extends TelloException {
    public TelloCommandException(String msg, Exception parent) {
        super(msg, parent);
    }

    public TelloCommandException(String msg) {
        super(msg);
    }
}
