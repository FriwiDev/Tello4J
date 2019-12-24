package me.friwi.tello4j.wifi.impl.command.read;

import me.friwi.tello4j.wifi.model.command.ReadCommand;

public class ReadBatteryCommand extends ReadCommand {
    public ReadBatteryCommand() {
        super("battery");
    }
}
