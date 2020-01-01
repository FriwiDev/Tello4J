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

import org.bytedeco.javacv.Frame;

import java.awt.image.BufferedImage;

/**
 * A TelloVideoFrame at least contains one {@link org.bytedeco.javacv.Frame} or {@link java.awt.image.BufferedImage},
 * but may contain both. It is used as a wrapper class to supply image data to a {@link VideoListener}.
 *
 * @author Fritz Windisch
 */
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
