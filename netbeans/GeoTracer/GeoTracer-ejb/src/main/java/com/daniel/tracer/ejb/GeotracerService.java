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
package com.daniel.tracer.ejb;

import com.daniel.search.GeolocationResult;
import com.daniel.search.GeotracerEventResult;
import com.daniel.search.GeotracerPath;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.enterprise.concurrent.ManagedExecutorService;

/**
 * This service contains the functionality for retrieving the path from one IP
 * address to another and the geographic location data for the hops.
 *
 * @author Bryan Daniel
 */
@Singleton
@LocalBean
public class GeotracerService {

    /**
     * The managed executor service
     */
    @Resource
    private ManagedExecutorService managedExecutorService;

    /**
     * The event propagator
     */
    @EJB
    private GeotracerEventRelay eventRelay;

    /**
     * This method passes the request and destination IP addresses to the
     * GeotracerPathFinder utility to find and return the list of geographic
     * location objects for each hop in the path.
     *
     * @param requestIpAddress the request IP address
     * @param destinationIpAddress the destination IP address
     * @return the list of GeolocationResult objects
     */
    public GeotracerPath findGeotracerPath(String requestIpAddress, String destinationIpAddress) {

        GeotracerEventResult geotracerEvent = GeotracerPathFinder.findGeotracerPath(requestIpAddress,
                destinationIpAddress, managedExecutorService);
        List<GeolocationResult> geolocations = new LinkedList<>();
        for (Integer hopNumber : geotracerEvent.getHops().keySet()) {
            if (geotracerEvent.getHops().get(hopNumber) == null) {
                geolocations.add(null);
            } else {
                geolocations.add(geotracerEvent.getHops().get(hopNumber).getGeolocation());
            }
        }
        GeotracerPath path = new GeotracerPath();
        path.setGeolocations(geolocations);
        eventRelay.fireSearchEvent(geotracerEvent);

        return path;
    }
}
