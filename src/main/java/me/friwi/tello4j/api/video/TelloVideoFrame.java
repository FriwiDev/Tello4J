package me.friwi.tello4j.api.video;

import org.bytedeco.javacv.Frame;

import java.awt.image.BufferedImage;

public class TelloVideoFrame {
    private BufferedImage image;
    private Frame javaCVFrame;
    private TelloVideoExportType exportType;

    public TelloVideoFrame(BufferedImage image) {
        this.image = image;
        this.exportType = TelloVideoExportType.BUFFERED_IMAGE;
    }

    public TelloVideoFrame(Frame javaCVFrame) {
        this.javaCVFrame = javaCVFrame;
        this.exportType = TelloVideoExportType.JAVACV_FRAME;
    }

    public TelloVideoFrame(BufferedImage image, Frame javaCVFrame) {
        this.image = image;
        this.javaCVFrame = javaCVFrame;
        this.exportType = TelloVideoExportType.BOTH;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Frame getJavaCVFrame() {
        return javaCVFrame;
    }

    public int getWidth() {
        return image == null ? javaCVFrame.imageWidth : image.getWidth();
    }

    public int getHeight() {
        return image == null ? javaCVFrame.imageHeight : image.getHeight();
    }

    public TelloVideoExportType getExportType() {
        return exportType;
    }
}
