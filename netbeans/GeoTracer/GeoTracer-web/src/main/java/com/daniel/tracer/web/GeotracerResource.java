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
package com.daniel.tracer.web;

import com.daniel.tracer.ejb.GeotracerService;
import com.daniel.search.GeolocationResult;
import com.daniel.search.GeotracerPath;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This REST Web Service handles requests to search for geographic location
 * information.
 *
 * @author Bryan Daniel
 */
@Path("/")
@RequestScoped
public class GeotracerResource {

    /**
     * The geographic location service
     */
    @EJB
    private GeotracerService geotracerService;

    /**
     * The logger for this class
     */
    private final Logger logger = LogManager.getLogger(GeotracerResource.class);

    /**
     * This method takes a destination IP address parameter and invokes the
     * tracer service to find and return the geographic data of the path from
     * the request address to the destination address.
     *
     * @param requestIpAddress the request IP address
     * @param destinationIpAddress the destination IP address
     * @return the response object
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("find")
    public Response findGeotracerPath(@FormParam("requestIpAddress") String requestIpAddress,
            @FormParam("destinationIpAddress") String destinationIpAddress) {
        String msg;

        // destination IP address required for operation
        if (requestIpAddress == null || destinationIpAddress == null) {
            if (requestIpAddress == null) {
                msg = "Property 'requestIpAddress' is missing.";
            } else {
                msg = "Property 'destinationIpAddress' is missing.";
            }
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }

        GeotracerPath geotracerPath = geotracerService.findGeotracerPath(requestIpAddress,
                destinationIpAddress);
        return Response.ok(toJson(geotracerPath.getGeolocations()), "application/json").build();
    }

    /**
     * This method converts the list of GeolocationResult objects to a JSON
     * string.
     *
     * @param geolocation the list geographic location data objects
     * @return the JSON string
     */
    private String toJson(List<GeolocationResult> geolocations) {
        String json = "";
        try {
            json = new ObjectMapper().writeValueAsString(geolocations);
        } catch (JsonProcessingException ex) {
            logger.error("A JsonProcessingException occurred in the toJson method.", ex);
        }
        return json;
    }
}
