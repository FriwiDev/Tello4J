package me.friwi.tello4j.api.state;

/**
 * This class represents one full state of the tello drone
 *
 * @author Fritz Windisch
 */
public class TelloDroneState {
    private int pitch, roll, yaw, speedX, speedY, speedZ, tempLow, tempHigh, tofDistance, height, battery, motorTime;
    private double barometer, accelerationX, accelerationY, accelerationZ;

    public TelloDroneState(int pitch, int roll, int yaw, int speedX, int speedY, int speedZ, int tempLow, int tempHigh, int tofDistance, int height, int battery, int motorTime, double barometer, double accelerationX, double accelerationY, double accelerationZ) {
        this.pitch = pitch;
        this.roll = roll;
        this.yaw = yaw;
        this.speedX = speedX;
        this.speedY = speedY;
        this.speedZ = speedZ;
        this.tempLow = tempLow;
        this.tempHigh = tempHigh;
        this.tofDistance = tofDistance;
        this.height = height;
        this.battery = battery;
        this.motorTime = motorTime;
        this.barometer = barometer;
        this.accelerationX = accelerationX;
        this.accelerationY = accelerationY;
        this.accelerationZ = accelerationZ;
    }

    public int getPitch() {
        return pitch;
    }

    public int getRoll() {
        return roll;
    }

    public int getYaw() {
        return yaw;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public int getSpeedZ() {
        return speedZ;
    }

    public int getTempLow() {
        return tempLow;
    }

    public int getTempHigh() {
        return tempHigh;
    }

    public int getTofDistance() {
        return tofDistance;
    }

    public int getHeight() {
        return height;
    }

    public int getBattery() {
        return battery;
    }

    public int getMotorTime() {
        return motorTime;
    }

    public double getBarometer() {
        return barometer;
    }

    public double getAccelerationX() {
        return accelerationX;
    }

    public double getAccelerationY() {
        return accelerationY;
    }

    public double getAccelerationZ() {
        return accelerationZ;
    }

    @Override
    public String toString() {
        return "TelloDroneState{" +
                "pitch=" + pitch +
                ", roll=" + roll +
                ", yaw=" + yaw +
                ", speedX=" + speedX +
                ", speedY=" + speedY +
                ", speedZ=" + speedZ +
                ", tempLow=" + tempLow +
                ", tempHigh=" + tempHigh +
                ", tofDistance=" + tofDistance +
                ", height=" + height +
                ", battery=" + battery +
                ", motorTime=" + motorTime +
                ", barometer=" + barometer +
                ", accelerationX=" + accelerationX +
                ", accelerationY=" + accelerationY +
                ", accelerationZ=" + accelerationZ +
                '}';
    }
}
