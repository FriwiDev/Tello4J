package me.friwi.tello4j.api.video;

import java.awt.image.BufferedImage;

public class TelloVideoFrame {
    private BufferedImage image;

    public TelloVideoFrame(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }
}
