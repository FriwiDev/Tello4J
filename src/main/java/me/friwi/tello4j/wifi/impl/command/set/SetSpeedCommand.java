package me.friwi.tello4j.wifi.impl.command.set;

import me.friwi.tello4j.util.TelloArgumentVerifier;
import me.friwi.tello4j.wifi.model.command.SetCommand;

public class SetSpeedCommand extends SetCommand {
    private int speed;

    public SetSpeedCommand(int speed) {
        super("speed " + speed);
        TelloArgumentVerifier.checkRange(speed, 10, 100, "Speed of %x exceeds [%min,%max]");
        this.speed = speed;
    }
}
