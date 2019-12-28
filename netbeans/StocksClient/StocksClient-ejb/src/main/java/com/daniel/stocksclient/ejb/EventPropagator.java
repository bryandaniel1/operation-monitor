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
package com.daniel.stocksclient.ejb;

import com.daniel.search.history.HistoricalStockPriceSearchEvent;
import com.daniel.search.price.StockPriceSearchEvent;
import java.text.MessageFormat;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This class fires search events to relay information concerning executed tasks
 * within the stocks client.
 *
 * @author Bryan Daniel
 */
@Stateless
@LocalBean
public class EventPropagator {

    /**
     * The stock price search event
     */
    @Inject
    private Event<StockPriceSearchEvent> priceSearchEvent;

    /**
     * The stock history search event
     */
    @Inject
    private Event<HistoricalStockPriceSearchEvent> historySearchEvent;

    /**
     * The logger for this class
     */
    private final Logger logger = LogManager.getLogger(EventPropagator.class);

    /**
     * This method fires the stock price search event to be handled by whatever
     * listeners are waiting for it. The method is executed asynchronously in
     * case the observer methods handling the event are long-running.
     *
     * @param stockPriceSearchEvent the stock price search event
     */
    @Asynchronous
    public void fireSearchEvent(StockPriceSearchEvent stockPriceSearchEvent) {

        priceSearchEvent.fire(stockPriceSearchEvent);
        logger.info(MessageFormat.format("A stock price search event was fired for event timed: {0}",
                stockPriceSearchEvent.getTimeSearched()));
    }

    /**
     * This method fires the stock history search event to be handled by
     * whatever listeners are waiting for it. The method is executed
     * asynchronously in case the observer methods handling the event are
     * long-running.
     *
     * @param historicalStockPriceSearchEvent the stock price search event
     */
    @Asynchronous
    public void fireSearchEvent(HistoricalStockPriceSearchEvent historicalStockPriceSearchEvent) {

        historySearchEvent.fire(historicalStockPriceSearchEvent);
        logger.info(MessageFormat.format("A stock history search event was fired for event timed: {0}",
                historicalStockPriceSearchEvent.getTimeSearched()));
    }
}
