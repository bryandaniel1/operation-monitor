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
package com.daniel.opmonitor.ejb;

import com.daniel.opmonitor.entity.Country;
import com.daniel.opmonitor.entity.Geolocation;
import com.daniel.opmonitor.entity.GeolocationSearchEvent;
import com.daniel.opmonitor.entity.GeotracerEvent;
import com.daniel.opmonitor.entity.HopSearch;
import com.daniel.opmonitor.entity.Region;
import com.daniel.opmonitor.entity.TracerHop;
import com.daniel.search.GeolocationResult;
import com.daniel.search.GeolocationSearchEventResult;
import com.daniel.search.GeotracerEventResult;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * This implementation of GeolocationService contains methods for accessing and
 * storing geographic location data.
 *
 * @author Bryan Daniel
 */
@Stateless
public class SimpleGeolocationService implements GeolocationService {

    /**
     * The precision for latitude values
     */
    public static final int LATITUDE_PRECISION = 6;

    /**
     * The precision for longitude values
     */
    public static final int LONGITUDE_PRECISION = 7;

    /**
     * The scale for latitude and longitude values
     */
    public static final int COORDINATE_SCALE = 4;

    /**
     * The EJB context
     */
    @Resource
    private EJBContext context;

    /**
     * The entity manager for the chat room entities
     */
    @PersistenceContext(unitName = "OperationMonitor-ejbPU")
    private EntityManager entityManager;

    /**
     * This method retrieves the list of geographic location search events
     * occurring on the given date.
     *
     * @param dateOccurred the date of the search event
     * @return the list of all GeolocationSearchEvent entities for the given
     * date or null if an exception occurs
     */
    @Override
    public List<GeolocationSearchEvent> findGeolocationSearchEvents(Date dateOccurred) {

        Query query = entityManager.createNamedQuery("GeolocationSearchEvent.findEventsByDay");
        List<GeolocationSearchEvent> geolocationSearchEvents = null;

        // getting a Calendar set to provide the next day's date
        Calendar dayAfter = Calendar.getInstance();
        dayAfter.setTime(dateOccurred);
        dayAfter.add(Calendar.DATE, 1);

        try {
            geolocationSearchEvents = query.setParameter("daySearched", dateOccurred)
                    .setParameter("dayAfter", dayAfter.getTime()).getResultList();
        } catch (Exception e) {
            Logger.getLogger(SimpleGeolocationService.class.getName()).log(Level.SEVERE,
                    "An exception occurred in the findGeolocationSearchEvents method.", e);
        }
        return geolocationSearchEvents;
    }

    /**
     * This method retrieves the list of tracer events occurring on the given
     * date.
     *
     * @param dateOccurred the date of the tracer event
     * @return the list of all GeotracerEvent entities for the given date or
     * null if an exception occurs
     */
    @Override
    public List<GeotracerEvent> findGeotracerEvents(Date dateOccurred) {

        Query query = entityManager.createNamedQuery("GeotracerEvent.findEventsByDay");
        List<GeotracerEvent> geotracerEvents = null;

        // getting a Calendar set to provide the next day's date
        Calendar dayAfter = Calendar.getInstance();
        dayAfter.setTime(dateOccurred);
        dayAfter.add(Calendar.DATE, 1);

        try {
            geotracerEvents = query.setParameter("daySearched", dateOccurred)
                    .setParameter("dayAfter", dayAfter.getTime()).getResultList();
        } catch (Exception e) {
            Logger.getLogger(SimpleGeolocationService.class.getName()).log(Level.SEVERE,
                    "An exception occurred in the findGeolocationSearchEvents method.", e);
        }
        return geotracerEvents;
    }

