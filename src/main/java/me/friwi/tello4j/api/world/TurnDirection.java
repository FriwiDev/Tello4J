package me.friwi.tello4j.api.world;

public enum TurnDirection {
    LEFT("ccw"),
    RIGHT("cw");

    private String command;

    private TurnDirection(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
