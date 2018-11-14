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

import com.daniel.search.GeolocationSearchEventResult;
import com.daniel.search.GeotracerEventResult;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.text.MessageFormat;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This MessageListener implementation handles messages arriving in the
 * operation queue.
 *
 * @author Bryan Daniel
 */
@MessageDriven(mappedName = "OperationQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class OperationMessageListener implements MessageListener {

    /**
     * The geographic location service
     */
    @EJB
    private GeolocationService geolocationService;
    
    /**
     * The logger for this class
     */
    private final Logger logger = LogManager.getLogger(OperationMessageListener.class);

    /**
     * This method uses the geolocation service to convert messages from the
     * operation queue into entities to be stored in the database. If the
     * message is of an unknown type, the message is logged and the service is
     * not invoked.
     *
     * @param message the message
     */
    @Override
    public void onMessage(Message message) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);            
            String messageString = message.getBody(String.class);

            /*
             * Retrieving nodes from the JSON string to determine which type to 
             * accept
             */
            Object json = mapper.readValue(messageString, Object.class);
            JsonNode rootNode = mapper.readTree(messageString);
            JsonNode hopsNode = rootNode.path("hops");

            if (!hopsNode.isMissingNode()) {
                GeotracerEventResult geotracerEventResult = mapper.convertValue(json,
                        GeotracerEventResult.class);
                geolocationService.storeGeotracerEvent(geotracerEventResult);
            } else {
                JsonNode geolocationNode = rootNode.path("geolocation");
                if (!geolocationNode.isMissingNode()) {
                    logger.info(MessageFormat.format("Message received:\n{0}", mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(json)));
                    GeolocationSearchEventResult geolocationSearchEventResult
                            = mapper.convertValue(json, GeolocationSearchEventResult.class);
                    geolocationService.storeGeolocationSearchEvent(geolocationSearchEventResult);
                } else {
                    logger.info(MessageFormat.format("Unknown message received from the queue:\n{0}",
                            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json)));
                }
            }
        } catch (JMSException | JsonProcessingException ex) {
            logger.error("An exception occurred in onMessage method.", ex);
        } catch (IOException ex) {
            logger.error("An IOException occurred in onMessage method.", ex);
        }
    }
}
