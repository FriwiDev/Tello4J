package me.friwi.tello4j.wifi.impl;

import me.friwi.tello4j.api.drone.TelloDrone;
import me.friwi.tello4j.api.exception.TelloException;
import me.friwi.tello4j.api.world.FlipDirection;
import me.friwi.tello4j.api.world.MovementDirection;
import me.friwi.tello4j.api.world.TurnDirection;
import me.friwi.tello4j.wifi.impl.command.control.*;
import me.friwi.tello4j.wifi.impl.command.read.*;
import me.friwi.tello4j.wifi.impl.command.set.RemoteControlCommand;
import me.friwi.tello4j.wifi.impl.command.set.SetSpeedCommand;
import me.friwi.tello4j.wifi.impl.command.set.SetWifiPasswordAndSSIDCommand;
import me.friwi.tello4j.wifi.impl.network.TelloCommandConnection;
import me.friwi.tello4j.wifi.impl.response.TelloReadCommandResponse;
import me.friwi.tello4j.wifi.model.command.ReadCommand;
import me.friwi.tello4j.wifi.model.response.TelloResponse;

public class WifiDrone extends TelloDrone {
    private TelloCommandConnection commandConnection;

    private boolean streaming = false;

    public WifiDrone() {
        this.commandConnection = new TelloCommandConnection(this);
    }

    @Override
    public void connect() throws TelloException {
        this.commandConnection.connect();
        //Enter SDK mode
        this.commandConnection.sendCommand(new EnterSDKModeCommand());
    }

    @Override
    public void disconnect() {
        this.commandConnection.disconnect();
    }

    @Override
    public boolean isConnected() {
        return this.commandConnection.isConnected();
    }

    /**
     * Control commands
     */

    public void takeoff() throws TelloException {
        this.commandConnection.sendCommand(new TakeoffCommand());
    }

    public void land() throws TelloException {
        this.commandConnection.sendCommand(new LandCommand());
    }

    public boolean isStreaming() {
        return this.streaming;
    }

    public void setStreaming(boolean stream) throws TelloException {
        //Only notify drone on state change
        if (stream && !streaming) {
            this.commandConnection.sendCommand(new StreamOnCommand());
        } else if (!stream && streaming) {
            this.commandConnection.sendCommand(new StreamOffCommand());
        }
        //If state change successful, update streaming parameter
        this.streaming = stream;
    }

    public void emergency() throws TelloException {
        this.commandConnection.sendCommand(new EmergencyCommand());
    }

    public void moveDirection(MovementDirection direction, int cm) throws TelloException {
        this.commandConnection.sendCommand(new FlyDirectionCommand(direction, cm));
    }

    public void turn(TurnDirection direction, int degrees) throws TelloException {
        this.commandConnection.sendCommand(new TurnCommand(direction, degrees));
    }

    public void flip(FlipDirection direction) throws TelloException {
        this.commandConnection.sendCommand(new FlipCommand(direction));
    }

    public void move(int x, int y, int z, int speed) throws TelloException {
        this.commandConnection.sendCommand(new FlyParameterizedCommand(x, y, z, speed));
    }

    public void curve(int x1, int y1, int z1, int x2, int y2, int z2, int speed) throws TelloException {
        this.commandConnection.sendCommand(new FlyCurveCommand(x1, x2, y1, y2, z1, z2, speed));
    }

    public void setSpeed(int speed) throws TelloException {
        this.commandConnection.sendCommand(new SetSpeedCommand(speed));
    }

    public void sendRemoteControlInputs(int lr, int fb, int ud, int yaw) throws TelloException {
        this.commandConnection.sendCommand(new RemoteControlCommand(lr, fb, ud, yaw));
    }

    public void setWifiSSIDAndPassword(String ssid, String password) throws TelloException {
        this.commandConnection.sendCommand(new SetWifiPasswordAndSSIDCommand(ssid, password));
    }

       /*
     * Fetch examples:
     *
[OUT] speed?
[IN ] 100.0

[OUT] battery?
[IN ] 19

[OUT] time?
[IN ] 4s

[OUT] height?
[IN ] 7dm

[OUT] temp?
[IN ] 71~73C

[OUT] attitude?
[IN ] pitch:0;roll:0;yaw:0;

[OUT] baro?
[IN ] 616.374329

[OUT] acceleration?
[IN ] agx:-28.00;agy:0.00;agz:-1018.00;

[OUT] tof?
[IN ] 822mm

[OUT] wifi?
[IN ] 90

     *
     */

    private Object[] fetch(ReadCommand cmd) throws TelloException {
        TelloResponse r = this.commandConnection.sendCommand(cmd);
        if (r instanceof TelloReadCommandResponse) {
            return ((TelloReadCommandResponse) r).getReturnValues();
        } else {
            throw new TelloException("Error while parsing input");
        }
    }

    public double fetchSpeed() throws TelloException {
        return (double) fetch(new ReadSpeedCommand())[0];
    }

    public int fetchBattery() throws TelloException {
        return (int) fetch(new ReadBatteryCommand())[0];
    }

    public int fetchMotorTime() throws TelloException {
        return (int) fetch(new ReadMotorTimeCommand())[0];
    }

    public int fetchHeight() throws TelloException {
        return (int) fetch(new ReadHeightCommand())[0];
    }

    public int fetchTemperature() throws TelloException {
        return (int) fetch(new ReadTemperatureCommand())[0];
    }

    public int[] fetchAttitude() throws TelloException {
        Object[] in = fetch(new ReadAttitudeCommand());
        int[] ret = new int[3];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (int) in[i];
        }
        return ret;
    }

    public double fetchBarometer() throws TelloException {
        return (double) fetch(new ReadBarometerCommand())[0];
    }

    public double[] fetchAcceleration() throws TelloException {
        Object[] in = fetch(new ReadAccelerationCommand());
        double[] ret = new double[3];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (double) in[i];
        }
        return ret;
    }

    public int fetchTOFDistance() throws TelloException {
        return (int) fetch(new ReadTOFDistanceCommand())[0];
    }

    public int fetchWifiSnr() throws TelloException {
        return (int) fetch(new ReadWifiSNRCommand())[0];
    }
}
