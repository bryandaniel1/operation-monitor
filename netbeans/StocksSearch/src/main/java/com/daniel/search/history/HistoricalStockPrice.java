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

/**
 * Contains data for a particular date returned from a stock history query.
 *
 * @author Bryan Daniel
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "open",
    "close",
    "high",
    "low",
    "volume"
})
public class HistoricalStockPrice {

    /**
     * The price on market open
     */
    @JsonProperty("open")
    private String open;

    /**
     * The price on market close
     */
    @JsonProperty("close")
    private String close;

    /**
     * The high for the day
     */
    @JsonProperty("high")
    private String high;

    /**
     * The low for the day
     */
    @JsonProperty("low")
    private String low;

    /**
     * The volume
     */
    @JsonProperty("volume")
    private String volume;

    /**
     * Gets the value of open.
     *
     * @return the value of open
     */
    @JsonProperty("open")
    public String getOpen() {
        return open;
    }

    /**
     * Sets the value of open.
     *
     * @param open the open to set
     */
    @JsonProperty("open")
    public void setOpen(String open) {
        this.open = open;
    }

    /**
     * Gets the value of close.
     *
     * @return the value of close
     */
    @JsonProperty("close")
    public String getClose() {
        return close;
    }

    /**
     * Sets the value of close.
     *
     * @param close the close to set
     */
    @JsonProperty("close")
    public void setClose(String close) {
        this.close = close;
    }

    /**
     * Gets the value of high.
     *
     * @return the value of high
     */
    @JsonProperty("high")
    public String getHigh() {
        return high;
    }

    /**
     * Sets the value of high.
     *
     * @param high the high to set
     */
    @JsonProperty("high")
    public void setHigh(String high) {
        this.high = high;
    }

    /**
     * Gets the value of low.
     *
     * @return the value of low
     */
    @JsonProperty("low")
    public String getLow() {
        return low;
    }

    /**
     * Sets the value of low.
     *
     * @param low the low to set
     */
    @JsonProperty("low")
    public void setLow(String low) {
        this.low = low;
    }

    /**
     * Gets the value of volume.
     *
     * @return the value of volume
     */
    @JsonProperty("volume")
    public String getVolume() {
        return volume;
    }

    /**
     * Sets the value of volume.
     *
     * @param volume the volume to set
     */
    @JsonProperty("volume")
    public void setVolume(String volume) {
        this.volume = volume;
    }
}
