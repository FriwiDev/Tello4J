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

package me.friwi.tello4j.wifi.impl;

import me.friwi.tello4j.api.drone.TelloDrone;
import me.friwi.tello4j.api.exception.*;
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
import me.friwi.tello4j.wifi.model.TelloSDKValues;
import me.friwi.tello4j.wifi.model.command.ReadCommand;
import me.friwi.tello4j.wifi.model.response.TelloResponse;

public class WifiDrone extends TelloDrone {
    private TelloCommandConnection commandConnection;

    private boolean streaming = false;

    public WifiDrone() {
        this.commandConnection = new TelloCommandConnection(this);
    }

    @Override
    public void connect() throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException {
        this.connect(TelloSDKValues.DRONE_IP_DST);
    }

    @Override
    public void connect(String remoteAddr) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException {
        this.commandConnection.connect(remoteAddr);
        //Enter SDK mode
        try {
            this.commandConnection.sendCommand(new EnterSDKModeCommand());
        } catch (TelloNoValidIMUException e) {
            //Will (hopefully) never happen
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        this.commandConnection.disconnect();
    }

    @Override
    public boolean isConnected() {
        return this.commandConnection.isConnected();
    }

    public void takeoff() throws TelloCommandTimedOutException, TelloCustomCommandException, TelloNetworkException, TelloGeneralCommandException {
        try {
            this.commandConnection.sendCommand(new TakeoffCommand());
        } catch (TelloNoValidIMUException e) {
            //Will (hopefully) never happen
            e.printStackTrace();
        }
    }

    public void land() throws TelloCommandTimedOutException, TelloCustomCommandException, TelloNetworkException, TelloGeneralCommandException {
        try {
            this.commandConnection.sendCommand(new LandCommand());
        } catch (TelloNoValidIMUException e) {
            //Will (hopefully) never happen
            e.printStackTrace();
        }
    }

    public boolean isStreaming() {
        return this.streaming;
    }

    public void setStreaming(boolean stream) throws TelloCommandTimedOutException, TelloCustomCommandException, TelloNetworkException, TelloGeneralCommandException {
        //Only notify drone on state change
        try {
            if (stream && !streaming) {
                this.commandConnection.sendCommand(new StreamOnCommand());
            } else if (!stream && streaming) {
                this.commandConnection.sendCommand(new StreamOffCommand());
            }
        } catch (TelloNoValidIMUException e) {
            //Will (hopefully) never happen
            e.printStackTrace();
        }
        //If state change successful, update streaming parameter
        this.streaming = stream;
    }

    public void emergency() throws TelloCommandTimedOutException, TelloCustomCommandException, TelloNetworkException, TelloGeneralCommandException {
        try {
            this.commandConnection.sendCommand(new EmergencyCommand());
        } catch (TelloNoValidIMUException e) {
            //Will (hopefully) never happen
            e.printStackTrace();
        }
    }

    public void moveDirection(MovementDirection direction, int cm) throws TelloCommandTimedOutException, TelloCustomCommandException, TelloNetworkException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.commandConnection.sendCommand(new FlyDirectionCommand(direction, cm));
    }

    public void turn(TurnDirection direction, int degrees) throws TelloCommandTimedOutException, TelloCustomCommandException, TelloNetworkException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.commandConnection.sendCommand(new TurnCommand(direction, degrees));
    }

    public void flip(FlipDirection direction) throws TelloCommandTimedOutException, TelloCustomCommandException, TelloNetworkException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.commandConnection.sendCommand(new FlipCommand(direction));
    }

    public void move(int x, int y, int z, int speed) throws TelloCommandTimedOutException, TelloCustomCommandException, TelloNetworkException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.commandConnection.sendCommand(new FlyParameterizedCommand(x, y, z, speed));
    }

    public void curve(int x1, int y1, int z1, int x2, int y2, int z2, int speed) throws TelloCommandTimedOutException, TelloCustomCommandException, TelloNetworkException, TelloNoValidIMUException, TelloGeneralCommandException {
        this.commandConnection.sendCommand(new FlyCurveCommand(x1, x2, y1, y2, z1, z2, speed));
    }

    public void setSpeed(int speed) throws TelloCommandTimedOutException, TelloCustomCommandException, TelloNetworkException, TelloGeneralCommandException {
        try {
            this.commandConnection.sendCommand(new SetSpeedCommand(speed));
        } catch (TelloNoValidIMUException e) {
            //Will (hopefully) never happen
            e.printStackTrace();
        }
    }

    public void sendRemoteControlInputs(int lr, int fb, int ud, int yaw) throws TelloCommandTimedOutException, TelloCustomCommandException, TelloNetworkException, TelloGeneralCommandException {
        try {
            this.commandConnection.sendCommand(new RemoteControlCommand(lr, fb, ud, yaw));
        } catch (TelloNoValidIMUException e) {
            //Will (hopefully) never happen
            e.printStackTrace();
        }
    }

    public void setWifiSSIDAndPassword(String ssid, String password) throws TelloCommandTimedOutException, TelloCustomCommandException, TelloNetworkException, TelloGeneralCommandException {
        try {
            this.commandConnection.sendCommand(new SetWifiPasswordAndSSIDCommand(ssid, password));
        } catch (TelloNoValidIMUException e) {
            //Will (hopefully) never happen
            e.printStackTrace();
        }
    }

    private Object[] fetch(ReadCommand cmd) throws TelloNetworkException, TelloCommandTimedOutException, TelloCustomCommandException, TelloGeneralCommandException {
        TelloResponse r = null;
        try {
            r = this.commandConnection.sendCommand(cmd);
        } catch (TelloNoValidIMUException e) {
            //Will (hopefully) never happen
            e.printStackTrace();
        }
        if (r instanceof TelloReadCommandResponse) {
            return ((TelloReadCommandResponse) r).getReturnValues();
        } else {
            throw new TelloNetworkException("Error while parsing input");
        }
    }

    public double fetchSpeed() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException {
        return (double) fetch(new ReadSpeedCommand())[0];
    }

    public int fetchBattery() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException {
        return (int) fetch(new ReadBatteryCommand())[0];
    }

    public int fetchMotorTime() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException {
        return (int) fetch(new ReadMotorTimeCommand())[0];
    }

    public int fetchHeight() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException {
        return (int) fetch(new ReadHeightCommand())[0];
    }

    public int fetchTemperature() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException {
        return (int) fetch(new ReadTemperatureCommand())[0];
    }

    public int[] fetchAttitude() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException {
        Object[] in = fetch(new ReadAttitudeCommand());
        int[] ret = new int[3];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (int) in[i];
        }
        return ret;
    }

    public double fetchBarometer() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException {
        return (double) fetch(new ReadBarometerCommand())[0];
    }

    public double[] fetchAcceleration() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException {
        Object[] in = fetch(new ReadAccelerationCommand());
        double[] ret = new double[3];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (double) in[i];
        }
        return ret;
    }

    public int fetchTOFDistance() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException {
        return (int) fetch(new ReadTOFDistanceCommand())[0];
    }

    public int fetchWifiSnr() throws TelloCommandTimedOutException, TelloNetworkException, TelloCustomCommandException, TelloGeneralCommandException {
        return (int) fetch(new ReadWifiSNRCommand())[0];
    }
}
