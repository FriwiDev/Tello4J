package me.friwi.tello4j.api.drone;

import me.friwi.tello4j.wifi.impl.WifiDrone;

/**
 * A factory to create a new {@link TelloDrone}, which is connected by wifi
 *
 * @author Fritz Windisch
 */
public class WifiDroneFactory {
    /**
     * Builds a new {@link TelloDrone}, which is connected by wifi
     *
     * @return new {@link TelloDrone}
     */
    public TelloDrone build() {
        return new WifiDrone();
    }
}
