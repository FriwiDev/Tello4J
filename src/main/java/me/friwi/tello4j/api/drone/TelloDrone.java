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

package me.friwi.tello4j.api.drone;

import me.friwi.tello4j.api.exception.*;
import me.friwi.tello4j.api.state.StateListener;
import me.friwi.tello4j.api.state.TelloDroneState;
import me.friwi.tello4j.api.video.TelloVideoExportType;
import me.friwi.tello4j.api.video.TelloVideoFrame;
import me.friwi.tello4j.api.video.VideoListener;
import me.friwi.tello4j.api.world.FlipDirection;
import me.friwi.tello4j.api.world.MovementDirection;
import me.friwi.tello4j.api.world.TurnDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents all tello drones with their possible sdk actions and their current data.
 * You can construct a tello drone by using a tello drone factory (e.g. {@link me.friwi.tello4j.api.drone.WifiDroneFactory}).
 *
 * @author Fritz Windisch
 */
public abstract class TelloDrone implements AutoCloseable {
    private List<VideoListener> videoListeners = new ArrayList<>();
    private List<StateListener> stateListeners = new ArrayList<>();
    private TelloDroneState cachedState;
    private TelloVideoExportType videoExportType = TelloVideoExportType.BUFFERED_IMAGE;

    /**
     * Disconnects and frees the resources of this tello drone.
     */
    @Override
    public void close() {
        this.disconnect();
    }

