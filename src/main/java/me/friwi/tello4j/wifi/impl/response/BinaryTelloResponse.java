package me.friwi.tello4j.wifi.impl.response;

import me.friwi.tello4j.wifi.model.command.TelloCommand;
import me.friwi.tello4j.wifi.model.response.TelloResponse;

public class BinaryTelloResponse extends TelloResponse {
    public BinaryTelloResponse(TelloCommand command, CommandResultType commandResultType, String message) {
        super(command, commandResultType, message);
    }
}
