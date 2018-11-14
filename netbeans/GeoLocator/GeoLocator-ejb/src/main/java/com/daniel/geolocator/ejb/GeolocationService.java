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

import com.daniel.search.GeolocationResult;
import com.daniel.search.GeolocationSearchEventResult;
import com.daniel.search.client.GeolocationInquisitor;
import java.text.MessageFormat;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This service contains the functionality for retrieving geographic location
 * data.
 *
 * @author Bryan Daniel
 */
@Stateless
@LocalBean
public class GeolocationService {

    /**
     * The event propagator
     */
    @EJB
    private EventPropagator eventPropagator;

    /**
     * The logger for this class
     */
    private final Logger logger = LogManager.getLogger(GeolocationService.class);

    /**
     * This method passes the given IP address to the geographic location search
     * utility to return the associated geographic location data.
     *
     * @param ipAddress the IP address
     * @return the GeolocationResult object
     */
    public GeolocationResult findGeolocation(String ipAddress) {

        GeolocationSearchEventResult geolocationSearchEvent = new GeolocationSearchEventResult();
        try {
            GeolocationInquisitor inquisitor = new GeolocationInquisitor();
            geolocationSearchEvent = inquisitor.findGeolocation(ipAddress);
            eventPropagator.fireSearchEvent(geolocationSearchEvent);
            logger.info(MessageFormat.format("A geographic location search was successfully performed for IP address: {0}",
                    ipAddress));
        } catch (ProcessingException | WebApplicationException | NullPointerException e) {
            if (e instanceof NotFoundException) {
                logger.info(MessageFormat.format("The request for IP address, {0}, returned a \"404 Not Found\" response.",
                        ipAddress));
            } else {
                logger.error(MessageFormat.format("An exception occurred searching for IP address: {0}",
                        ipAddress), e);
            }
        }
        return geolocationSearchEvent.getGeolocation();
    }
}
