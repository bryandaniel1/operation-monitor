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
package com.daniel.geolocator.web;

import com.daniel.geolocator.ejb.GeolocationService;
import com.daniel.search.GeolocationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This REST Web Service handles requests to search for geographic location
 * information.
 *
 * @author Bryan Daniel
 */
@Path("/")
@RequestScoped
public class GeolocationResource {

    /**
     * The geographic location service
     */
    @EJB
    private GeolocationService geolocationService;

    /**
     * This method takes an IP address parameter and invokes the
     * geographic-location service to find and return the associated geographic
     * data.
     *
     * @param ipAddress the IP address
     * @return the response object
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("find")
    public Response findGeolocation(@FormParam("ipAddress") String ipAddress) {
        String msg;

        // IP address required for operation
        if (ipAddress == null) {
            msg = "Property 'ipAddress' is missing.";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }

        GeolocationResult geolocation = geolocationService.findGeolocation(ipAddress);
        return Response.ok(toJson(geolocation), "application/json").build();
    }

    /**
     * This method converts the GeolocationResult object to a JSON string.
     *
     * @param geolocation the geographic location data
     * @return the JSON string
     */
    private String toJson(GeolocationResult geolocation) {
        String json = "";
        try {
            json = new ObjectMapper().writeValueAsString(geolocation);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(GeolocationResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
}
