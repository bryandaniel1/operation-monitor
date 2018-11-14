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

import com.daniel.search.GeotracerEventResult;
import java.text.MessageFormat;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This class fires tracer events to relay information concerning executed
 * traceroute functions.
 *
 * @author Bryan Daniel
 */
@Stateless
@LocalBean
public class GeotracerEventRelay {

    /**
     * The geotracer event
     */
    @Inject
    private Event<GeotracerEventResult> tracerEvent;

    /**
     * The logger for this class
     */
    private final Logger logger = LogManager.getLogger(GeotracerEventRelay.class);

    /**
     * This method fires the tracer event to be handled by the listener(s) for
     * the GeotracerEventResult. The method is executed asynchronously in case
     * the observer methods handling the event are long-running.
     *
     * @param geotracerEvent the tracer event
     */
    @Asynchronous
    public void fireSearchEvent(GeotracerEventResult geotracerEvent) {

        tracerEvent.fire(geotracerEvent);
        logger.info(MessageFormat.format("A geographic tracer event was fired for execution timed: {0}",
                geotracerEvent.getTimeExecuted()));
    }
}
