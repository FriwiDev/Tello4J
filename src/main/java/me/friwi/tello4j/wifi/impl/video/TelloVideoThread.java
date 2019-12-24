package me.friwi.tello4j.wifi.impl.video;

import me.friwi.tello4j.api.exception.TelloException;
import me.friwi.tello4j.api.exception.TelloNetworkException;
import me.friwi.tello4j.wifi.impl.network.TelloCommandConnection;
import me.friwi.tello4j.wifi.model.TelloSDKValues;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

import static me.friwi.tello4j.wifi.model.TelloSDKValues.STREAM_DEFAULT_PACKET_SIZE;

public class TelloVideoThread extends Thread {
    protected boolean running = true;
    private TelloCommandConnection connection;
    private DatagramSocket ds;
    protected TelloVideoQueue queue;

    private List<byte[]> currentFrame = new LinkedList<>();
    protected PipedInputStream pis = new PipedInputStream();
    private PipedOutputStream pos = null;

    private boolean streamAligned = false;
    private byte[] buf = new byte[2048];
    private TelloFrameGrabberThread frameGrabberThread;

    public TelloVideoThread(TelloCommandConnection connection) throws TelloNetworkException {
        this.connection = connection;
        this.queue = new TelloVideoQueue(this);
        try {
            this.pos = new PipedOutputStream(pis);
        } catch (IOException e) {
            throw new TelloNetworkException("Error on constructing video stream", e);
        }
        frameGrabberThread = new TelloFrameGrabberThread(this);
        frameGrabberThread.start();
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
        try {
            pos.close(); //This will shut down the frame grabber as well
        } catch (IOException e) {
            e.printStackTrace();
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
