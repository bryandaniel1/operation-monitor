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

import com.daniel.search.GeolocationSearchEventResult;
import com.daniel.search.GeolocationSearchTask;
import com.daniel.search.GeotracerEventResult;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.enterprise.concurrent.ManagedExecutorService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This class runs the traceroute program to collect information on the path
 * from a request address to the destination address.
 *
 * @author Bryan Daniel
 */
public final class GeotracerPathFinder {

    /**
     * The command and parameters for the traceroute function: -n no mapping to
     * host names -w the response wait time -q the number of probe packets sent
     * per hop -N the number of concurrent packet launches -m the maximum number
     * of hops to probe
     */
    public static final String TRACEROUTE_COMMAND = "traceroute -n -w 2 -q 1 -N 17 -m 17 ";

    /**
     * The logger for this class
     */
    private static final Logger LOGGER = LogManager.getLogger(GeotracerPathFinder.class);

    /**
     * Private constructor - not called
     */
    private GeotracerPathFinder() {
    }

    /**
     * This method invokes the sub-routines for finding the IP addresses in the
     * path and the geographic location data for each IP address. The map of
     * results is returned from the method.
     *
     * @param requestIpAddress the IP address of the request
     * @param destinationIpAddress the host name/IP address of the destination
     * @param managedExecutorService the executor service
     * @return the map of results
     */
    public static GeotracerEventResult findGeotracerPath(String requestIpAddress,
            String destinationIpAddress, ManagedExecutorService managedExecutorService) {

        long timeStarted = System.currentTimeMillis();
        LinkedHashMap<Integer, String> ipAddresses = collectAddresses(requestIpAddress,
                destinationIpAddress, managedExecutorService);
        LinkedHashMap<Integer, GeolocationSearchEventResult> hopLocations
                = fetchLocations(ipAddresses, managedExecutorService);
        long timeElapsed = System.currentTimeMillis() - timeStarted;

        GeotracerEventResult geotracerEvent = new GeotracerEventResult();
        geotracerEvent.setTimeExecuted(new Date(timeStarted));
        geotracerEvent.setTimeElapsed(timeElapsed);
        geotracerEvent.setHops(hopLocations);

        return geotracerEvent;
    }

    /**
     * This method collects IP addresses in the tracer path by creating and
     * executing TracerouteRunner tasks.
     *
     * @param requestIpAddress the IP address of the request
     * @param destinationIpAddress the host name/IP address of the destination
     * @param managedExecutorService the executor service
     * @return the map populated with keys
     */
    private static LinkedHashMap<Integer, String> collectAddresses(String requestIpAddress,
            String destinationIpAddress, ManagedExecutorService managedExecutorService) {

        long time = System.currentTimeMillis();
        LinkedHashMap<Integer, String> ipAddresses = new LinkedHashMap<>();
        try {
            StringBuilder firstCommand = new StringBuilder();
            firstCommand.append(TRACEROUTE_COMMAND);
            firstCommand.append(requestIpAddress);
            StringBuilder secondCommand = new StringBuilder();
            secondCommand.append(TRACEROUTE_COMMAND);
            secondCommand.append(destinationIpAddress);

            TracerouteRunner firstTask = new TracerouteRunner(firstCommand.toString());
            TracerouteRunner secondTask = new TracerouteRunner(secondCommand.toString());

            Future<LinkedList<String>> firstResults = managedExecutorService.submit(firstTask);
            Future<LinkedList<String>> secondResults = managedExecutorService.submit(secondTask);
            LinkedList<String> pathToRequest = firstResults.get();
            LinkedList<String> pathToDestination = secondResults.get();

            // the hops to the request are added first in descending order
            Iterator<String> descendingIterator = pathToRequest.descendingIterator();
            Integer hopCount = 1;
            while (descendingIterator.hasNext()) {
                ipAddresses.put(hopCount++, descendingIterator.next());
            }

            // the hops to the destination are added last in ascending order
            Iterator<String> ascendingIterator = pathToDestination.iterator();
            while (ascendingIterator.hasNext()) {
                ipAddresses.put(hopCount++, ascendingIterator.next());
            }

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            LOGGER.error("GeotracerPathFinder.collectAddresses ExecutionException.", e);
        }
        LOGGER.info(MessageFormat.format("GeotracerPathFinder.collectAddresses time elapsed: {0}ms",
                System.currentTimeMillis() - time));

        return ipAddresses;
    }

    /**
     * This method sets the values for the hop-locations map by invoking
     * GeolocationSearchTask tasks for each IP address.
     *
     * @param hopLocations the map of hop locations
     * @param managedExecutorService the executor service
     */
    private static LinkedHashMap<Integer, GeolocationSearchEventResult> fetchLocations(
            LinkedHashMap<Integer, String> ipAddresses, ManagedExecutorService managedExecutorService) {

        long time = System.currentTimeMillis();
        LinkedHashMap<Integer, GeolocationSearchEventResult> hopLocations = new LinkedHashMap<>();
        List<GeolocationSearchTask> tasks = new ArrayList<>();
        for (Integer hopNumber : ipAddresses.keySet()) {
            if (!ipAddresses.get(hopNumber).equals(TracerouteRunner.NO_RESPONSE)) {
                GeolocationSearchTask task = new GeolocationSearchTask(ipAddresses.get(hopNumber));
                tasks.add(task);
            }
        }
        try {
            List<Future<GeolocationSearchEventResult>> resultList = managedExecutorService.invokeAll(tasks);
            for (Integer hopNumber : ipAddresses.keySet()) {
                String ipAddress = ipAddresses.get(hopNumber);
                if (ipAddress.equals(TracerouteRunner.NO_RESPONSE)) {
                    hopLocations.put(hopNumber, null);
                } else {
                    for (Future<GeolocationSearchEventResult> next : resultList) {
                        if (ipAddress.equals(next.get().getGeolocation().getIpAddress())) {
                            if (next.get().getGeolocation().getLatitude() == 0
                                    && next.get().getGeolocation().getLongitude() == 0) {
                                hopLocations.put(hopNumber, null);
                            } else {
                                hopLocations.put(hopNumber, next.get());
                            }
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException ee) {
            LOGGER.error("The task threw an error.", ee);
        }
        LOGGER.info(MessageFormat.format("GeotracerPathFinder.fetchLocations time elapsed: {0}ms",
                System.currentTimeMillis() - time));

        return hopLocations;
    }

}
