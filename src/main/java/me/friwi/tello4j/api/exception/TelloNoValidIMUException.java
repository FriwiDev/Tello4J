package me.friwi.tello4j.api.exception;

import me.friwi.tello4j.wifi.model.TelloSDKValues;

public class TelloNoValidIMUException extends TelloCommandException {
    public TelloNoValidIMUException(Exception parent) {
        super(TelloSDKValues.NO_VALID_IMU_MSG, parent);
    }

    public TelloNoValidIMUException() {
        super(TelloSDKValues.NO_VALID_IMU_MSG);
    }
}
