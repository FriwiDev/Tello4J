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
    boolean running = true;
    TelloVideoQueue queue;
    PipedInputStream pis = new PipedInputStream();
    private TelloCommandConnection connection;
    private DatagramSocket ds;
    private List<byte[]> currentFrame = new LinkedList<>();
    private PipedOutputStream pos;

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
        frameGrabberThread.start();
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

    TelloCommandConnection getConnection() {
        return connection;
    }
}
