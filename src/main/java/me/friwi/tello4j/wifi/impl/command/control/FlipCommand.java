package me.friwi.tello4j.wifi.impl.command.control;

import me.friwi.tello4j.api.world.FlipDirection;
import me.friwi.tello4j.wifi.model.command.ControlCommand;

public class FlipCommand extends ControlCommand {
    private FlipDirection direction;

    public FlipCommand(FlipDirection direction) {
        super("flip " + direction.getCommand());
        this.direction = direction;
    }
}
