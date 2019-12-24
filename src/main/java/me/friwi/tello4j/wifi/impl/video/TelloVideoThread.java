package me.friwi.tello4j.wifi.impl.video;

import me.friwi.tello4j.api.exception.TelloException;
import me.friwi.tello4j.api.exception.TelloNetworkException;
import me.friwi.tello4j.api.video.TelloVideoFrame;
import me.friwi.tello4j.wifi.impl.network.TelloCommandConnection;
import me.friwi.tello4j.wifi.model.TelloSDKValues;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

import static me.friwi.tello4j.wifi.model.TelloSDKValues.*;

public class TelloVideoThread extends Thread {
    private boolean running = true;
    private TelloCommandConnection connection;
    private DatagramSocket ds;
    private TelloVideoQueue queue;

    private List<byte[]> currentFrame = new LinkedList<>();
    private PipedInputStream pis = new PipedInputStream();
    private PipedOutputStream pos = null;

    private boolean streamAligned = false;
    private byte[] buf = new byte[2048];

    public TelloVideoThread(TelloCommandConnection connection) throws TelloNetworkException {
        this.connection = connection;
        this.queue = new TelloVideoQueue(this);
        try {
            this.pos = new PipedOutputStream(pis);
        } catch (IOException e) {
            throw new TelloNetworkException("Error on constructing video stream", e);
        }
        new Thread(() -> {
            if (TelloSDKValues.DEBUG) avutil.av_log_set_level(avutil.AV_LOG_ERROR);
            else avutil.av_log_set_level(avutil.AV_LOG_FATAL);
            Java2DFrameConverter conv = new Java2DFrameConverter();
            CustomFFmpegFrameGrabber fg = new CustomFFmpegFrameGrabber(pis);
            fg.setImageMode(FrameGrabber.ImageMode.COLOR);
            fg.setFormat("h264");
            fg.setFrameRate(30);
            fg.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            fg.setImageWidth(VIDEO_WIDTH);
            fg.setImageHeight(VIDEO_HEIGHT);
            try {
                fg.start();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
                return;
            }
            Frame f;
            while (running) {
                try {
                    f = fg.grabImage();
                    if (f != null) {
                        TelloVideoFrame frame;
                        switch (connection.getDrone().getVideoExportType()) {
                            case BUFFERED_IMAGE:
                                frame = new TelloVideoFrame(conv.convert(f));
                                break;
                            case JAVACV_FRAME:
                                frame = new TelloVideoFrame(f.clone());
                                break;
                            case BOTH:
                            default:
                                Frame cloned = f.clone();
                                frame = new TelloVideoFrame(conv.convert(cloned), cloned);
                        }
                        queue.queueFrame(frame);
                    }
                } catch (FrameGrabber.Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }).start();
    }

    public void connect() throws TelloNetworkException {
        try {
            ds = new DatagramSocket(TelloSDKValues.STREAM_PORT, InetAddress.getByName(TelloSDKValues.COMMANDER_IP_DST));
            ds.setSoTimeout(TelloSDKValues.VIDEO_SOCKET_TIMEOUT);
        } catch (Exception e) {
            throw new TelloNetworkException("Error while creating stream receive socket", e);
        }
    }

    public void run() {
        queue.start();
        setName("Stream-Thread");
        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                ds.receive(packet);
                handleInput(buf, packet.getLength());
            } catch (Exception e) {
                //Ignore missing updates - no way to error them
                //Disconnect at end of program is also intended to end here
            }
        }
    }

    private void handleInput(byte[] bytes, int length) throws TelloException {
        if (!streamAligned) {
            if (length != STREAM_DEFAULT_PACKET_SIZE) streamAligned = true;
            return;
        }
        try {
            pos.write(bytes, 0, length);
            pos.flush();
        } catch (IOException e) {
            throw new TelloNetworkException("Error while pushing data", e);
        }
    }

    public void kill() {
        running = false;
        queue.kill();
        ds.close();
    }

    protected TelloCommandConnection getConnection() {
        return connection;
    }
}
