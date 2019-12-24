package me.friwi.tello4j.api.drone;

import me.friwi.tello4j.wifi.impl.WifiDrone;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DroneFactory {
    private DroneType type;

    public DroneFactory(DroneType type) {
        this.type = type;
    }

    public DroneType getType() {
        return type;
    }

    public TelloDrone build() {
        switch (type) {
            case WIFI:
                return new WifiDrone();
            case SIMULATED:
                throw new NotImplementedException();
            default:
                throw new NotImplementedException();
        }
    }
}
