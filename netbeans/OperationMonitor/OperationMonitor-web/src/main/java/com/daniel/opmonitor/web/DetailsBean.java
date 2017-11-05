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
package com.daniel.opmonitor.web;

import com.daniel.opmonitor.entity.Geolocation;
import com.daniel.opmonitor.entity.GeolocationSearchEvent;
import com.daniel.opmonitor.entity.GeotracerEvent;
import com.daniel.opmonitor.entity.TracerHop;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This managed bean holds the information and functionality to support the
 * event details page.
 *
 * @author Bryan Daniel
 */
@Named(value = "detailsBean")
@SessionScoped
public class DetailsBean implements Serializable {

    /**
     * serial version UID
     */
    private static final long serialVersionUID = -6635059200753460162L;

    /**
     * The selected geographic data search event
     */
    private GeolocationSearchEvent selectedSearchEvent;

    /**
     * The selected trace route event
     */
    private GeotracerEvent selectedTracerEvent;

    /**
     * The hop locations for the selected tracer event
     */
    private List<Geolocation> hopLocations;

    /**
     * This method clears any stored tracer event and returns the details page
     * target.
     *
     * @return the target for the details page
     */
    public String viewSearchEvent() {
        setSelectedTracerEvent(null);
        setHopLocations(null);
        return "/monitor/details";
    }

    /**
     * This method clears any stored search event and returns the details page
     * target.
     *
     * @return the target for the details page
     */
    public String viewTracerEvent() {
        setSelectedSearchEvent(null);
        hopLocations = new ArrayList<>();       
        
        for (TracerHop hop : selectedTracerEvent.getTracerHopList()) {
            if (hop.getHopSearch() != null){
                hopLocations.add(hop.getHopSearch().getSearch().getLocation());
            }            
        }
        return "/monitor/details";
    }

    /**
     * Get the value of selectedSearchEvent
     *
     * @return the value of selectedSearchEvent
     */
    public GeolocationSearchEvent getSelectedSearchEvent() {
        return selectedSearchEvent;
    }

    /**
     * Set the value of selectedSearchEvent
     *
     * @param selectedSearchEvent new value of selectedSearchEvent
     */
    public void setSelectedSearchEvent(GeolocationSearchEvent selectedSearchEvent) {
        this.selectedSearchEvent = selectedSearchEvent;
    }

    /**
     * Get the value of selectedTracerEvent
     *
     * @return the value of selectedTracerEvent
     */
    public GeotracerEvent getSelectedTracerEvent() {
        return selectedTracerEvent;
    }

    /**
     * Set the value of selectedTracerEvent
     *
     * @param selectedTracerEvent new value of selectedTracerEvent
     */
    public void setSelectedTracerEvent(GeotracerEvent selectedTracerEvent) {
        this.selectedTracerEvent = selectedTracerEvent;
    }

    /**
     * Get the value of hopLocations
     *
     * @return the value of hopLocations
     */
    public List<Geolocation> getHopLocations() {
        return hopLocations;
    }

    /**
     * Set the value of hopLocations
     *
     * @param hopLocations new value of hopLocations
     */
    public void setHopLocations(List<Geolocation> hopLocations) {
        this.hopLocations = hopLocations;
    }
    
    /**
     * This method returns the selected search location as a JSON string.
     *
     * @return the selected search location as a string
     */
    public String getSearchLocationString(){
        
        String searchLocationString = null;
        if (selectedSearchEvent != null) {
            try {
                searchLocationString = new ObjectMapper().writeValueAsString(selectedSearchEvent.getLocation());
            } catch (JsonProcessingException ex) {
                Logger.getLogger(DetailsBean.class.getName()).log(Level.SEVERE, 
                        "An exception occurred in the getSearchLocationString method.", ex);
            }
        }
        return searchLocationString;
    }

    /**
     * This method returns the hop locations as a JSON string.
     *
     * @return the hop locations as a string
     */
    public String getHopLocationsString() {

        String tracerString = null;
        if (hopLocations != null) {
            try {
                tracerString = new ObjectMapper().writeValueAsString(hopLocations);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(DetailsBean.class.getName()).log(Level.SEVERE, 
                        "An exception occurred in the getTracerString method.", ex);
            }
        }
        return tracerString;
    }
}
