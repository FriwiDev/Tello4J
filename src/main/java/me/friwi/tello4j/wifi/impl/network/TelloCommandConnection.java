package me.friwi.tello4j.wifi.impl.network;

import me.friwi.tello4j.api.exception.TelloException;
import me.friwi.tello4j.api.exception.TelloNetworkException;
import me.friwi.tello4j.wifi.impl.WifiDrone;
import me.friwi.tello4j.wifi.impl.state.TelloStateThread;
import me.friwi.tello4j.wifi.impl.video.TelloVideoThread;
import me.friwi.tello4j.wifi.model.TelloSDKValues;
import me.friwi.tello4j.wifi.model.command.TelloCommand;
import me.friwi.tello4j.wifi.model.response.TelloResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class TelloCommandConnection {
    protected DatagramSocket ds;
    protected InetAddress remoteAddress;
    protected boolean connectionState = false;
    protected TelloCommandQueue queue;
    protected TelloStateThread stateThread;
    protected TelloVideoThread videoThread;

    protected WifiDrone drone;

    public TelloCommandConnection(WifiDrone drone) {
        this.drone = drone;
    }

    public void connect() throws TelloNetworkException {
        try {
            queue = new TelloCommandQueue(this);
            stateThread = new TelloStateThread(this);
            videoThread = new TelloVideoThread(this);
            this.remoteAddress = InetAddress.getByName(TelloSDKValues.DRONE_IP_DST);
            ds = new DatagramSocket(TelloSDKValues.COMMAND_PORT);
            ds.setSoTimeout(TelloSDKValues.COMMAND_SOCKET_TIMEOUT);
            ds.connect(remoteAddress, TelloSDKValues.COMMAND_PORT);
            stateThread.connect();
            videoThread.connect();
            queue.start();
            stateThread.start();
            videoThread.start();
            connectionState = true;
        } catch (Exception e) {
            throw new TelloNetworkException("Could not connect to drone", e);
        }
    }

    public void disconnect() {
        connectionState = false;
        queue.kill();
        stateThread.kill();
        videoThread.kill();
        ds.disconnect();
        ds.close();
    }

    public TelloResponse sendCommand(TelloCommand cmd) throws TelloException {
        synchronized (cmd) {
            queue.queueCommand(cmd);
            try {
                cmd.wait(); //Wait for finish in command queue
            } catch (InterruptedException e) {
                throw new TelloNetworkException("\"" + cmd.serializeCommand() + "\" command was interrupted while executing it!");
            }
        }
        if (cmd.getException() != null) {
            throw new TelloException("Error while performing command", cmd.getException());
        }
        if (cmd.getResponse() == null) {
            throw new TelloNetworkException("\"" + cmd.serializeCommand() + "\" command was not answered!");
        }
        return cmd.getResponse();
    }

    protected void send(String str) throws TelloNetworkException {
        if (!connectionState)
            throw new TelloNetworkException("Can not send/receive data when the connection is closed!");

        if (TelloSDKValues.DEBUG) System.out.println("[OUT] " + str);

        try {
            this.send(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new TelloNetworkException("Your system does not support utf-8 encoding", e);
        }
    }

    protected void send(byte[] bytes) throws TelloNetworkException {
        if (!connectionState)
            throw new TelloNetworkException("Can not send/receive data when the connection is closed!");
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, remoteAddress, TelloSDKValues.COMMAND_PORT);
        try {
            ds.send(packet);
        } catch (IOException e) {
            throw new TelloNetworkException("Error on sending packet", e);
        }
    }

    protected byte[] readBytes() throws TelloNetworkException {
        if (!connectionState)
            throw new TelloNetworkException("Can not send/receive data when the connection is closed!");
        byte[] data = new byte[256];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            ds.receive(packet);
        } catch (IOException e) {
            throw new TelloNetworkException("Error while reading from command channel", e);
        }
        return Arrays.copyOf(data, packet.getLength());
    }

    protected String readString() throws TelloNetworkException {
        if (!connectionState)
            throw new TelloNetworkException("Can not send/receive data when the connection is closed!");
        byte[] data = readBytes();
        try {
            String str = new String(data, "UTF-8");
            if (TelloSDKValues.DEBUG) System.out.println("[IN ] " + str.trim());
            return str;
        } catch (UnsupportedEncodingException e) {
            throw new TelloNetworkException("Your system does not support utf-8 encoding", e);
        }
    }

    public boolean isConnected() {
        return connectionState;
    }

    public WifiDrone getDrone() {
        return drone;
    }
}