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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.daniel.search.GeolocationResult;
import com.daniel.search.GeolocationSearchEventResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This class contains the functionality to search and return geographic
 * location data.
 *
 * @author Bryan Daniel
 */
public class GeolocationInquisitor {

    /**
     * The location of the web service returning geographic location
     * information. The old location was http://freegeoip.net/json. The service
     * was shut down, so the new location is an instance of the old application
     * running locally.
     */
    public static final String SERVICE_URL = "http://localhost:16101/json";

    /**
     * The logger for this class
     */
    private final Logger logger;

    /**
     * Sets the values for instance variables.
     */
    public GeolocationInquisitor() {
        logger = LogManager.getLogger(GeolocationInquisitor.class);
    }

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
        logger.info("Preparing geoloation search service call.");
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();

        long timeStarted = System.currentTimeMillis();
        Date timeSearched = new Date(timeStarted);
        Client client = ClientBuilder.newClient();
        String geolocationString = client.target(SERVICE_URL).path(ipAddress)
                .request(MediaType.APPLICATION_JSON).get(String.class);

        logger.info(MessageFormat.format("Geoloation search result:\n{0}", geolocationString));

        GeolocationSearchEventResult geolocationSearchEvent = new GeolocationSearchEventResult();

        try {
            GeolocationResult geolocation = mapper.readValue(geolocationString, GeolocationResult.class);

            logger.info(MessageFormat.format("Geoloation search object:\n{0}", 
                    objectWriter.writeValueAsString(geolocation)));

            long timeElapsed = System.currentTimeMillis() - timeStarted;
            geolocationSearchEvent.setTimeSearched(timeSearched);
            geolocationSearchEvent.setTimeElapsed(timeElapsed);
            geolocationSearchEvent.setGeolocation(geolocation);

            logger.info(MessageFormat.format("Geoloation search event to send:\n{0}", objectWriter
                    .writeValueAsString(geolocationSearchEvent)));
        } catch (JsonProcessingException ex) {
            logger.error("JsonProcessingException occurred in findGeolocation method.", ex);
        } catch (IOException ex) {
            logger.error("IOException occurred in findGeolocation method.", ex);
        }

        return geolocationSearchEvent;
    }
}
