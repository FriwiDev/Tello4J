package me.friwi.tello4j.wifi.impl.command.set;

import me.friwi.tello4j.wifi.model.command.SetCommand;

public class SetWifiPasswordAndSSIDCommand extends SetCommand {
    private String ssid, password;

    public SetWifiPasswordAndSSIDCommand(String ssid, String password) {
        super("wifi " + ssid + " " + password);
        this.ssid = ssid;
        this.password = password;
    }
}
