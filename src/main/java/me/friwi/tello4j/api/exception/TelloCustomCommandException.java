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

package me.friwi.tello4j.api.exception;


/**
 * This exception is thrown, when the drone replies to a command with a custom error message.
 * The message can be obtained by calling {@link #getReason()}
 *
 * @author Fritz Windisch
 */
public class TelloCustomCommandException extends TelloCommandException {
    private String reason;

    public TelloCustomCommandException(String msg, String reason, Exception parent) {
        super(msg, parent);
    }

    public TelloCustomCommandException(String msg, String reason) {
        super(msg);
    }

    public String getReason() {
        return reason;
    }
}
