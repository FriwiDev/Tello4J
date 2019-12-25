package me.friwi.tello4j.wifi.model.command;

import me.friwi.tello4j.api.exception.*;
import me.friwi.tello4j.wifi.model.response.TelloResponse;

public abstract class TelloCommand {
    private TelloResponse response;
    private TelloException exception;

    public abstract String serializeCommand();

    public abstract TelloResponse buildResponse(String data) throws TelloGeneralCommandException, TelloNoValidIMUException, TelloCustomCommandException, TelloNetworkException;

    public TelloResponse getResponse() {
        return response;
    }

    public void setResponse(TelloResponse response) {
        this.response = response;
    }

    public TelloException getException() {
        return exception;
    }

    public void setException(TelloException exception) {
        this.exception = exception;
    }
}
