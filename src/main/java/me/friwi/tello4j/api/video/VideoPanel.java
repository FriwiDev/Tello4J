package me.friwi.tello4j.api.video;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static me.friwi.tello4j.wifi.model.TelloSDKValues.VIDEO_HEIGHT;
import static me.friwi.tello4j.wifi.model.TelloSDKValues.VIDEO_WIDTH;

public class VideoPanel extends JPanel {
    BufferedImage image;

    public VideoPanel() {
        this.setPreferredSize(new Dimension(VIDEO_WIDTH, VIDEO_HEIGHT));
        this.setSize(new Dimension(VIDEO_WIDTH, VIDEO_HEIGHT));
        this.setMinimumSize(new Dimension(VIDEO_WIDTH, VIDEO_HEIGHT));
        this.setMaximumSize(new Dimension(VIDEO_WIDTH, VIDEO_HEIGHT));
        this.setBackground(Color.BLACK);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        this.repaint();
    }

    @Override
    public void paint(Graphics gr) {
        if (gr instanceof Graphics2D) {
            Graphics2D g = (Graphics2D) gr;
            if (getImage() != null) g.drawImage(getImage(), 0, 0, null);
        }
    }
}
