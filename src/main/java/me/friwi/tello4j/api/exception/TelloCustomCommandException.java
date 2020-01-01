package me.friwi.tello4j.api.exception;


/**
 * This exception is thrown, when the drone replies to a command with a custom error message.
 * The message can be obtained by calling {@link #getReason()}
 *
 * @author Fritz Windisch
 */
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
