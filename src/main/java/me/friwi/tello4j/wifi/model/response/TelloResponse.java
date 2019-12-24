package me.friwi.tello4j.wifi.model.response;

import me.friwi.tello4j.api.exception.TelloCommandException;
import me.friwi.tello4j.api.exception.TelloNoValidIMUException;
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

    public TelloCommandException generateException() {
        if(this.getMessage().equalsIgnoreCase("error No valid imu"))return new TelloNoValidIMUException();
        return new TelloCommandException("Error while executing command \"" + getCommand().serializeCommand() + "\": " + this.getMessage());
    }
}
