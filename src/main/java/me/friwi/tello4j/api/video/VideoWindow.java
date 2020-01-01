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

package me.friwi.tello4j.api.video;

import javax.swing.*;
import java.awt.*;

import static me.friwi.tello4j.wifi.model.TelloSDKValues.VIDEO_HEIGHT;
import static me.friwi.tello4j.wifi.model.TelloSDKValues.VIDEO_WIDTH;

/**
 * A simple {@link VideoListener} in the form of a {@link JFrame}, that draws all received images to itself.
 *
 * @author Fritz Windisch
 */
public class VideoWindow extends JFrame implements VideoListener {
    private VideoPanel panel;

    public VideoWindow() {
        this("Tello Video Stream");
    }

    public VideoWindow(String title) {
        super(title);
        Dimension size = new Dimension(VIDEO_WIDTH, VIDEO_HEIGHT);
        this.setPreferredSize(size);
        this.setSize(size);
        this.setMinimumSize(size);
        this.setMaximumSize(size);
        this.setBackground(Color.BLACK);
        this.panel = new VideoPanel();
        this.add(this.panel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void setFrame(TelloVideoFrame frame) {
        this.panel.setFrame(frame);
    }

    @Override
    public void onFrameReceived(TelloVideoFrame frame) {
        setFrame(frame);
    }
}
