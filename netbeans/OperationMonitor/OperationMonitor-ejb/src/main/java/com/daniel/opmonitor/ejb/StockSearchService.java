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

import com.daniel.opmonitor.entity.StockPriceSearch;
import com.daniel.opmonitor.entity.StockHistorySearch;
import com.daniel.search.history.HistoricalStockPriceSearchEvent;
import com.daniel.search.price.StockPriceSearchEvent;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * This service provides methods for stock search data operations.
 *
 * @author Bryan Daniel
 */
@Local
public interface StockSearchService {

    /**
     * This method retrieves the list of stock price search events occurring on
     * the given date.
     *
     * @param dateOccurred the date of the search event
     * @return the list of all StockPriceSearch entities
     */
    public List<StockPriceSearch> findStockPriceSearchEvents(Date dateOccurred);

    /**
     * This method retrieves the list of stock history events occurring on the
     * given date.
     *
     * @param dateOccurred the date of the history search event
     * @return the list of all StockHistorySearch entities
     */
    public List<StockHistorySearch> findStockHistorySearchEvents(Date dateOccurred);

    /**
     * This method saves data for the given StockPriceSearchEvent object to the
     * database.
     *
     * @param stockPriceSearchEvent the StockPriceSearchEvent object
     * @return the indication of operation success or failure
     */
    public boolean storeStockPriceSearchEvent(StockPriceSearchEvent stockPriceSearchEvent);

    /**
     * This method saves data for the given HistoricalStockPriceSearchEvent
     * object to the database.
     *
     * @param historicalStockPriceSearchEvent the
     * HistoricalStockPriceSearchEvent object
     * @return the indication of operation success or failure
     */
    public boolean storeHistoricalStockPriceSearchEvent(HistoricalStockPriceSearchEvent historicalStockPriceSearchEvent);
}