    /**
     * Estabilishes a connection with a tello drone at the default address (192.168.10.1). Can only be used once, please construct a new tello drone object
     * when reconnecting.
     * You still need to connect to the tello wifi manually before invoking this call.
     *
     * @throws TelloNetworkException         If communication with the tello drone produced invalid input or
     *                                       the wifi network was set up incorrectly. Also thrown if you called
     *                                       {@link #connect()} twice.
     * @throws TelloCommandTimedOutException If the tello drone did not answer in time.
     * @throws TelloCustomCommandException   If the tello drone answered with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the tello drone answered with an unspecified error (possibly battery too low).
     */
    public abstract void connect() throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Estabilishes a connection with a tello drone at a custom address. Can only be used once, please construct a new tello drone object
     * when reconnecting.
     * You still need to connect to the tello wifi manually before invoking this call.
     *
     * @param remoteAddr The remote address or hostname your tello uses. If unsure, use {@link #connect()} to connect to the default destination IP.
     *
     * @throws TelloNetworkException         If communication with the tello drone produced invalid input or
     *                                       the wifi network was set up incorrectly. Also thrown if you called
     *                                       {@link #connect()} twice.
     * @throws TelloCommandTimedOutException If the tello drone did not answer in time.
     * @throws TelloCustomCommandException   If the tello drone answered with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the tello drone answered with an unspecified error (possibly battery too low).
     */
    public abstract void connect(String remoteAddr) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Disconnects from this tello drone. Does not close this drones resources.
     */
    public abstract void disconnect();

    /**
     * Retrieves the connection state of this drone.
     * When the drone times out after not sending commands for 15 seconds, the drone automatically lands safely and
     * closes the connection.
     *
     * @return true: if drone was already connected, false: otherwise
     */
    public abstract boolean isConnected();

    /**
     * Instructs this drone to take off.
     *
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error (possibly battery too low).
     */
    public abstract void takeoff() throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Instructs this drone to land.
     *
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract void land() throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Fetches whether streaming is currently enabled on this drone
     *
     * @return true: if streaming is enabled, false: otherwise
     */
    public abstract boolean isStreaming();

    /**
     * Instruct this drone to enable/disable streaming. You can listen to the stream by adding a {@link VideoListener}
     * using {@link #addVideoListener(VideoListener)}.
     *
     * @param stream true: drone should start streaming, false: drone should stop streaming.
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract void setStreaming(boolean stream) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Instructs this drone to turn off all motors.
     *
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract void emergency() throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Instructs this drone to move a certain amount of centimeters in one direction.
     *
     * @param direction The direction the drone should move in
     * @param cm        The amount in centimeters to be moved
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     * @throws TelloNoValidIMUException      If the IMU is not correctly calibrated or the ground beneath the drone does
     *                                       not provide enough textual features.
     */
    public abstract void moveDirection(MovementDirection direction, int cm) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException;

    /**
     * Instructs this drone to turn a certain amount of degrees in one direction
     *
     * @param direction The direction to turn the drone
     * @param degrees   The amount of degrees to turn
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     * @throws TelloNoValidIMUException      If the IMU is not correctly calibrated or the ground beneath the drone does
     *                                       not provide enough textual features.
     */
    public abstract void turn(TurnDirection direction, int degrees) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException;

    /**
     * Instructs this drone to perform a flip in the direction you specify
     *
     * @param direction The direction to perform the flip to
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error (possibly battery too low).
     * @throws TelloNoValidIMUException      If the IMU is not correctly calibrated or the ground beneath the drone does
     *                                       not provide enough textual features.
     */
    public abstract void flip(FlipDirection direction) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException;

    /**
     * Instructs this drone to move to a relative position (x, y, z) with a set speed
     *
     * @param x     Amount of centimeters to move on the x-Axis (left/right)
     * @param y     Amount of centimeters to move on the y-Axis (forward/backward)
     * @param z     Amount of centimeters to move on the z-Axis (up/down)
     * @param speed Flying speed
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     * @throws TelloNoValidIMUException      If the IMU is not correctly calibrated or the ground beneath the drone does
     *                                       not provide enough textual features.
     */
    public abstract void move(int x, int y, int z, int speed) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException;

    /**
     * Instructs this drone to fly a curve via one relative point to another
     *
     * @param x1    Amount of centimeters to the first point on the x-Axis (left/right)
     * @param y1    Amount of centimeters to the first point on the y-Axis (forward/backward)
     * @param z1    Amount of centimeters to the first point on the z-Axis (up/down)
     * @param x2    Amount of centimeters to the second point on the x-Axis (left/right)
     * @param y2    Amount of centimeters to the second point on the y-Axis (forward/backward)
     * @param z2    Amount of centimeters to the second point on the z-Axis (up/down)
     * @param speed Flying speed
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     * @throws TelloNoValidIMUException      If the IMU is not correctly calibrated or the ground beneath the drone does
     *                                       not provide enough textual features.
     */
    public abstract void curve(int x1, int y1, int z1, int x2, int y2, int z2, int speed) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException;

    /**
     * Sets the flying speed of this drone
     *
     * @param speed Flying speed
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract void setSpeed(int speed) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Send remote control input to this drone
     *
     * @param lr  Left/right input
     * @param fb  Forward/Backward input
     * @param ud  Up/Down input
     * @param yaw Rotation input
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract void sendRemoteControlInputs(int lr, int fb, int ud, int yaw) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Changes your tello wifi name (ssid) and password. Can be reset by pressing the tello "on" button for 5 seconds
     *
     * @param ssid     New wifi ssid
     * @param password New wifi password
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract void setWifiSSIDAndPassword(String ssid, String password) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Fetch the current speed setting of this drone. Please note that this does not return the actual current speed.
     *
     * @return Speed setting
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract double fetchSpeed() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Fetch the current battery level of this drone.
     *
     * @return Battery level
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract int fetchBattery() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Fetch the current running duration of the motors/airborne time.
     *
     * @return Motor time in seconds
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract int fetchMotorTime() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Fetch the current altitude of this drone.
     *
     * @return Altitude in centimeters
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract int fetchHeight() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Fetch the current temperature of this drone (own temperature, not outside temperature).
     *
     * @return Temperature in degrees celsius
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract int fetchTemperature() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Fetches the current attitude of this drone.
     *
     * @return An array of int[]{x, y, z}, containing the relative coordinates from the starting point
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract int[] fetchAttitude() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Fetch the barometer value of this drone.
     *
     * @return Barometer value in hectopascal
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract double fetchBarometer() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Fetch the current acceleration force of this drone.
     *
     * @return An array of double[]{vx, vy, vz}. vy always contains the earth acceleration.
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract double[] fetchAcceleration() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Fetch the distance ahead using the time-of-flight sensor.
     *
     * @return Distance to the next obstacle in centimeters
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract int fetchTOFDistance() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    /**
     * Fetch the wifi signal-noise-ratio from this drones perspective.
     *
     * @return Signal-noise-ratio value
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     */
    public abstract int fetchWifiSnr() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;


    /**
     * Instructs this drone to move up a certain amount of centimeters.
     *
     * @param cm The amount in centimeters to be moved
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     * @throws TelloNoValidIMUException      If the IMU is not correctly calibrated or the ground beneath the drone does
     *                                       not provide enough textual features.
     */
    public void up(int cm) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.moveDirection(MovementDirection.UP, cm);
    }

    /**
     * Instructs this drone to move down a certain amount of centimeters.
     *
     * @param cm The amount in centimeters to be moved
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     * @throws TelloNoValidIMUException      If the IMU is not correctly calibrated or the ground beneath the drone does
     *                                       not provide enough textual features.
     */
    public void down(int cm) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.moveDirection(MovementDirection.DOWN, cm);
    }

