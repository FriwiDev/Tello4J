# Tello4J

A small Java library that connects your JVM to your tello drone. Provides access to tello sdk commands, state messages and video feed.

## Getting started

Include the library in your project by adding it as a maven dependency:
```
<dependency>
    <groupId>me.friwi</groupId>
    <artifactId>tello4j</artifactId>
    <version>1.0.2</version>
</dependency>
```
...or download the .jar [here](https://search.maven.org/artifact/me.friwi/tello4j/1.0.2/jar) (You will need to include JavaCV 1.5.2 in your project for the library to work). 

Use the API to send instructions to your drone, receive state updates and video frames from the camera of your drone:
```java
public class FlightPlanExample {
    public static void main(String args[]) {
        //Initialize a wifi drone
        try (TelloDrone drone = new WifiDroneFactory().build()) {
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
        } catch (TelloNetworkException e) {
            if(e instanceof TelloConnectionTimedOutException){
                //The connection timed out because we did not send commands within the last 15 seconds.
                //The drone safely lands then.
                e.printStackTrace();
            }else {
                //Errors that occurred on the network side (e.g. parsing errors, connect error)
                //can be observed here
                e.printStackTrace();
            }
        } catch (TelloNoValidIMUException e) {
            //Commands that move the drone, apart from "takeoff", "land"
            //and "remote control" can fail due to no valid imu data.
            //This mainly happens when the ground under the drone does not
            //provide enough textual features for the drone to navigate properly.
            e.printStackTrace();
        } catch (TelloGeneralCommandException e) {
            //This exception is thrown when the drone reported an unspecified error
            //to the api. This can happen when the battery is too low for a
            //command to be executed
            e.printStackTrace();
        } catch (TelloCustomCommandException e) {
            //This exception is thrown when the drone reported an error with description
            //to the api. The reason can be obtained with e.getReason()
            e.printStackTrace();
        } catch (TelloCommandTimedOutException e) {
            //This exception is thrown when a command is not answered by the drone for 20 seconds
            e.printStackTrace();
        }
    }
}
```

## Documentation

Visit the javadoc [here](https://friwi.me/tello4j/javadoc/).
