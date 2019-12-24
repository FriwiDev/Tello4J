package me.friwi.tello4j.wifi.impl.command.control;

import me.friwi.tello4j.api.world.MovementDirection;
import me.friwi.tello4j.util.TelloArgumentVerifier;
import me.friwi.tello4j.wifi.model.command.ControlCommand;

public class FlyDirectionCommand extends ControlCommand {
    private MovementDirection direction;
    private int amount;

    public FlyDirectionCommand(MovementDirection direction, int amount) {
        super(direction.getCommand() + " " + amount);
        TelloArgumentVerifier.checkRange(amount, 20, 500, "The amount of %xcm exceeded the allowed range of [%min,%max]");
        this.direction = direction;
        this.amount = amount;
    }
}
