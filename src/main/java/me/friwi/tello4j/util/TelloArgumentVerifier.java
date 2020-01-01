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

package me.friwi.tello4j.util;

public class TelloArgumentVerifier {
    public static void checkRange(int x, int min, int max, String description) {
        if (x < min || x > max) {
            throw new IllegalArgumentException(description.replace("%x", String.valueOf(x))
                    .replace("%min", String.valueOf(min))
                    .replace("%max", String.valueOf(max)));
        }
    }

    public static void checkRangeAllowNegative(int x, int min, int max, String description) {
        checkRange(x < 0 ? -x : x, min, max, description);
    }

    public static void checkRangeAllowNegativeOne(int[] xValues, int min, int max, String description) {
        for (int x : xValues) {
            if (x < 0) x = -x;
            if (x >= min && x <= max) {
                return; //Found one
            }
        }
        //Found none
        throw new IllegalArgumentException(description
                .replace("%min", String.valueOf(min))
                .replace("%max", String.valueOf(max)));
    }

    public static void checkRangeAllowNegativeAll(int[] xValues, int min, int max, String description) {
        for (int x : xValues) {
            if (x < 0) x = -x;
            if (x < min || x > max) {
                throw new IllegalArgumentException(description
                        .replace("%min", String.valueOf(min))
                        .replace("%max", String.valueOf(max)));
            }
        }
    }
}
