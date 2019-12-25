package me.friwi.tello4j.api.drone;

import me.friwi.tello4j.wifi.impl.WifiDrone;

public class DroneFactory {
    public TelloDrone build() {
        return new WifiDrone();
    }
}
