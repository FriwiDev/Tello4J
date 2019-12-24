package me.friwi.tello4j.wifi.impl.command.set;

import me.friwi.tello4j.util.TelloArgumentVerifier;
import me.friwi.tello4j.wifi.model.command.SetCommand;

public class RemoteControlCommand extends SetCommand {
    private int leftRight, forwardBackward, upDown, yaw;

    public RemoteControlCommand(int leftRight, int forwardBackward, int upDown, int yaw) {
        super("rc " + leftRight + " " + forwardBackward + " " + upDown + " " + yaw);
        TelloArgumentVerifier.checkRange(leftRight, -100, 100, "Left/right of %x exceeds [%min,%max]");
        TelloArgumentVerifier.checkRange(forwardBackward, -100, 100, "Forward/backward of %x exceeds [%min,%max]");
        TelloArgumentVerifier.checkRange(upDown, -100, 100, "Up/down of %x exceeds [%min,%max]");
        TelloArgumentVerifier.checkRange(yaw, -100, 100, "Yaw of %x exceeds [%min,%max]");
        this.leftRight = leftRight;
        this.forwardBackward = forwardBackward;
        this.upDown = upDown;
        this.yaw = yaw;
    }
}
