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
