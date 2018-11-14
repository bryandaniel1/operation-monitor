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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This class observes geographic tracer events and produces a message
 * containing the information for each event. The messaging in this class is
 * configured for a local queue.
 *
 * @author Bryan Daniel
 */
@Stateless
@LocalBean
public class GeotracerMessageProducer {

    /**
     * The Java Message Service context
     */
    @Inject
    private JMSContext jmsContext;

    /**
     * The destination queue for the messages produced
     */
    @Resource(mappedName = "OperationQueue")
    private Queue queue;

    /**
     * The logger for this class
     */
    private final Logger logger = LogManager.getLogger(GeotracerMessageProducer.class);

    /**
     * This method listens handles the firing of a tracer event by sending the
     * event data as messages to the queue.
     *
     * @param geotracerEvent the GeotracerEventResult object
     */
    @Asynchronous
    public void produceGeotracerEventMessage(@Observes GeotracerEventResult geotracerEvent) {
        try {
            jmsContext.createProducer().send(queue, new ObjectMapper().writeValueAsString(geotracerEvent));
        } catch (JsonProcessingException ex) {
            logger.error("A JsonProcessingException occurred in the toJson method.", ex);
        }
    }
}
