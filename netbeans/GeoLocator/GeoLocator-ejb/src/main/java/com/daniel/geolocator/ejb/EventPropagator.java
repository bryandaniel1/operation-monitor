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
package com.daniel.geolocator.ejb;

import com.daniel.search.GeolocationSearchEventResult;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * This class fires search events to relay information concerning executed
 * tasks.
 *
 * @author Bryan Daniel
 */
@Stateless
@LocalBean
public class EventPropagator {

    /**
     * The search event
     */
    @Inject
    private Event<GeolocationSearchEventResult> searchEvent;

    /**
     * This method fires the search event to be handled by whatever listeners
     * are waiting for it. The method is executed asynchronously in case the
     * observer method(s) handling the event are long-running.
     *
     * @param geolocationSearchEvent the geographic location data search event
     */
    @Asynchronous
    public void fireSearchEvent(GeolocationSearchEventResult geolocationSearchEvent) {

        searchEvent.fire(geolocationSearchEvent);
        Logger.getLogger(EventPropagator.class.getName()).log(Level.INFO,
                MessageFormat.format("A geographic location search event was fired for event timed: {0}",
                        geolocationSearchEvent.getTimeSearched()));
    }
}
