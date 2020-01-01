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

package me.friwi.tello4j.api.video;

import me.friwi.tello4j.api.drone.TelloDrone;

/**
 * A VideoListener receives video data in the form of {@link TelloVideoFrame}s.
 *
 * @author Fritz Windisch
 */
public interface VideoListener {
    /**
     * After adding the listener to the {@link TelloDrone}, this method will be called
     * with each image received from the drone.
     *
     * @param frame
     */
    void onFrameReceived(TelloVideoFrame frame);
}
