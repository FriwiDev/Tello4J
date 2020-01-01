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
