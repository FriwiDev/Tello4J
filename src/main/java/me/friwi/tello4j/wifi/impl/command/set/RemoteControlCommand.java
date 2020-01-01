/*
 * Copyright 2020 Fritz Windisch
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
