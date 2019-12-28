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

import com.daniel.search.price.StockPriceSearchResult;
import com.daniel.search.price.StockPriceSearchEvent;
import com.daniel.search.client.StocksInquisitor;
import com.daniel.search.history.HistoricalStockPriceSearchEvent;
import com.daniel.search.history.HistoricalStockPriceSearchResult;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This StockSearchService implementation contains the functionality for
 * retrieving stock price data.
 *
 * @author Bryan Daniel
 */
@Stateless(name = "stockSearchService")
public class StockSearchServiceImpl extends StockSearchTemplate implements StockSearchService {

    /**
     * The logger for this class
     */
    private final Logger logger = LogManager.getLogger(StockSearchTemplate.class);

    /**
     * The event propagator
     */
    @EJB
    private EventPropagator eventPropagator;

    @Override
    public StockPriceSearchResult findStockPrice(String stockSymbol) {

        String apiToken = getApiToken();
        StockPriceSearchEvent stockPriceSearchEvent = null;
        try {
            StocksInquisitor inquisitor = StocksInquisitor.getInstance();
            stockPriceSearchEvent = inquisitor.findStockPrice(stockSymbol, apiToken);
            eventPropagator.fireSearchEvent(stockPriceSearchEvent);
            logger.info(MessageFormat.format("A stock price search was successfully performed for stock symbol: {0}",
                    stockSymbol));
        } catch (ProcessingException | WebApplicationException | NullPointerException | IOException e) {
            logException(e, stockSymbol, logger);
        }
        return stockPriceSearchEvent != null ? stockPriceSearchEvent.getStockPriceSearchResult() : null;
    }

    @Override
    public HistoricalStockPriceSearchResult findStockHistory(String stockSymbol) {
        
        String apiToken = getApiToken();
        HistoricalStockPriceSearchEvent historicalStockPriceSearchEvent = null;
        try {
            StocksInquisitor inquisitor = StocksInquisitor.getInstance();
            LocalDate dateFrom = LocalDate.now().minusYears(1);
            historicalStockPriceSearchEvent = inquisitor.findStockPriceHistory(stockSymbol, apiToken, dateFrom);
            eventPropagator.fireSearchEvent(historicalStockPriceSearchEvent);
            logger.info(MessageFormat.format("A stock history search was successfully performed for stock symbol: {0}",
                    stockSymbol));
        } catch (ProcessingException | WebApplicationException | NullPointerException | IOException e) {
            logException(e, stockSymbol, logger);
        }
        return historicalStockPriceSearchEvent != null
                ? historicalStockPriceSearchEvent.getHistoricalStockPriceSearchResult()
                : null;
    }
}
