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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Map;

/**
 * This class represents the result of a stock history web service call,
 * containing all the history objects returned from query.
 *
 * @author Bryan Daniel
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "history"
})
public class HistoricalStockPriceSearchResult {

    /**
     * The stock symbol
     */
    @JsonProperty("name")
    private String name;

    /**
     * The map of historical dates and details
     */
    @JsonProperty("history")
    private Map<String, HistoricalStockPrice> history;

    /**
     * Gets the value of name.
     *
     * @return the name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Sets the value of name.
     *
     * @param name the name to set
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the value of history.
     *
     * @return the history
     */
    @JsonProperty("history")
    public Map<String, HistoricalStockPrice> getHistory() {
        return history;
    }

    /**
     * Sets the value of history.
     *
     * @param history the history to set
     */
    @JsonProperty("history")
    public void setHistory(Map<String, HistoricalStockPrice> history) {
        this.history = history;
    }
}