    /**
     * This method saves data for the given GeolocationSearchEventResult object
     * to the database.
     *
     * @param geolocationSearchEventResult the GeolocationSearchEventResult
     * object
     * @return the indication of operation success or failure
     */
    @Override
    public boolean storeGeolocationSearchEvent(GeolocationSearchEventResult geolocationSearchEventResult) {

        try {
            GeolocationSearchEvent geolocationSearchEvent = new GeolocationSearchEvent();
            geolocationSearchEvent.setSearchId(null);
            geolocationSearchEvent.setTimeSearched(geolocationSearchEventResult.getTimeSearched());
            geolocationSearchEvent.setTimeElapsed(geolocationSearchEventResult.getTimeElapsed());
            geolocationSearchEvent.setLocation(storeGeolocation(geolocationSearchEventResult.getGeolocation()));
            entityManager.persist(geolocationSearchEvent);
            entityManager.flush();
            return true;
        } catch (ValidationException ve) {
            Logger.getLogger(SimpleGeolocationService.class.getName()).log(Level.SEVERE,
                    "SimpleGeolocationService: A ValidationException occurred in the storeGeolocationSearchEvent method: {0}",
                    ve.getMessage());
            context.setRollbackOnly();
        } catch (Exception e) {
            Logger.getLogger(SimpleGeolocationService.class.getName()).log(Level.SEVERE,
                    "SimpleGeolocationService: An Exception occurred in the storeGeolocationSearchEvent method: {0}",
                    e.getMessage());
            context.setRollbackOnly();
        }
        return false;
    }

    /**
     * This method saves data for the given GeotracerEventResult object to the
     * database. The entities to be stored are the GeotracerEvent entity along
     * with the list of TracerHop entities, and the HopSearch,
     * GeolocationSearchEvent, and Geolocation entities for each hop resulting
     * in a response.
     *
     * @param geotracerEventResult the GeotracerEventResult object
     * @return the indication of operation success or failure
     */
    @Override
    public boolean storeGeotracerEvent(GeotracerEventResult geotracerEventResult) {

        try {
            GeotracerEvent geotracerEvent = new GeotracerEvent();
            geotracerEvent.setTracerId(null);
            geotracerEvent.setTimeExecuted(geotracerEventResult.getTimeExecuted());
            geotracerEvent.setTimeElapsed(geotracerEventResult.getTimeElapsed());

            // each hop in the route is stored
            LinkedHashMap<Integer, GeolocationSearchEventResult> results = geotracerEventResult.getHops();
            List<TracerHop> tracerHopList = new ArrayList<>();
            for (Integer hopOrder : results.keySet()) {
                TracerHop tracerHop = storeTracerHop(hopOrder, geotracerEvent);
                tracerHopList.add(tracerHop);

                // non-null search results are stored
                GeolocationSearchEventResult searchResult = results.get(hopOrder);
                if (searchResult != null) {
                    GeolocationSearchEvent geolocationSearchEvent = new GeolocationSearchEvent();
                    geolocationSearchEvent.setSearchId(null);
                    geolocationSearchEvent.setTimeSearched(searchResult.getTimeSearched());
                    geolocationSearchEvent.setTimeElapsed(searchResult.getTimeElapsed());
                    geolocationSearchEvent.setLocation(storeGeolocation(searchResult.getGeolocation()));
                    HopSearch hopSearch = createHopSearch(tracerHop, geolocationSearchEvent);
                    geolocationSearchEvent.setHopSearch(hopSearch);
                }
            }
            geotracerEvent.setTracerHopList(tracerHopList);
            entityManager.flush();
            return true;
        } catch (ConstraintViolationException e) {
            Logger.getLogger(SimpleGeolocationService.class.getName()).log(Level.SEVERE,
                    "SimpleGeolocationService: A ConstraintViolationException occurred in the storeGeotracerEvent method.");
            e.getConstraintViolations().forEach(err -> Logger.getLogger(SimpleGeolocationService.class.getName())
                    .log(Level.SEVERE, err.toString()));
            context.setRollbackOnly();
        } catch (ValidationException ve) {
            Logger.getLogger(SimpleGeolocationService.class.getName()).log(Level.SEVERE,
                    "SimpleGeolocationService: A ValidationException occurred in the storeGeotracerEvent method.",
                    ve);
            context.setRollbackOnly();
        } catch (Exception e) {
            Logger.getLogger(SimpleGeolocationService.class.getName()).log(Level.SEVERE,
                    "SimpleGeolocationService: An Exception occurred in the storeGeotracerEvent method: {0}",
                    e.getMessage());
            context.setRollbackOnly();
        }
        return false;
    }

