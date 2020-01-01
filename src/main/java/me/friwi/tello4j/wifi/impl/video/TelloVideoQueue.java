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

package me.friwi.tello4j.wifi.impl.video;

import me.friwi.tello4j.api.video.TelloVideoFrame;
import me.friwi.tello4j.api.video.VideoListener;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TelloVideoQueue extends Thread {
    private ConcurrentLinkedQueue<TelloVideoFrame> queue = new ConcurrentLinkedQueue<>();
    private boolean running = true;
    private TelloVideoThread videoThread;

    public TelloVideoQueue(TelloVideoThread videoThread) {
        this.videoThread = videoThread;
    }

    public synchronized void run() {
        setName("Video-Queue");
        while (running) {
            TelloVideoFrame frame = queue.poll();
            if (frame != null) {
                for (VideoListener listener : this.videoThread.getConnection().getDrone().getVideoListeners()) {
                    listener.onFrameReceived(frame);
                }
            } else {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace(); //Unlikely
                }
            }
        }
    }

    public synchronized void queueFrame(TelloVideoFrame frame) {
        queue.add(frame);
        this.notifyAll();
    }

    public synchronized void kill() {
        running = false;
        this.notifyAll();
    }
}
