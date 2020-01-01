package me.friwi.tello4j.api.exception;

import me.friwi.tello4j.wifi.model.TelloSDKValues;

/**
 * This exception is thrown when the tello drone does not answer in time.
 *
 * @author Fritz Windisch
 */
public class TelloCommandTimedOutException extends TelloCommandException {
    public TelloCommandTimedOutException(Exception parent) {
        super(TelloSDKValues.COMMAND_TIMED_OUT, parent);
    }

    public TelloCommandTimedOutException() {
        super(TelloSDKValues.COMMAND_TIMED_OUT);
    }
}
