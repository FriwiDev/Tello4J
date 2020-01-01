package me.friwi.tello4j.api.exception;

/**
 * Class that unifies all exceptions thrown by this library.
 *
 * @author Fritz Windisch
 */
public class TelloException extends Exception {
    public TelloException(String msg, Exception parent) {
        super(msg, parent);
    }

    public TelloException(String msg) {
        super(msg);
    }
}