    /**
     * This method finds unique geographic locations in the database by
     * filtering on IP address, latitude, and longitude.
     *
     * @param geolocationResult the geographic location data
     * @return the Geolocation entity or null if not found
     */
    private Geolocation findGeolocation(GeolocationResult geolocationResult) {

        Geolocation geolocation = null;
        try {
            geolocation = entityManager.createNamedQuery("Geolocation.findUniqueGeolocation",
                    Geolocation.class).setParameter("ipAddress", geolocationResult.getIpAddress())
                    .setParameter("latitude", geolocationResult.getLatitude())
                    .setParameter("longitude", geolocationResult.getLongitude()).getSingleResult();
        } catch (NoResultException nre) {
            Logger.getLogger(SimpleGeolocationService.class.getName()).log(Level.INFO,
                    "SimpleGeolocationService: No geolocation stored for IP address {0}.",
                    geolocationResult.getIpAddress());
        } catch (Exception e) {
            Logger.getLogger(SimpleGeolocationService.class.getName()).log(Level.SEVERE,
                    "SimpleGeolocationService: An exception occurred in the findGeolocation method.", e);
        }
        return geolocation;
    }

    /**
     * This method finds and returns the Region entity for the given region
     * code. If an entity for the given code does not exist, the method returns
     * null.
     *
     * @param regionCode the region code
     * @return the Region entity or null if the entity does not exist
     */
    private Region findRegion(String regionCode) {

        Region region = null;
        try {
            region = entityManager.createNamedQuery("Region.findByRegionCode",
                    Region.class).setParameter("regionCode", regionCode).getSingleResult();
        } catch (NoResultException nre) {
            Logger.getLogger(SimpleGeolocationService.class.getName()).log(Level.INFO,
                    "SimpleGeolocationService: No geolocation stored for region code {0}.",
                    regionCode);
        } catch (Exception e) {
            Logger.getLogger(SimpleGeolocationService.class.getName()).log(Level.SEVERE,
                    "SimpleGeolocationService: An exception occurred in the findRegion method.", e);
        }
        return region;
    }

    /**
     * This method finds and returns the Country entity for the given country
     * code. If an entity for the given code does not exist, the method returns
     * null.
     *
     * @param countryCode the country code
     * @return the Country entity or null if the entity does not exist
     */
    private Country findCountry(String countryCode) {

        Country country = null;
        try {
            country = entityManager.createNamedQuery("Country.findByCountryCode",
                    Country.class).setParameter("countryCode", countryCode).getSingleResult();
        } catch (NoResultException nre) {
            Logger.getLogger(SimpleGeolocationService.class.getName()).log(Level.INFO,
                    "SimpleGeolocationService: No geolocation stored for country code {0}.",
                    countryCode);
        } catch (Exception e) {
            Logger.getLogger(SimpleGeolocationService.class.getName()).log(Level.SEVERE,
                    "SimpleGeolocationService: An exception occurred in the findCountry method.", e);
        }
        return country;
    }

