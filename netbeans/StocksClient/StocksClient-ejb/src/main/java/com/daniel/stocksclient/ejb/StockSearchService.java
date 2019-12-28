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

import com.daniel.search.history.HistoricalStockPriceSearchResult;
import com.daniel.search.price.StockPriceSearchResult;
import javax.ejb.Local;

/**
 * Provides the ability to request stock market information for a given stock
 * symbol.
 *
 * @author Bryan Daniel
 */
@Local
public interface StockSearchService {

    /**
     * This method passes the given stock symbol to the stocks web service to
     * return the price and associated stock information.
     *
     * @param stockSymbol the stock symbol
     * @return the stock price result or null if an exception occurs
     */
    public StockPriceSearchResult findStockPrice(String stockSymbol);

    /**
     * This method passes the given stock symbol to the stocks web service to
     * return the historical stock data.
     *
     * @param stockSymbol the stock symbol
     * @return the stock history result or null if an exception occurs
     */
    public HistoricalStockPriceSearchResult findStockHistory(String stockSymbol);
}
