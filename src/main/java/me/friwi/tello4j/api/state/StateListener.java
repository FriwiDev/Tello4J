package me.friwi.tello4j.api.state;

public interface StateListener {
    void onStateChanged(TelloDroneState oldState, TelloDroneState newState);
}
