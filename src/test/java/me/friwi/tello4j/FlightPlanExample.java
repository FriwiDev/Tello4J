package me.friwi.tello4j;

import me.friwi.tello4j.api.drone.DroneFactory;
import me.friwi.tello4j.api.drone.DroneType;
import me.friwi.tello4j.api.drone.TelloDrone;
import me.friwi.tello4j.api.exception.TelloException;
import me.friwi.tello4j.api.exception.TelloNoValidIMUException;
import me.friwi.tello4j.api.video.TelloVideoExportType;
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
            //...[optional] select which type of frame you want to receive
            // a) [default] BUFFERED_IMAGE: Receive buffered images in each TelloVideoFrame
            // b) JAVACV_FRAME: Receive javacv frames in each TelloVideoFrame to further process them
            // c) BOTH: Receive both frame types in each TelloVideoFrame
            drone.setVideoExportType(TelloVideoExportType.BUFFERED_IMAGE);
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
            drone.backward(30);
            drone.land();
            //Prevent our drone from being closed
            //(the drone is automatically closed when leaving the try-with-resource block)
            while (true) ;
        }catch(TelloNoValidIMUException e){
            //Commands that move the drone, apart from "takeoff", "land"
            //and "remote control" can fail due to no valid imu data.
            //This mainly happens when the ground under the drone does not
            //provide enough textual features for the drone to navigate properly.
            e.printStackTrace();
        }
    }
}
