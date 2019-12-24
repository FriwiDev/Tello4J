package me.friwi.tello4j.api.video;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static me.friwi.tello4j.wifi.model.TelloSDKValues.VIDEO_HEIGHT;
import static me.friwi.tello4j.wifi.model.TelloSDKValues.VIDEO_WIDTH;

public class VideoWindow extends JFrame implements VideoListener {
    private VideoPanel panel;

    public VideoWindow() {
        super("Tello Video Stream");
        this.setPreferredSize(new Dimension(VIDEO_WIDTH, VIDEO_HEIGHT));
        this.setSize(new Dimension(VIDEO_WIDTH, VIDEO_HEIGHT));
        this.setMinimumSize(new Dimension(VIDEO_WIDTH, VIDEO_HEIGHT));
        this.setMaximumSize(new Dimension(VIDEO_WIDTH, VIDEO_HEIGHT));
        this.setBackground(Color.BLACK);
        this.panel = new VideoPanel();
        this.setVisible(true);
        this.add(this.panel);
    }

    public void setImage(BufferedImage img) {
        this.panel.setImage(img);
    }

    @Override
    public void onFrameReceived(TelloVideoFrame frame) {
        setImage(frame.getImage());
    }
}
