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
package com.daniel.search.client;

import com.daniel.search.GeolocationResult;
import com.daniel.search.GeolocationSearchEventResult;
import java.util.Date;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 * This class contains the functionality to search and return geographic
 * location data.
 *
 * @author Bryan Daniel
 */
public class GeolocationInquisitor {

    /**
     * The location of the web service returning geographic location information
     */
    public static final String SERVICE_URL = "http://freegeoip.net/json";

    /**
     * This method passes the given IP address to a web service to return the
     * associated geographic location data wrapped in a
     * GeolocationSearchEventResult object with the search time.
     *
     * @param ipAddress the IP address
     * @return the GeolocationSearchEventResult object
     * @throws ProcessingException in case the request processing or subsequent
     * I/O operation fails.
     * @throws WebApplicationException in case the response status code of the
     * response returned by the server is not successful and the specified
     * generic response type does not represent Response
     * @throws NullPointerException in case the IP address value is null
     */
    public GeolocationSearchEventResult findGeolocation(String ipAddress) throws ProcessingException,
            WebApplicationException, NullPointerException {

        if (ipAddress == null) {
            throw new NullPointerException("The IP address was null.");
        }

        long timeStarted = System.currentTimeMillis();
        Date timeSearched = new Date(timeStarted);
        Client client = ClientBuilder.newClient();
        GeolocationResult geolocation = client.target(SERVICE_URL).path(ipAddress)
                .request(MediaType.APPLICATION_JSON).get(GeolocationResult.class);
        long timeElapsed = System.currentTimeMillis() - timeStarted;

        GeolocationSearchEventResult geolocationSearchEvent = new GeolocationSearchEventResult();
        geolocationSearchEvent.setTimeSearched(timeSearched);
        geolocationSearchEvent.setTimeElapsed(timeElapsed);
        geolocationSearchEvent.setGeolocation(geolocation);

        return geolocationSearchEvent;
    }
}
