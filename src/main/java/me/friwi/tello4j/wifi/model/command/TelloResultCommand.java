package me.friwi.tello4j.wifi.model.command;

import me.friwi.tello4j.api.exception.TelloCommandException;
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

    public TelloResponse buildResponse(String data) throws TelloCommandException {
        TelloCommandResultResponse response = new TelloCommandResultResponse(this, data);
        if (response.getCommandResultType() == CommandResultType.ERROR) {
            throw response.generateException();
        }
        return response;
    }
}
