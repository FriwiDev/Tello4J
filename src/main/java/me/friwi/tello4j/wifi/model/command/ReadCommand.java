package me.friwi.tello4j.wifi.model.command;

import me.friwi.tello4j.api.exception.TelloCommandException;
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
    public TelloResponse buildResponse(String data) throws TelloCommandException {
        TelloReadCommandResponse response = new TelloReadCommandResponse(this, data);
        if (response.getCommandResultType() == CommandResultType.ERROR) {
            throw response.generateException();
        }
        return response;
    }
}
