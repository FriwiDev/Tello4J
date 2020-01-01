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

package me.friwi.tello4j.wifi.model.command;

import me.friwi.tello4j.api.exception.TelloCustomCommandException;
import me.friwi.tello4j.api.exception.TelloGeneralCommandException;
import me.friwi.tello4j.api.exception.TelloNetworkException;
import me.friwi.tello4j.api.exception.TelloNoValidIMUException;
import me.friwi.tello4j.wifi.impl.response.CommandResultType;
import me.friwi.tello4j.wifi.impl.response.TelloReadCommandResponse;
import me.friwi.tello4j.wifi.model.response.TelloResponse;

public abstract class ReadCommand extends TelloCommand {
    private String command;

    public ReadCommand(String command) {
        this.command = command + "?";
    }

    @Override
    public String serializeCommand() {
        return this.command;
    }

    @Override
    public TelloResponse buildResponse(String data) throws TelloNetworkException, TelloGeneralCommandException, TelloNoValidIMUException, TelloCustomCommandException {
        TelloReadCommandResponse response = new TelloReadCommandResponse(this, data);
        if (response.getCommandResultType() == CommandResultType.ERROR) {
            if (response.getMessage().equalsIgnoreCase("error")) throw new TelloGeneralCommandException();
            if (response.getMessage().equalsIgnoreCase("error No valid imu")) throw new TelloNoValidIMUException();
            throw new TelloCustomCommandException("Error while executing command \"" + serializeCommand() + "\": " + response.getMessage(), response.getMessage());
        }
        return response;
    }
}
