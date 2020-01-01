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
 * Class that unifies all exceptions thrown by this library.
 *
 * @author Fritz Windisch
 */
public class TelloException extends Exception {
    public TelloException(String msg, Exception parent) {
        super(msg, parent);
    }

    public TelloException(String msg) {
        super(msg);
    }
}
