package me.friwi.tello4j.api.world;

public enum FlipDirection {
    LEFT("l"),
    RIGHT("r"),
    FORWARD("f"),
    BACKWARD("b");

    private String command;

    private FlipDirection(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
