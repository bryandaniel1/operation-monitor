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
package com.daniel.search.task;

import com.daniel.search.client.StocksInquisitor;
import com.daniel.search.price.StockPriceSearchEvent;
import java.util.concurrent.Callable;

/**
 * This class performs a stock price search task which passes a stock symbol and
 * API token to the StocksInquisitor search function and returns the results.
 *
 * @author Bryan Daniel
 */
public class StockPriceSearchTask implements Callable<StockPriceSearchEvent> {

    /**
     * The stock symbol used for the task
     */
    private String stockSymbol;

    /**
     * The API token necessary for this task
     */
    private String apiToken;

    /**
     * This default constructor sets stockSymbol to null.
     */
    public StockPriceSearchTask() {
        this.stockSymbol = null;
    }

    /**
     * This parameterized constructor sets the value of stockSymbol.
     *
     * @param stockSymbol the stock symbol value
     * @param apiToken the API token
     */
    public StockPriceSearchTask(String stockSymbol, String apiToken) {
        this.stockSymbol = stockSymbol;
        this.apiToken = apiToken;
    }

    @Override
    public StockPriceSearchEvent call() throws Exception {
        StocksInquisitor inquisitor = StocksInquisitor.getInstance();
        return inquisitor.findStockPrice(stockSymbol, apiToken);
    }

    /**
     * Sets the value of stockSymbol
     *
     * @param stockSymbol the new value of stockSymbol
     */
    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    /**
     * Sets the value of apiToken
     *
     * @param apiToken the API token
     */
    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }
}
