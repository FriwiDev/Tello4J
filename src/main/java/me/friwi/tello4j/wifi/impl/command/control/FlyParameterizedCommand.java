package me.friwi.tello4j.wifi.impl.command.control;

import me.friwi.tello4j.util.TelloArgumentVerifier;
import me.friwi.tello4j.wifi.model.command.ControlCommand;

public class FlyParameterizedCommand extends ControlCommand {
    private int x, y, z, speed;

    public FlyParameterizedCommand(int x, int y, int z, int speed) {
        super("go " + x + " " + y + " " + z + " " + speed);
        TelloArgumentVerifier.checkRangeAllowNegativeOne(new int[]{x, y, z}, 20, 500, "At least one x, y or z needs to be within [%min,%max]");
        TelloArgumentVerifier.checkRangeAllowNegativeAll(new int[]{x, y, z}, 0, 500, "All values for x, y and z need to be within [%min,%max]");
        TelloArgumentVerifier.checkRange(speed, 10, 100, "Speed of %x exceeds [%min,%max]");
        this.x = x;
        this.y = y;
        this.z = z;
        this.speed = speed;
    }
}
