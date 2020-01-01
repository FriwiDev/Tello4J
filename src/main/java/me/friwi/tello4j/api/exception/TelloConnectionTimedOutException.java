package me.friwi.tello4j.api.exception;

import me.friwi.tello4j.wifi.model.TelloSDKValues;

/**
 * This exception is thrown when no commands were sent to the tello drone within the last 15 seconds.
 *
 * @author Fritz Windisch
 */
public class TelloConnectionTimedOutException extends TelloNetworkException {
    public TelloConnectionTimedOutException(Exception parent) {
        super(TelloSDKValues.CONNECTION_TIMED_OUT, parent);
    }

    public TelloConnectionTimedOutException() {
        super(TelloSDKValues.CONNECTION_TIMED_OUT);
    }
}
