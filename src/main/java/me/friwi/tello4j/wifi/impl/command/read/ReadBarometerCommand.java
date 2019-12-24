package me.friwi.tello4j.wifi.impl.command.read;

import me.friwi.tello4j.wifi.model.command.ReadCommand;

public class ReadBarometerCommand extends ReadCommand {
    public ReadBarometerCommand() {
        super("baro");
    }
}
