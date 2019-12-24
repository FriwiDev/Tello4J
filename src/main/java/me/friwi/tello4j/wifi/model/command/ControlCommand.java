package me.friwi.tello4j.wifi.model.command;

public abstract class ControlCommand extends TelloResultCommand {
    public ControlCommand(String cmd) {
        super(cmd);
    }
}
