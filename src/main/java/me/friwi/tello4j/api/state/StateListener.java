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

package me.friwi.tello4j.api.state;

import me.friwi.tello4j.api.drone.TelloDrone;

/**
 * Listener used to receive state updates from the {@link TelloDrone}
 *
 * @author Fritz Windisch
 */
public interface StateListener {
    /**
     * Method that is called, when the state of the {@link TelloDrone} is updated
     *
     * @param oldState The previous state
     * @param newState The now updated state
     */
    void onStateChanged(TelloDroneState oldState, TelloDroneState newState);
}
