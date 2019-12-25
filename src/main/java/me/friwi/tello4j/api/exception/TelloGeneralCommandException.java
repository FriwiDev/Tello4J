package me.friwi.tello4j.api.exception;

import me.friwi.tello4j.wifi.model.TelloSDKValues;

public class TelloGeneralCommandException extends TelloCommandException {
    public TelloGeneralCommandException(Exception parent) {
        super(TelloSDKValues.GENERAL_ERROR, parent);
    }

    public TelloGeneralCommandException() {
        super(TelloSDKValues.GENERAL_ERROR);
    }
}
