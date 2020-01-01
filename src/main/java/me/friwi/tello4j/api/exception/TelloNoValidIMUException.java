package me.friwi.tello4j.api.exception;

import me.friwi.tello4j.wifi.model.TelloSDKValues;

/**
 * This exception is thrown, when the ground under the drone does not provide enough
 * textual features for the drone to orientate itself.
 *
 * @author Fritz Windisch
 */
public class TelloNoValidIMUException extends TelloCommandException {
    public TelloNoValidIMUException(Exception parent) {
        super(TelloSDKValues.NO_VALID_IMU_MSG, parent);
    }

    public TelloNoValidIMUException() {
        super(TelloSDKValues.NO_VALID_IMU_MSG);
    }
}
