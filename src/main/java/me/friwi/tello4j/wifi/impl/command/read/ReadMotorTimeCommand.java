package me.friwi.tello4j.wifi.impl.command.read;

import me.friwi.tello4j.wifi.model.command.ReadCommand;

public class ReadMotorTimeCommand extends ReadCommand {
    public ReadMotorTimeCommand() {
        super("time");
    }
}
