package me.friwi.tello4j.api.drone;

import me.friwi.tello4j.api.exception.*;
import me.friwi.tello4j.api.state.StateListener;
import me.friwi.tello4j.api.state.TelloDroneState;
import me.friwi.tello4j.api.video.TelloVideoExportType;
import me.friwi.tello4j.api.video.VideoListener;
import me.friwi.tello4j.api.world.FlipDirection;
import me.friwi.tello4j.api.world.MovementDirection;
import me.friwi.tello4j.api.world.TurnDirection;

import java.util.ArrayList;
import java.util.List;

public abstract class TelloDrone implements AutoCloseable {
    private List<VideoListener> videoListeners = new ArrayList<>();
    private List<StateListener> stateListeners = new ArrayList<>();
    private TelloDroneState cachedState;
    private TelloVideoExportType videoExportType = TelloVideoExportType.BUFFERED_IMAGE;

    @Override
    public void close() {
        this.disconnect();
    }

    public abstract void connect() throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract void disconnect();

    public abstract boolean isConnected();

    public abstract void takeoff() throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract void land() throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract boolean isStreaming();

    public abstract void setStreaming(boolean stream) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract void emergency() throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract void moveDirection(MovementDirection direction, int cm) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException;

    public abstract void turn(TurnDirection direction, int degrees) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException;

    public abstract void flip(FlipDirection direction) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException;

    public abstract void move(int x, int y, int z, int speed) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException;

    public abstract void curve(int x1, int y1, int z1, int x2, int y2, int z2, int speed) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException;

    public abstract void setSpeed(int speed) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract void sendRemoteControlInputs(int lr, int fb, int ud, int yaw) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract void setWifiSSIDAndPassword(String ssid, String password) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException;

    public abstract double fetchSpeed() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract int fetchBattery() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract int fetchMotorTime() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract int fetchHeight() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract int fetchTemperature() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract int[] fetchAttitude() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract double fetchBarometer() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract double[] fetchAcceleration() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract int fetchTOFDistance() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;

    public abstract int fetchWifiSnr() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException;


    public void up(int cm) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.moveDirection(MovementDirection.UP, cm);
    }

    public void down(int cm) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.moveDirection(MovementDirection.DOWN, cm);
    }

    public void left(int cm) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.moveDirection(MovementDirection.LEFT, cm);
    }

    public void right(int cm) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.moveDirection(MovementDirection.RIGHT, cm);
    }

    public void forward(int cm) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.moveDirection(MovementDirection.FORWARD, cm);
    }

    public void backward(int cm) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.moveDirection(MovementDirection.BACKWARD, cm);
    }

    public void turnLeft(int degrees) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.turn(TurnDirection.LEFT, degrees);
    }

    public void turnRight(int degrees) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.turn(TurnDirection.RIGHT, degrees);
    }

    public void addVideoListener(VideoListener listener) {
        this.videoListeners.add(listener);
    }

    public boolean removeVideoListener(VideoListener listener) {
        return this.videoListeners.remove(listener);
    }

    public List<VideoListener> getVideoListeners() {
        return videoListeners;
    }

    public void addStateListener(StateListener listener) {
        this.stateListeners.add(listener);
    }

    public boolean removeStateListener(StateListener listener) {
        return this.stateListeners.remove(listener);
    }

    public List<StateListener> getStateListeners() {
        return stateListeners;
    }

    public TelloDroneState getCachedState() {
        return cachedState;
    }

    public void setCachedState(TelloDroneState cachedState) {
        this.cachedState = cachedState;
    }

    public TelloVideoExportType getVideoExportType() {
        return videoExportType;
    }

    public void setVideoExportType(TelloVideoExportType videoExportType) {
        this.videoExportType = videoExportType;
    }
}
