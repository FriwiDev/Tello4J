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

public class FlyCurveCommand extends ControlCommand {
    private int x1, x2, y1, y2, z1, z2, speed;

    public FlyCurveCommand(int x1, int x2, int y1, int y2, int z1, int z2, int speed) {
        super("curve " + x1 + " " + y1 + " " + z1 + " " + x2 + " " + y2 + " " + z2 + " " + speed);
        TelloArgumentVerifier.checkRangeAllowNegativeOne(new int[]{x1 - x2, y1 - y2, z1 - z2}, 20, 500, "At least one x, y or z needs to be within [%min,%max]");
        TelloArgumentVerifier.checkRangeAllowNegativeAll(new int[]{x1 - x2, y1 - y2, z1 - z2}, 0, 500, "All values for x, y and z need to be within [%min,%max]");
        TelloArgumentVerifier.checkRange(speed, 10, 100, "Speed of %x exceeds [%min,%max]");
        //TODO check arc radius
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.speed = speed;
    }
}
