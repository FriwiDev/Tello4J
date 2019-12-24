package me.friwi.tello4j.wifi.impl.command.control;

import me.friwi.tello4j.api.world.TurnDirection;
import me.friwi.tello4j.util.TelloArgumentVerifier;
import me.friwi.tello4j.wifi.model.command.ControlCommand;

public class TurnCommand extends ControlCommand {
    private TurnDirection direction;
    private int amount;

    public TurnCommand(TurnDirection direction, int amount) {
        super(direction.getCommand() + " " + amount);
        TelloArgumentVerifier.checkRange(amount, 1, 3600, "The amount of %x degrees exceeded the allowed range of [%min,%max]");
        this.direction = direction;
        this.amount = amount;
    }
}