    /**
     * This method uses the given GeolocationResult to find an existing
     * Geolocation entity in the database or construct a new Geolocation entity
     * to be stored in the database.
     *
     * @param geolocationResult the GeolocationResult object
     * @return the new Geolocation entity
     */
    private Geolocation storeGeolocation(GeolocationResult geolocationResult) {

        Geolocation geolocation = findGeolocation(geolocationResult);
        if (geolocation == null) {

            MathContext latitudeContext = new MathContext(LATITUDE_PRECISION);
            BigDecimal latitudeValue = new BigDecimal(Float.toString(geolocationResult.getLatitude()),
                    latitudeContext);
            latitudeValue.setScale(COORDINATE_SCALE);

            MathContext longitudeContext = new MathContext(LONGITUDE_PRECISION);
            BigDecimal longitudeValue = new BigDecimal(Float.toString(geolocationResult.getLongitude()),
                    longitudeContext);
            longitudeValue.setScale(COORDINATE_SCALE);

            geolocation = new Geolocation();
            geolocation.setLocationId(null);
            geolocation.setIpAddress(geolocationResult.getIpAddress());
            geolocation.setLatitude(latitudeValue);
            geolocation.setLongitude(longitudeValue);
            geolocation.setCity(geolocationResult.getCity().equals("") ? null
                    : geolocationResult.getCity());
            geolocation.setRegion(storeRegion(geolocationResult));
            geolocation.setCountry(storeCountry(geolocationResult));
            geolocation.setZipCode(geolocationResult.getZipCode().equals("") ? null
                    : geolocationResult.getZipCode());
            geolocation.setTimeZone(geolocationResult.getTimeZone().equals("") ? null
                    : geolocationResult.getTimeZone());
            geolocation.setMetroCode(geolocationResult.getMetroCode() == 0 ? null
                    : geolocationResult.getMetroCode());
            entityManager.persist(geolocation);
            entityManager.flush();
        }
        return geolocation;
    }

    /**
     * This method saves a Region entity to the database if the entity does not
     * already exist. The new or existing entity is returned from the operation.
     *
     * @param geolocationResult the geographic location data
     * @return the Region entity
     */
    private Region storeRegion(GeolocationResult geolocationResult) {

        Region region = findRegion(geolocationResult.getRegionCode());
        if (region == null) {
            if (geolocationResult.getRegionCode().equals("")) {
                return null;
            } else {
                region = new Region();
                region.setRegionCode(geolocationResult.getRegionCode());
                region.setRegionName(geolocationResult.getRegionName());
                entityManager.persist(region);
            }
        }
        return region;
    }

    /**
     * This method saves a Country entity to the database if the entity does not
     * already exist. The new or existing entity is returned from the operation.
     *
     * @param geolocationResult the geographic location data
     * @return the Country entity
     */
    private Country storeCountry(GeolocationResult geolocationResult) {

        Country country = findCountry(geolocationResult.getCountryCode());
        if (country == null) {
            if (geolocationResult.getCountryCode().equals("")) {
                return null;
            } else {
                country = new Country();
                country.setCountryCode(geolocationResult.getCountryCode());
                country.setCountryName(geolocationResult.getCountryName());
                entityManager.persist(country);
            }
        }
        return country;
    }

    /**
     * This method takes the hop order and the tracer event to construct and
     * persist a new TracerHop entity in the database.
     *
     * @param hopOrder the hop order
     * @param geotracerEvent the tracer event
     * @return the new TracerHop entity
     */
    private TracerHop storeTracerHop(Integer hopOrder, GeotracerEvent geotracerEvent) {

        TracerHop tracerHop = new TracerHop(null, hopOrder);
        tracerHop.setTracer(geotracerEvent);
        entityManager.persist(tracerHop);
        return tracerHop;
    }

    /**
     * This method takes the TracerHop and GeolocationSearchEvent objects to
     * construct and return a new HopSearch entity.
     *
     * @param tracerHop the trace route hop
     * @param search the search associated with the given hop
     * @return the new HopSearch entity
     */
    private HopSearch createHopSearch(TracerHop tracerHop, GeolocationSearchEvent search) {

        HopSearch hopSearch = new HopSearch(tracerHop.getHopId());
        hopSearch.setTracerHop(tracerHop);
        hopSearch.setSearch(search);
        tracerHop.setHopSearch(hopSearch);
        return hopSearch;
    }
}
