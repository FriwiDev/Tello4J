package me.friwi.tello4j.api.video;

public interface VideoListener {
    void onFrameReceived(TelloVideoFrame frame);
}
