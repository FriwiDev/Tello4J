package me.friwi.tello4j.api.exception;

import me.friwi.tello4j.wifi.model.TelloSDKValues;

public class TelloCommandTimedOutException extends TelloCommandException {
    public TelloCommandTimedOutException(Exception parent) {
        super(TelloSDKValues.COMMAND_TIMED_OUT, parent);
    }

    public TelloCommandTimedOutException() {
        super(TelloSDKValues.COMMAND_TIMED_OUT);
    }
}
