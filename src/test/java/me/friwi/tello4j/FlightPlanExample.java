package me.friwi.tello4j;

import me.friwi.tello4j.api.drone.DroneFactory;
import me.friwi.tello4j.api.drone.DroneType;
import me.friwi.tello4j.api.drone.TelloDrone;
import me.friwi.tello4j.api.exception.TelloException;
import me.friwi.tello4j.api.video.VideoWindow;
import me.friwi.tello4j.api.world.FlipDirection;

public class FlightPlanExample {
    public static void main(String args[]) throws TelloException {
        //Initialize a wifi drone
        try (TelloDrone drone = new DroneFactory(DroneType.WIFI).build()) {
            drone.connect();
            //Subscribe to state updates of our drone (e.g. current speed, attitude)
            drone.addStateListener((o, n) -> {
                //Do sth when switching from one to another state
            });
            //Create a video window to see things with our drones eyes
            drone.addVideoListener(new VideoWindow());
            //...or use a custom video listener to process the single frames
            drone.addVideoListener(frame -> {
                //Do sth when we received a frame
            });
            //...and tell the drone to turn on the stream
            drone.setStreaming(true);
            //Now perform a flight plan
            drone.takeoff();
            drone.forward(30);
            drone.turnLeft(90);
            drone.forward(30);
            drone.backward(30);
            drone.flip(FlipDirection.FORWARD);
            drone.turnRight(90);
            drone.forward(30);
            drone.land();
            //Prevent our drone from being closed
            while (true) ;
        }
    }
}
