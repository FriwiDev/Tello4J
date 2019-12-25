package me.friwi.tello4j.wifi.model.response;

import me.friwi.tello4j.wifi.impl.response.CommandResultType;
import me.friwi.tello4j.wifi.model.command.TelloCommand;

public class TelloResponse {
    protected CommandResultType commandResultType;
    private TelloCommand command;
    private String message;

    public TelloResponse(TelloCommand command, CommandResultType commandResultType, String message) {
        this.command = command;
        this.commandResultType = commandResultType;
        this.message = message;
    }

    public TelloCommand getCommand() {
        return command;
    }

    public CommandResultType getCommandResultType() {
        return commandResultType;
    }

    public String getMessage() {
        return message;
    }
}
