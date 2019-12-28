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
package com.daniel.search.price;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This class represents the result of a stock price web service call,
 * containing all the stockPriceData returned from query.
 *
 * @author Bryan Daniel
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "symbols_requested",
    "symbols_returned",
    "data"
})
public class StockPriceSearchResult {

    /**
     * The symbols requested
     */
    @JsonProperty("symbols_requested")
    private Integer symbolsRequested;

    /**
     * The symbols returned
     */
    @JsonProperty("symbols_returned")
    private Integer symbolsReturned;

    /**
     * The stock price stockPriceData
     */
    @JsonProperty("data")
    private List<StockPrice> stockPriceData = null;

    /**
     * Gets the value of symbolsRequested.
     *
     * @return the value of symbolsRequested
     */
    @JsonProperty("symbols_requested")
    public Integer getSymbolsRequested() {
        return symbolsRequested;
    }

    /**
     * Sets the value of symbolsRequested.
     *
     * @param symbolsRequested the symbolsRequested to set
     */
    @JsonProperty("symbols_requested")
    public void setSymbolsRequested(Integer symbolsRequested) {
        this.symbolsRequested = symbolsRequested;
    }

    /**
     * Gets the value of symbolsReturned.
     *
     * @return the value of symbolsReturned
     */
    @JsonProperty("symbols_returned")
    public Integer getSymbolsReturned() {
        return symbolsReturned;
    }

    /**
     * Sets the value of symbolsReturned.
     *
     * @param symbolsReturned the symbolsReturned to set
     */
    @JsonProperty("symbols_returned")
    public void setSymbolsReturned(Integer symbolsReturned) {
        this.symbolsReturned = symbolsReturned;
    }

    /**
     * Gets the value of stockPriceData.
     *
     * @return the value of stockPriceData
     */
    @JsonProperty("data")
    public List<StockPrice> getStockPriceData() {
        return stockPriceData;
    }

    /**
     * Sets the value of stockPriceData.
     *
     * @param stockPriceData the stockPriceData to set
     */
    @JsonProperty("data")
    public void setStockPriceData(List<StockPrice> stockPriceData) {
        this.stockPriceData = stockPriceData;
    }
}
