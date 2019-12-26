package me.friwi.tello4j.api.drone;

import me.friwi.tello4j.wifi.impl.WifiDrone;

public class WifiDroneFactory {
    public TelloDrone build() {
        return new WifiDrone();
    }
}
