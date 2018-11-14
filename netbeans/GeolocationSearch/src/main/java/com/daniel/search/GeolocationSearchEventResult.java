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
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the geographic location search event.
 *
 * @author Bryan Daniel
 */
@XmlRootElement(name = "geolocation_search_event")
public class GeolocationSearchEventResult {

    /**
     * The time the geographic location was searched
     */
    @XmlElement(name = "time_searched")
    @JsonProperty("time_searched")
    private Date timeSearched;
    
    /**
     * The time taken to complete the search
     */
    @XmlElement(name = "time_elapsed")
    @JsonProperty("time_elapsed")
    private long timeElapsed;

    /**
     * The geographic location data
     */
    @XmlElement(name = "geolocation")
    @JsonProperty("geolocation")
    private GeolocationResult geolocation;

    /**
     * Get the value of timeSearched
     *
     * @return the value of timeSearched
     */
    public Date getTimeSearched() {
        return timeSearched;
    }

    /**
     * Set the value of timeSearched
     *
     * @param timeSearched new value of timeSearched
     */
    public void setTimeSearched(Date timeSearched) {
        this.timeSearched = timeSearched;
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
     * Get the value of geolocation
     *
     * @return the value of geolocation
     */
    public GeolocationResult getGeolocation() {
        return geolocation;
    }

    /**
     * Set the value of geolocation
     *
     * @param geolocation new value of geolocation
     */
    public void setGeolocation(GeolocationResult geolocation) {
        this.geolocation = geolocation;
    }
}
