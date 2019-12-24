package me.friwi.tello4j.wifi.impl.network;

import me.friwi.tello4j.api.exception.TelloException;
import me.friwi.tello4j.api.exception.TelloNetworkException;
import me.friwi.tello4j.wifi.model.TelloSDKValues;
import me.friwi.tello4j.wifi.model.command.TelloCommand;
import me.friwi.tello4j.wifi.model.response.TelloResponse;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TelloCommandQueue extends Thread {
    private ConcurrentLinkedQueue<TelloCommand> queue = new ConcurrentLinkedQueue<>();
    private boolean running = true;
    private TelloCommandConnection connection;

    public TelloCommandQueue(TelloCommandConnection connection) {
        this.connection = connection;
    }

    public synchronized void run() {
        setName("Command-Queue");
        while (running) {
            TelloCommand cmd = queue.poll();
            if (cmd != null) {
                try {
                    this.connection.send(cmd.serializeCommand());
                    String data = this.connection.readString().trim();
                    int attempt = 0;
                    boolean invalid = false;
                    do {
                        invalid = false;
                        if (data.startsWith("conn_ack")) invalid = true;
                        if (!TelloSDKValues.COMMAND_REPLY_PATTERN.matcher(data).matches()) invalid = true;
                        if (invalid && TelloSDKValues.DEBUG) {
                            System.err.println("Dropping reply \"" + data + "\" as it might be binary");
                        }
                        attempt++;
                        if (invalid && attempt >= TelloSDKValues.COMMAND_SOCKET_BINARY_ATTEMPTS) {
                            throw new TelloNetworkException("Too many binary messages received after sending command. Broken connection?");
                        }
                        if(invalid){
                            data = this.connection.readString().trim();
                        }
                    } while (invalid);
                    TelloResponse response = cmd.buildResponse(data);
                    cmd.setResponse(response);
                    synchronized (cmd) {
                        cmd.notifyAll();
                    }
                } catch (TelloException e) {
                    cmd.setException(e);
                    synchronized (cmd) {
                        cmd.notifyAll();
                    }
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

    public synchronized void queueCommand(TelloCommand cmd) {
        queue.add(cmd);
        this.notifyAll();
    }

    public synchronized void kill() {
        running = false;
        this.notifyAll();
    }
}
