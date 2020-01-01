package me.friwi.tello4j.api.world;

/**
 * This enum represents all possible directions the drone can move to
 *
 * @author Fritz Windisch
 */
public enum MovementDirection {
    UP("up"),
    DOWN("down"),
    FORWARD("forward"),
    BACKWARD("back"),
    LEFT("left"),
    RIGHT("right");

    private String command;

    private MovementDirection(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
