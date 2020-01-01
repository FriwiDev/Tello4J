package me.friwi.tello4j.api.video;

import me.friwi.tello4j.api.drone.TelloDrone;

/**
 * A VideoListener receives video data in the form of {@link TelloVideoFrame}s.
 *
 * @author Fritz Windisch
 */
public interface VideoListener {
    /**
     * After adding the listener to the {@link TelloDrone}, this method will be called
     * with each image received from the drone.
     *
     * @param frame
     */
    void onFrameReceived(TelloVideoFrame frame);
}
