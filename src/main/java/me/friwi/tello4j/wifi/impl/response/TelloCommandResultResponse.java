package me.friwi.tello4j.wifi.impl.response;

import me.friwi.tello4j.wifi.model.command.TelloCommand;
import me.friwi.tello4j.wifi.model.response.TelloResponse;

public class TelloCommandResultResponse extends TelloResponse {
    public TelloCommandResultResponse(TelloCommand command, String message) {
        super(command, message.equalsIgnoreCase("ok") ? CommandResultType.OK : CommandResultType.ERROR, message);
    }
}
