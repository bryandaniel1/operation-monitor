/* 
 * Copyright 2019 Bryan Daniel.
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

import com.daniel.search.history.HistoricalStockPriceSearchEvent;
import com.daniel.search.price.StockPriceSearchEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
     * The stock search data service
     */
    @EJB
    private StockSearchService stockSearchService;

    /**
     * The logger for this class
     */
    private final Logger logger = LogManager.getLogger(OperationMessageListener.class);

    /**
     * The name of the stock price history node within the message
     */
    private final String HISTORY_NODE = "stock_history_search_result";

    /**
     * The name of the stock price search node within the message
     */
    private final String PRICE_NODE = "stock_price_search_result";

    /**
     * This method uses the stock search service to convert messages from the
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
            mapper.registerModule(new JavaTimeModule());
            String messageString = message.getBody(String.class);

            /*
             * Retrieving nodes from the JSON string to determine which type to 
             * accept
             */
            JsonNode rootNode = mapper.readTree(messageString);
            JsonNode historyNode = rootNode.path(HISTORY_NODE);

            if (!historyNode.isMissingNode()) {
                HistoricalStockPriceSearchEvent historicalStockPriceSearchEvent = mapper.readValue(messageString,
                        HistoricalStockPriceSearchEvent.class);
                stockSearchService.storeHistoricalStockPriceSearchEvent(historicalStockPriceSearchEvent);
            } else {
                JsonNode dataNode = rootNode.path(PRICE_NODE);
                if (!dataNode.isMissingNode()) {
                    StockPriceSearchEvent stockPriceSearchEvent = mapper.readValue(messageString, StockPriceSearchEvent.class);
                    stockSearchService.storeStockPriceSearchEvent(stockPriceSearchEvent);
                } else {
                    logger.info(MessageFormat.format("Unknown message received from the queue:\n{0}",
                            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageString)));
                }
            }
        } catch (JMSException | JsonProcessingException ex) {
            logger.error("An exception occurred in onMessage method.", ex);
        } catch (IOException ex) {
            logger.error("An IOException occurred in onMessage method.", ex);
        }
    }
}
