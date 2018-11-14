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
package com.daniel.geolocator.ejb;

import com.daniel.search.GeolocationSearchEventResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.enterprise.event.Observes;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This class observes geographic location search events and produces a message
 * for each event. The messaging in this class is configured for a remote queue.
 *
 * @author Bryan Daniel
 */
@Stateless
@LocalBean
public class EventHandler {

    /**
     * The value for the initial context factory
     */
    public static final String INITIAL_CONTEXT_FACTORY_VALUE = "com.sun.enterprise.naming.SerialInitContextFactory";

    /**
     * The value for URL package prefixes
     */
    public static final String URL_PKG_PREFIXES_VALUE = "sun.enterprise.naming";

    /**
     * The value for the provider URL
     */
    public static final String PROVIDER_URL_VALUE = "iiop://localhost:3700";

    /**
     * The Java Message Service context
     */
    public static final String CONNECTION_FACTORY_NAME = "OperationQueueConnectionFactory";

    /**
     * The message queue name
     */
    public static final String QUEUE_NAME = "OperationQueue";

    /**
     * The logger for this class
     */
    private final Logger logger = LogManager.getLogger(EventHandler.class);

    /**
     * This method handles geographic location search events by producing a
     * message containing the event data and sending it to the operation queue.
     *
     * @param searchEvent the GeolocationSearchEventResult object
     */
    @Asynchronous
    public void produceSearchEventMessage(@Observes GeolocationSearchEventResult searchEvent) {

        try {
            Context context = createInitialContext();
            JMSContext jmsContext = ((ConnectionFactory) context.lookup(CONNECTION_FACTORY_NAME)).createContext();
            Queue queue = (Queue) context.lookup(QUEUE_NAME);
            jmsContext.createProducer().send(queue, new ObjectMapper().writeValueAsString(searchEvent));
        } catch (JsonProcessingException | JMSException | NamingException ex) {
            logger.error("An exception occurred in produceSearchEventMessage method.", ex);
        }
    }

    /**
     * This method creates the properties for the Java Message Service and
     * returns the context created with these properties.
     *
     * @return the InitialContext object
     * @throws JMSException
     * @throws NamingException
     */
    public Context createInitialContext() throws JMSException, NamingException {

        Properties properties = new Properties();
        properties.setProperty(InitialContext.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY_VALUE);
        properties.setProperty(InitialContext.URL_PKG_PREFIXES, URL_PKG_PREFIXES_VALUE);
        properties.setProperty(InitialContext.PROVIDER_URL, PROVIDER_URL_VALUE);
        return new InitialContext(properties);
    }
}
