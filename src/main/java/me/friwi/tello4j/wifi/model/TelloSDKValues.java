package me.friwi.tello4j.wifi.model;

import java.util.regex.Pattern;

public class TelloSDKValues {
    public static final String DRONE_IP_DST = "192.168.10.1";
    public static final String COMMANDER_IP_DST = "0.0.0.0";

    public static final int COMMAND_PORT = 8889;
    public static final int STATE_PORT = 8890;
    public static final int STREAM_PORT = 11111;

    public static final int COMMAND_SOCKET_TIMEOUT = 20000;
    public static final int STATE_SOCKET_TIMEOUT = 1000;
    public static final int VIDEO_SOCKET_TIMEOUT = 1000;
    public static final int COMMAND_SOCKET_BINARY_ATTEMPTS = 5;

    public static final int STREAM_DEFAULT_PACKET_SIZE = 1460;

    public static final boolean DEBUG = false;

    public static final int VIDEO_WIDTH = 960;
    public static final int VIDEO_HEIGHT = 720;

    public static final Pattern COMMAND_REPLY_PATTERN = Pattern.compile("[\\w\\. ;:\"]+");

    public static final String NO_VALID_IMU_MSG = "Your tello drone can not obtain valid internal measurement unit parameters. This can occur when the ground under your drone does not provide enough textual features.";
}
