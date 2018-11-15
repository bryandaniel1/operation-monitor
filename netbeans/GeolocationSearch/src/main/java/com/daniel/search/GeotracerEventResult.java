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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * This class represents the geographic tracer event.
 *
 * @author Bryan Daniel
 */
@JsonRootName(value = "geotracer_event")
public class GeotracerEventResult {

    /**
     * The time of traceroute function execution
     */
    @JsonProperty("time_executed")
    private Date timeExecuted;

    /**
     * The time taken to complete the trace
     */
    @JsonProperty("time_elapsed")
    private long timeElapsed;

    /**
     * The map of geographic locations collected for the hops
     */
    @JsonProperty("hops")
    private LinkedHashMap<Integer, GeolocationSearchEventResult> hops;

    /**
     * Get the value of timeExecuted
     *
     * @return the value of timeExecuted
     */
    public Date getTimeExecuted() {
        return timeExecuted;
    }

    /**
     * Set the value of timeExecuted
     *
     * @param timeExecuted new value of timeExecuted
     */
    public void setTimeExecuted(Date timeExecuted) {
        this.timeExecuted = timeExecuted;
    }

    /**
     * Get the value of timeElapsed
     *
     * @return the value of timeElapsed
     */
    public long getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * Set the value of timeElapsed
     *
     * @param timeElapsed new value of timeElapsed
     */
    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    /**
     * Get the value of hops
     *
     * @return the value of hops
     */
    public LinkedHashMap<Integer, GeolocationSearchEventResult> getHops() {
        return hops;
    }

    /**
     * Set the value of hops
     *
     * @param hops new value of hops
     */
    public void setHops(LinkedHashMap<Integer, GeolocationSearchEventResult> hops) {
        this.hops = hops;
    }
}
