package me.friwi.tello4j.wifi.model.command;

import me.friwi.tello4j.api.exception.TelloCustomCommandException;
import me.friwi.tello4j.api.exception.TelloGeneralCommandException;
import me.friwi.tello4j.api.exception.TelloNetworkException;
import me.friwi.tello4j.api.exception.TelloNoValidIMUException;
import me.friwi.tello4j.wifi.impl.response.CommandResultType;
import me.friwi.tello4j.wifi.impl.response.TelloReadCommandResponse;
import me.friwi.tello4j.wifi.model.response.TelloResponse;

public abstract class ReadCommand extends TelloCommand {
    private String command;

    public ReadCommand(String command) {
        this.command = command + "?";
    }

    @Override
    public String serializeCommand() {
        return this.command;
    }

    @Override
    public TelloResponse buildResponse(String data) throws TelloNetworkException, TelloGeneralCommandException, TelloNoValidIMUException, TelloCustomCommandException {
        TelloReadCommandResponse response = new TelloReadCommandResponse(this, data);
        if (response.getCommandResultType() == CommandResultType.ERROR) {
            if(response.getMessage().equalsIgnoreCase("error"))throw new TelloGeneralCommandException();
            if(response.getMessage().equalsIgnoreCase("error No valid imu"))throw new TelloNoValidIMUException();
            throw new TelloCustomCommandException("Error while executing command \"" + serializeCommand() + "\": " + response.getMessage(), response.getMessage());
        }
        return response;
    }
}
