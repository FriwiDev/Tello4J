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
