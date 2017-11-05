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

import com.daniel.opmonitor.entity.GeolocationSearchEvent;
import com.daniel.opmonitor.entity.GeotracerEvent;
import com.daniel.search.GeolocationSearchEventResult;
import com.daniel.search.GeotracerEventResult;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * This service provides methods for geographic location data operations.
 *
 * @author Bryan Daniel
 */
@Local
public interface GeolocationService {

    /**
     * This method retrieves the list of geographic location search events
     * occurring on the given date.
     *
     * @param dateOccurred the date of the search event
     * @return the list of all GeolocationSearchEvent entities
     */
    public List<GeolocationSearchEvent> findGeolocationSearchEvents(Date dateOccurred);

    /**
     * This method retrieves the list of tracer events occurring on the given
     * date.
     *
     * @param dateOccurred the date of the tracer event
     * @return the list of all GeotracerEvent entities
     */
    public List<GeotracerEvent> findGeotracerEvents(Date dateOccurred);

    /**
     * This method saves data for the given GeolocationSearchEventResult object
     * to the database.
     *
     * @param geolocationSearchEventResult the GeolocationSearchEventResult
     * object
     * @return the indication of operation success or failure
     */
    public boolean storeGeolocationSearchEvent(GeolocationSearchEventResult geolocationSearchEventResult);

    /**
     * This method saves data for the given GeotracerEventResult object to the
     * database.
     *
     * @param geotracerEventResult the GeotracerEventResult object
     * @return the indication of operation success or failure
     */
    public boolean storeGeotracerEvent(GeotracerEventResult geotracerEventResult);
}
