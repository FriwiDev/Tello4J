package me.friwi.tello4j.api.exception;

import me.friwi.tello4j.wifi.model.TelloSDKValues;

/**
 * This exception is thrown, when the drone replies to a command without specifying a reason.
 * This is common, when the battery of the drone is too low to execute a command.
 *
 * @author Fritz Windisch
 */
public class TelloGeneralCommandException extends TelloCommandException {
    public TelloGeneralCommandException(Exception parent) {
        super(TelloSDKValues.GENERAL_ERROR, parent);
    }

    public TelloGeneralCommandException() {
        super(TelloSDKValues.GENERAL_ERROR);
    }
}
