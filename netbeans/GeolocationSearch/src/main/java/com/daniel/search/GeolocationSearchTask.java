/* 
 * Copyright 2017 Bryan Daniel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.daniel.search;

import com.daniel.search.client.GeolocationInquisitor;
import java.util.concurrent.Callable;

/**
 * This class contains a task to pass an IP address to the GeolocationInquisitor
 * search function and return the results.
 *
 * @author Bryan Daniel
 */
public class GeolocationSearchTask implements Callable<GeolocationSearchEventResult> {

    /**
     * The IP address used for the task
     */
    private String ipAddress;

    /**
     * This default constructor sets ipAddress to null.
     */
    public GeolocationSearchTask() {
        this.ipAddress = null;
    }

    /**
     * This parameterized constructor sets the value of ipAddress.
     *
     * @param ipAddress the IP address value
     */
    public GeolocationSearchTask(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public GeolocationSearchEventResult call() throws Exception {
        GeolocationInquisitor inquisitor = new GeolocationInquisitor();
        return inquisitor.findGeolocation(ipAddress);
    }

    /**
     * Sets the value of ipAddress
     *
     * @param ipAddress the new value of ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
