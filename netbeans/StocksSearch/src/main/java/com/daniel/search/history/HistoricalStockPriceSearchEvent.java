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
package com.daniel.search.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.time.LocalDateTime;

/**
 * This class represents the stock price history search event. This event holds
 * the time of the search, the time taken to complete the search, and all data
 * returned from the search.
 *
 * @author Bryan Daniel
 */
@JsonRootName(value = "stock_history_search_event")
public class HistoricalStockPriceSearchEvent {

    /**
     * The time the stock price was queried
     */
    @JsonProperty("time_searched")
    private LocalDateTime timeSearched;

    /**
     * The time taken to complete the search
     */
    @JsonProperty("time_elapsed")
    private long timeElapsed;

    /**
     * The stock price history result
     */
    @JsonProperty("stock_history_search_result")
    private HistoricalStockPriceSearchResult historicalStockPriceSearchResult;

    /**
     * Get the value of timeSearched
     *
     * @return the value of timeSearched
     */
    public LocalDateTime getTimeSearched() {
        return timeSearched;
    }

    /**
     * Set the value of timeSearched
     *
     * @param timeSearched new value of timeSearched
     */
    public void setTimeSearched(LocalDateTime timeSearched) {
        this.timeSearched = timeSearched;
    }

    /**
     * Get the value of timeElapsed
     *
     * @return the value of timeElapsed
     */
    public long getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * Set the value of timeElapsed
     *
     * @param timeElapsed new value of timeElapsed
     */
    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    /**
     * Get the value of historicalStockPriceSearchResult
     *
     * @return the value of historicalStockPriceSearchResult
     */
    public HistoricalStockPriceSearchResult getHistoricalStockPriceSearchResult() {
        return historicalStockPriceSearchResult;
    }

    /**
     * Set the value of historicalStockPriceSearchResult
     *
     * @param historicalStockPriceSearchResult new value of
     * historicalStockPriceSearchResult
     */
    public void setHistoricalStockPriceSearchResult(HistoricalStockPriceSearchResult historicalStockPriceSearchResult) {
        this.historicalStockPriceSearchResult = historicalStockPriceSearchResult;
    }
}
