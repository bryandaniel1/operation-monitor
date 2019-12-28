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
package com.daniel.search.client;

import com.daniel.search.history.HistoricalStockPriceSearchEvent;
import com.daniel.search.history.HistoricalStockPriceSearchResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.daniel.search.price.StockPriceSearchEvent;
import com.daniel.search.price.StockPriceSearchResult;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 * This class contains the functionality to search for and return stock market
 * data. Only one stocks inquisitor is produced to send requests.
 *
 * @author Bryan Daniel
 */
public class StocksInquisitor {

    /**
     * The URL of the web service returning stock information.
     */
    public static final String SERVICE_URL = "https://api.worldtradingdata.com/api/v1/";

    /**
     * The path for a stock price query.
     */
    public static final String PRICE_QUERY = "stock";

    /**
     * The path for a stock history query.
     */
    public static final String HISTORY_QUERY = "history";

    /**
     * The parameter name for the stock symbol
     */
    public static final String SYMBOL = "symbol";

    /**
     * The parameter name for the start date
     */
    public static final String DATE_FROM = "date_from";

    /**
     * The parameter name for the API token
     */
    public static final String API_TOKEN = "api_token";

    /**
     * The StocksInquisitor instance
     */
    private static StocksInquisitor stocksInquisitor;

    /*
     * Private constructor - not called from outside this class.
     */
    private StocksInquisitor() {
    }

    /**
     * Returns the single instance of StocksInquisitor. If it has not yet been
     * instantiated, it is instantiated here.
     *
     * @return the instance of StocksInquisitor
     */
    public static synchronized StocksInquisitor getInstance() {
        if (stocksInquisitor == null) {
            stocksInquisitor = new StocksInquisitor();
        }
        return stocksInquisitor;
    }

    /**
     * This method passes the given stock symbol and API token to a web service
     * to return the stock price and associated stock market data wrapped in a
     * StockPriceSearchEvent object with the search time.
     *
     * @param stockSymbol the stock symbol
     * @param apiToken the API token
     * @return the StockPriceSearchEvent object
     * @throws java.io.IOException if an I/O operation fails
     * @throws ProcessingException in case the request processing or subsequent
     * I/O operation fails.
     * @throws WebApplicationException in case the response status code of the
     * response returned by the server is not successful and the specified
     * generic response type does not represent Response
     * @throws NullPointerException in case the stock symbol value is null
     */
    public StockPriceSearchEvent findStockPrice(String stockSymbol, String apiToken) throws IOException {

        validateInput(stockSymbol);

        long timeStarted = System.currentTimeMillis();
        LocalDateTime timeSearched = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStarted),
                TimeZone.getDefault().toZoneId());

        Client client = ClientBuilder.newClient();
        String stockPriceString = client.target(SERVICE_URL)
                .path(PRICE_QUERY)
                .queryParam(SYMBOL, stockSymbol)
                .queryParam(API_TOKEN, apiToken)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        StockPriceSearchEvent stockPriceSearchEvent = new StockPriceSearchEvent();
        StockPriceSearchResult stockPriceSearchResult = mapper.readValue(stockPriceString,
                StockPriceSearchResult.class);
        stockPriceSearchEvent.setTimeSearched(timeSearched);
        stockPriceSearchEvent.setTimeElapsed(System.currentTimeMillis() - timeStarted);
        stockPriceSearchEvent.setStockPriceSearchResult(stockPriceSearchResult);

        return stockPriceSearchEvent;
    }

    /**
     * This method passes the given stock symbol and API token to a web service
     * to return the stock price history wrapped in a
     * HistoricalStockPriceSearchEvent object with the search time.
     *
     * @param stockSymbol the stock symbol
     * @param apiToken the API token
     * @param dateFrom the start date for the history retrieval
     * @return the HistoricalStockPriceSearchEvent object
     * @throws java.io.IOException if an I/O operation fails
     * @throws ProcessingException in case the request processing or subsequent
     * I/O operation fails.
     * @throws WebApplicationException in case the response status code of the
     * response returned by the server is not successful and the specified
     * generic response type does not represent Response
     * @throws NullPointerException in case the stock symbol value is null
     */
    public HistoricalStockPriceSearchEvent findStockPriceHistory(String stockSymbol, String apiToken, LocalDate dateFrom) throws IOException {

        validateInput(stockSymbol);

        long timeStarted = System.currentTimeMillis();
        LocalDateTime timeSearched = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStarted),
                TimeZone.getDefault().toZoneId());

        Client client = ClientBuilder.newClient();
        String stockPriceString = client.target(SERVICE_URL)
                .path(HISTORY_QUERY)
                .queryParam(SYMBOL, stockSymbol)
                .queryParam(DATE_FROM, dateFrom.toString())
                .queryParam(API_TOKEN, apiToken)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        ObjectMapper mapper = new ObjectMapper();
        HistoricalStockPriceSearchEvent historicalStockPriceSearchEvent = new HistoricalStockPriceSearchEvent();
        HistoricalStockPriceSearchResult historicalStockPriceSearchResult = mapper.readValue(stockPriceString,
                HistoricalStockPriceSearchResult.class);
        historicalStockPriceSearchEvent.setTimeSearched(timeSearched);
        historicalStockPriceSearchEvent.setTimeElapsed(System.currentTimeMillis() - timeStarted);
        historicalStockPriceSearchEvent.setHistoricalStockPriceSearchResult(historicalStockPriceSearchResult);

        return historicalStockPriceSearchEvent;
    }

    /**
     * Validates the presence of a stock symbol.
     *
     * @param stockSymbol the stock symbol to validate
     */
    private void validateInput(String stockSymbol) {
        if (stockSymbol == null) {
            throw new NullPointerException("The stock symbol was null.");
        }
    }
}
