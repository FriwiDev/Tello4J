package me.friwi.tello4j.wifi.model.command;

import me.friwi.tello4j.api.exception.TelloCustomCommandException;
import me.friwi.tello4j.api.exception.TelloGeneralCommandException;
import me.friwi.tello4j.api.exception.TelloNoValidIMUException;
import me.friwi.tello4j.wifi.impl.response.CommandResultType;
import me.friwi.tello4j.wifi.impl.response.TelloCommandResultResponse;
import me.friwi.tello4j.wifi.model.response.TelloResponse;

public abstract class TelloResultCommand extends TelloCommand {
    private String cmd;

    public TelloResultCommand(String cmd) {
        this.cmd = cmd;
    }

    public String serializeCommand() {
        return this.cmd;
    }

    public TelloResponse buildResponse(String data) throws TelloGeneralCommandException, TelloNoValidIMUException, TelloCustomCommandException {
        TelloCommandResultResponse response = new TelloCommandResultResponse(this, data);
        if (response.getCommandResultType() == CommandResultType.ERROR) {
            if(response.getMessage().equalsIgnoreCase("error"))throw new TelloGeneralCommandException();
            if(response.getMessage().equalsIgnoreCase("error No valid imu"))throw new TelloNoValidIMUException();
            throw new TelloCustomCommandException("Error while executing command \"" + serializeCommand() + "\": " + response.getMessage(), response.getMessage());
        }
        return response;
    }
}