    /**
     * Instructs this drone to move to the left a certain amount of centimeters.
     *
     * @param cm The amount in centimeters to be moved
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     * @throws TelloNoValidIMUException      If the IMU is not correctly calibrated or the ground beneath the drone does
     *                                       not provide enough textual features.
     */
    public void left(int cm) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.moveDirection(MovementDirection.LEFT, cm);
    }

    /**
     * Instructs this drone to move to the right a certain amount of centimeters.
     *
     * @param cm The amount in centimeters to be moved
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     * @throws TelloNoValidIMUException      If the IMU is not correctly calibrated or the ground beneath the drone does
     *                                       not provide enough textual features.
     */
    public void right(int cm) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.moveDirection(MovementDirection.RIGHT, cm);
    }

    /**
     * Instructs this drone to move forward a certain amount of centimeters.
     *
     * @param cm The amount in centimeters to be moved
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     * @throws TelloNoValidIMUException      If the IMU is not correctly calibrated or the ground beneath the drone does
     *                                       not provide enough textual features.
     */
    public void forward(int cm) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.moveDirection(MovementDirection.FORWARD, cm);
    }

    /**
     * Instructs this drone to move backward a certain amount of centimeters.
     *
     * @param cm The amount in centimeters to be moved
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     * @throws TelloNoValidIMUException      If the IMU is not correctly calibrated or the ground beneath the drone does
     *                                       not provide enough textual features.
     */
    public void backward(int cm) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.moveDirection(MovementDirection.BACKWARD, cm);
    }

    /**
     * Instructs this drone to turn left a certain amount of degrees
     *
     * @param degrees The amount of degrees to turn
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     * @throws TelloNoValidIMUException      If the IMU is not correctly calibrated or the ground beneath the drone does
     *                                       not provide enough textual features.
     */
    public void turnLeft(int degrees) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.turn(TurnDirection.LEFT, degrees);
    }

    /**
     * Instructs this drone to turn right a certain amount of degrees
     *
     * @param degrees The amount of degrees to turn
     * @throws TelloNetworkException         If there was an error sending the command or receiving the result.
     * @throws TelloCommandTimedOutException If the drone does not answer.
     * @throws TelloCustomCommandException   If the drone responds with a custom error message.
     *                                       Use {@link TelloCustomCommandException#getReason()} to fetch the custom error message.
     * @throws TelloGeneralCommandException  If the drone responds with an unspecified error.
     * @throws TelloNoValidIMUException      If the IMU is not correctly calibrated or the ground beneath the drone does
     *                                       not provide enough textual features.
     */
    public void turnRight(int degrees) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.turn(TurnDirection.RIGHT, degrees);
    }

    /**
     * Adds a {@link VideoListener} to this drone, which can then receive new {@link TelloVideoFrame}s from this drone
     *
     * @param listener The listener to be added
     */
    public void addVideoListener(VideoListener listener) {
        this.videoListeners.add(listener);
    }

    /**
     * Removes a {@link VideoListener} from this drone, which will no longer receive {@link TelloVideoFrame}s
     *
     * @param listener The listener to be removed
     * @return true: if the listener was previously attatched to this drone, false: otherwise
     */
    public boolean removeVideoListener(VideoListener listener) {
        return this.videoListeners.remove(listener);
    }

    /**
     * Lists all {@link VideoListener}s currently attached to this drone
     *
     * @return List of all {@link VideoListener}s
     */
    public List<VideoListener> getVideoListeners() {
        return videoListeners;
    }

    /**
     * Adds a {@link StateListener} to this drone, which can then receive new {@link TelloDroneState}s from this drone
     *
     * @param listener The listener to be added
     */
    public void addStateListener(StateListener listener) {
        this.stateListeners.add(listener);
    }

    /**
     * Removes a {@link StateListener} from this drone, which will no longer receive {@link TelloDroneState}s
     *
     * @param listener The listener to be removed
     * @return true: if the listener was previously attatched to this drone, false: otherwise
     */
    public boolean removeStateListener(StateListener listener) {
        return this.stateListeners.remove(listener);
    }

    /**
     * Lists all {@link StateListener}s currently attached to this drone
     *
     * @return List of all {@link StateListener}s
     */
    public List<StateListener> getStateListeners() {
        return stateListeners;
    }

    /**
     * Fetches the last received {@link TelloDroneState} of this drone
     *
     * @return Last received {@link TelloDroneState}
     */
    public TelloDroneState getCachedState() {
        return cachedState;
    }

    /**
     * Set the last received {@link TelloDroneState} of this drone
     *
     * @param cachedState A new {@link TelloDroneState} to be cached for this drone
     */
    public void setCachedState(TelloDroneState cachedState) {
        this.cachedState = cachedState;
    }

    /**
     * Retrieves the current {@link TelloVideoExportType} for this drone
     *
     * @return Current video export type
     */
    public TelloVideoExportType getVideoExportType() {
        return videoExportType;
    }

    /**
     * Sets the current {@link TelloVideoExportType} for this drone. Please note that it may take a few seconds for the
     * new type to be applied.
     *
     * @param videoExportType The new video export type
     */
    public void setVideoExportType(TelloVideoExportType videoExportType) {
        this.videoExportType = videoExportType;
    }
}
