package me.friwi.tello4j.api.video;

/**
 * This enum specifies the video export type of a {@link me.friwi.tello4j.api.video.VideoListener}.
 * In general, the listener can provide {@link org.bytedeco.javacv.Frame}s or/and {@link java.awt.image.BufferedImage}s.
 *
 * @author Fritz Windisch
 */
public enum TelloVideoExportType {
    /**
     * Provide only {@link org.bytedeco.javacv.Frame}s.
     */
    JAVACV_FRAME,
    /**
     * Provide only {@link java.awt.image.BufferedImage}s.
     */
    BUFFERED_IMAGE,
    /**
     * Provide {@link org.bytedeco.javacv.Frame}s and {@link java.awt.image.BufferedImage}s.
     */
    BOTH;
}
