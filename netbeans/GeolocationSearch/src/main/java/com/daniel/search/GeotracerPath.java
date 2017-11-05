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

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the path from one IP address to another, containing
 * the geographic search results for each hop.
 *
 * @author Bryan Daniel
 */
@XmlRootElement(name = "geotracer_path")
public class GeotracerPath {

    /**
     * The list of geographic search results
     */
    @XmlElement(name = "geolocations")
    private List<GeolocationResult> geolocations;

    /**
     * Get the value of geolocations
     *
     * @return the value of geolocations
     */
    public List<GeolocationResult> getGeolocations() {
        return geolocations;
    }

    /**
     * Set the value of geolocations
     *
     * @param geolocations new value of geolocations
     */
    public void setGeolocations(List<GeolocationResult> geolocations) {
        this.geolocations = geolocations;
    }

}
