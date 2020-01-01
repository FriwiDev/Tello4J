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

import me.friwi.tello4j.api.exception.*;
import me.friwi.tello4j.wifi.model.response.TelloResponse;

public abstract class TelloCommand {
    private TelloResponse response;
    private TelloException exception;

    public abstract String serializeCommand();

    public abstract TelloResponse buildResponse(String data) throws TelloGeneralCommandException, TelloNoValidIMUException, TelloCustomCommandException, TelloNetworkException;

    public TelloResponse getResponse() {
        return response;
    }

    public void setResponse(TelloResponse response) {
        this.response = response;
    }

    public TelloException getException() {
        return exception;
    }

    public void setException(TelloException exception) {
        this.exception = exception;
    }
}
