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

import org.bytedeco.javacv.Java2DFrameConverter;

import javax.swing.*;
import java.awt.*;

import static me.friwi.tello4j.wifi.model.TelloSDKValues.VIDEO_HEIGHT;
import static me.friwi.tello4j.wifi.model.TelloSDKValues.VIDEO_WIDTH;

/**
 * A simple {@link VideoListener} in the form of a {@link JPanel}, that draws all received images to itself.
 *
 * @author Fritz Windisch
 */
public class VideoPanel extends JPanel implements VideoListener {
    private TelloVideoFrame frame;
    private Java2DFrameConverter converter = null;

    public VideoPanel() {
        Dimension size = new Dimension(VIDEO_WIDTH, VIDEO_HEIGHT);
        this.setPreferredSize(size);
        this.setSize(size);
        this.setMinimumSize(size);
        this.setMaximumSize(size);
        this.setBackground(Color.BLACK);
    }

    public TelloVideoFrame getFrame() {
        return frame;
    }

    public void setFrame(TelloVideoFrame frame) {
        this.frame = frame;
        this.repaint();
    }

    @Override
    public void paint(Graphics gr) {
        if (gr instanceof Graphics2D) {
            Graphics2D g = (Graphics2D) gr;
            if (getFrame() != null) {
                if (getFrame().getExportType() == TelloVideoExportType.JAVACV_FRAME) {
                    if (converter == null) converter = new Java2DFrameConverter();
                    this.frame = new TelloVideoFrame(converter.convert(getFrame().getJavaCVFrame()), getFrame().getJavaCVFrame());
                }
                g.drawImage(getFrame().getImage(), 0, 0, null);
            }
        }
    }

    @Override
    public void onFrameReceived(TelloVideoFrame frame) {
        setFrame(frame);
    }
}
