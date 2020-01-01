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

package me.friwi.tello4j.wifi.impl.response;

import me.friwi.tello4j.api.exception.TelloNetworkException;
import me.friwi.tello4j.wifi.model.command.TelloCommand;
import me.friwi.tello4j.wifi.model.response.TelloResponse;

public class TelloReadCommandResponse extends TelloResponse {
    private Object[] returnValues = new Object[1];
    private char[] numberChars = new char[]{
            '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'
    };

    public TelloReadCommandResponse(TelloCommand command, String message) throws TelloNetworkException {
        super(command, null, message);
        try {
            String[] arguments = message.trim().split(";");
            returnValues = new Object[arguments.length];
            for (int i = 0; i < returnValues.length; i++) {
                if (arguments[i].contains(":")) arguments[i] = arguments[i].split(":")[1];
                String number = fetchFirstNumber(arguments[i]);
                if (number.contains(".")) {
                    double value = Double.parseDouble(number);
                    if (arguments[i].contains("dm")) value *= 10; //Return in cm
                    returnValues[i] = value;
                } else {
                    int value = Integer.parseInt(number);
                    if (arguments[i].contains("dm")) value *= 10; //Return in cm
                    returnValues[i] = value;
                }
            }
        } catch (Exception e) {
            this.commandResultType = CommandResultType.ERROR;
            throw new TelloNetworkException("Error while parsing input \"" + message + "\"", e);
        }
        this.commandResultType = CommandResultType.OK;
    }

    /**
     * Extracts the first number from a character sequence (skips all letters)
     *
     * @param argument
     * @return
     */
    private String fetchFirstNumber(String argument) {
        int begin = -1;
        int l = argument.length();
        char[] a = argument.toCharArray();
        for (int i = 0; i < l; i++) {
            boolean correct = false;
            for (char c : numberChars) {
                if (c == a[i]) {
                    correct = true;
                    break;
                }
            }
            if (correct && begin == -1) {
                begin = i;
            }
            if (!correct && begin != -1) {
                return argument.substring(begin, i);
            }
        }
        if (begin != -1) {
            return argument.substring(begin, l);
        } else {
            return "error while extracting " + argument;
        }
    }

    public Object[] getReturnValues() {
        return returnValues;
    }
}
