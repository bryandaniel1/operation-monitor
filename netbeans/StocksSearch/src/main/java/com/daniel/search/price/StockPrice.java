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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Contains all data returned from a query for a particular stock symbol.
 *
 * @author Bryan Daniel
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "symbol",
    "name",
    "price",
    "currency",
    "price_open",
    "day_high",
    "day_low",
    "52_week_high",
    "52_week_low",
    "day_change",
    "change_pct",
    "close_yesterday",
    "market_cap",
    "volume",
    "volume_avg",
    "shares",
    "stock_exchange_long",
    "stock_exchange_short",
    "timezone",
    "timezone_name",
    "gmt_offset",
    "last_trade_time",
    "pe",
    "eps"
})
public class StockPrice {

    /**
     * The stock symbol
     */
    @JsonProperty("symbol")
    private String symbol;

    /**
     * The company name
     */
    @JsonProperty("name")
    private String name;

    /**
     * The stock price
     */
    @JsonProperty("price")
    private String price;

    /**
     * The currency
     */
    @JsonProperty("currency")
    private String currency;

    /**
     * The price on market open
     */
    @JsonProperty("price_open")
    private String priceOpen;

    /**
     * The high for the day
     */
    @JsonProperty("day_high")
    private String dayHigh;

    /**
     * The low for the day
     */
    @JsonProperty("day_low")
    private String dayLow;

    /**
     * The high for the last 52 weeks
     */
    @JsonProperty("52_week_high")
    private String fiftyTwoWeekHigh;

    /**
     * The low for the last 52 weeks
     */
    @JsonProperty("52_week_low")
    private String fiftyTwoWeekLow;

    /**
     * The price change for the day
     */
    @JsonProperty("day_change")
    private String dayChange;

    /**
     * The percent change for the day
     */
    @JsonProperty("change_pct")
    private String changePct;

    /**
     * The price at market close yesterday
     */
    @JsonProperty("close_yesterday")
    private String closeYesterday;

    /**
     * The market cap
     */
    @JsonProperty("market_cap")
    private String marketCap;

    /**
     * The volume
     */
    @JsonProperty("volume")
    private String volume;

    /**
     * The average volume
     */
    @JsonProperty("volume_avg")
    private String volumeAvg;

    /**
     * The number of shares
     */
    @JsonProperty("shares")
    private String shares;

    /**
     * The stock exchange long
     */
    @JsonProperty("stock_exchange_long")
    private String stockExchangeLong;

    /**
     * The stock exchange short
     */
    @JsonProperty("stock_exchange_short")
    private String stockExchangeShort;

    /**
     * The time zone
     */
    @JsonProperty("timezone")
    private String timezone;

    /**
     * The time zone name
     */
    @JsonProperty("timezone_name")
    private String timezoneName;

    /**
     * The Greenwich Mean Time offset
     */
    @JsonProperty("gmt_offset")
    private String gmtOffset;

    /**
     * The last trade time
     */
    @JsonProperty("last_trade_time")
    private String lastTradeTime;

    /**
     * The price to earnings ratio
     */
    @JsonProperty("pe")
    private String pe;

    /**
     * The earnings per share
     */
    @JsonProperty("eps")
    private String eps;

    /**
     * Gets the value of symbol.
     *
     * @return the value of symbol
     */
    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the value of symbol.
     *
     * @param symbol the symbol to set
     */
    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets the value of name.
     *
     * @return the value of name
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
     * Gets the value of price.
     *
     * @return the value of price
     */
    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    /**
     * Sets the value of price.
     *
     * @param price the price to set
     */
    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Gets the value of currency.
     *
     * @return the value of currency
     */
    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of currency.
     *
     * @param currency the currency to set
     */
    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets the value of priceOpen.
     *
     * @return the value of priceOpen
     */
    @JsonProperty("price_open")
    public String getPriceOpen() {
        return priceOpen;
    }

    /**
     * Sets the value of priceOpen.
     *
     * @param priceOpen the priceOpen to set
     */
    @JsonProperty("price_open")
    public void setPriceOpen(String priceOpen) {
        this.priceOpen = priceOpen;
    }

    /**
     * Gets the value of dayHigh.
     *
     * @return the value of dayHigh
     */
    @JsonProperty("day_high")
    public String getDayHigh() {
        return dayHigh;
    }

    /**
     * Sets the value of dayHigh.
     *
     * @param dayHigh the dayHigh to set
     */
    @JsonProperty("day_high")
    public void setDayHigh(String dayHigh) {
        this.dayHigh = dayHigh;
    }

    /**
     * Gets the value of dayLow.
     *
     * @return the value of dayLow
     */
    @JsonProperty("day_low")
    public String getDayLow() {
        return dayLow;
    }

    /**
     * Sets the value of dayLow.
     *
     * @param dayLow the dayLow to set
     */
    @JsonProperty("day_low")
    public void setDayLow(String dayLow) {
        this.dayLow = dayLow;
    }

    /**
     * Gets the value of fiftyTwoWeekHigh.
     *
     * @return the value of fiftyTwoWeekHigh
     */
    @JsonProperty("52_week_high")
    public String getFiftyTwoWeekHigh() {
        return fiftyTwoWeekHigh;
    }

    /**
     * Sets the value of fiftyTwoWeekHigh.
     *
     * @param fiftyTwoWeekHigh the fiftyTwoWeekHigh to set
     */
    @JsonProperty("52_week_high")
    public void setFiftyTwoWeekHigh(String fiftyTwoWeekHigh) {
        this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
    }

    /**
     * Gets the value of fiftyTwoWeekLow.
     *
     * @return the value of fiftyTwoWeekLow
     */
    @JsonProperty("52_week_low")
    public String getFiftyTwoWeekLow() {
        return fiftyTwoWeekLow;
    }

    /**
     * Sets the value of fiftyTwoWeekLow.
     *
     * @param fiftyTwoWeekLow the fiftyTwoWeekLow to set
     */
    @JsonProperty("52_week_low")
    public void setFiftyTwoWeekLow(String fiftyTwoWeekLow) {
        this.fiftyTwoWeekLow = fiftyTwoWeekLow;
    }

    /**
     * Gets the value of dayChange.
     *
     * @return the value of dayChange
     */
    @JsonProperty("day_change")
    public String getDayChange() {
        return dayChange;
    }

    /**
     * Sets the value of dayChange.
     *
     * @param dayChange the dayChange to set
     */
    @JsonProperty("day_change")
    public void setDayChange(String dayChange) {
        this.dayChange = dayChange;
    }

    /**
     * Gets the value of changePct.
     *
     * @return the value of changePct
     */
    @JsonProperty("change_pct")
    public String getChangePct() {
        return changePct;
    }

    /**
     * Sets the value of changePct.
     *
     * @param changePct the changePct to set
     */
    @JsonProperty("change_pct")
    public void setChangePct(String changePct) {
        this.changePct = changePct;
    }

    /**
     * Gets the value of closeYesterday.
     *
     * @return the value of closeYesterday
     */
    @JsonProperty("close_yesterday")
    public String getCloseYesterday() {
        return closeYesterday;
    }

    /**
     * Sets the value of closeYesterday.
     *
     * @param closeYesterday the closeYesterday to set
     */
    @JsonProperty("close_yesterday")
    public void setCloseYesterday(String closeYesterday) {
        this.closeYesterday = closeYesterday;
    }

    /**
     * Gets the value of marketCap.
     *
     * @return the value of marketCap
     */
    @JsonProperty("market_cap")
    public String getMarketCap() {
        return marketCap;
    }

    /**
     * Sets the value of marketCap.
     *
     * @param marketCap the marketCap to set
     */
    @JsonProperty("market_cap")
    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
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

    /**
     * Gets the value of volumeAvg.
     *
     * @return the value of volumeAvg
     */
    @JsonProperty("volume_avg")
    public String getVolumeAvg() {
        return volumeAvg;
    }

    /**
     * Sets the value of volumeAvg.
     *
     * @param volumeAvg the volumeAvg to set
     */
    @JsonProperty("volume_avg")
    public void setVolumeAvg(String volumeAvg) {
        this.volumeAvg = volumeAvg;
    }

    /**
     * Gets the value of shares.
     *
     * @return the value of shares
     */
    @JsonProperty("shares")
    public String getShares() {
        return shares;
    }

    /**
     * Sets the value of shares.
     *
     * @param shares the shares to set
     */
    @JsonProperty("shares")
    public void setShares(String shares) {
        this.shares = shares;
    }

    /**
     * Gets the value of stockExchangeLong.
     *
     * @return the value of stockExchangeLong
     */
    @JsonProperty("stock_exchange_long")
    public String getStockExchangeLong() {
        return stockExchangeLong;
    }

    /**
     * Sets the value of stockExchangeLong.
     *
     * @param stockExchangeLong the stockExchangeLong to set
     */
    @JsonProperty("stock_exchange_long")
    public void setStockExchangeLong(String stockExchangeLong) {
        this.stockExchangeLong = stockExchangeLong;
    }

    /**
     * Gets the value of stockExchangeShort.
     *
     * @return the value of stockExchangeShort
     */
    @JsonProperty("stock_exchange_short")
    public String getStockExchangeShort() {
        return stockExchangeShort;
    }

    /**
     * Sets the value of stockExchangeShort.
     *
     * @param stockExchangeShort the stockExchangeShort to set
     */
    @JsonProperty("stock_exchange_short")
    public void setStockExchangeShort(String stockExchangeShort) {
        this.stockExchangeShort = stockExchangeShort;
    }

    /**
     * Gets the value of timezone.
     *
     * @return the value of timezone
     */
    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    /**
     * Sets the value of timezone.
     *
     * @param timezone the timezone to set
     */
    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * Gets the value of timezoneName.
     *
     * @return the value of timezoneName
     */
    @JsonProperty("timezone_name")
    public String getTimezoneName() {
        return timezoneName;
    }

    /**
     * Sets the value of timezoneName.
     *
     * @param timezoneName the timezoneName to set
     */
    @JsonProperty("timezone_name")
    public void setTimezoneName(String timezoneName) {
        this.timezoneName = timezoneName;
    }

    /**
     * Gets the value of gmtOffset.
     *
     * @return the value of gmtOffset
     */
    @JsonProperty("gmt_offset")
    public String getGmtOffset() {
        return gmtOffset;
    }

    /**
     * Sets the value of gmtOffset.
     *
     * @param gmtOffset the gmtOffset to set
     */
    @JsonProperty("gmt_offset")
    public void setGmtOffset(String gmtOffset) {
        this.gmtOffset = gmtOffset;
    }

    /**
     * Gets the value of lastTradeTime.
     *
     * @return the value of lastTradeTime
     */
    @JsonProperty("last_trade_time")
    public String getLastTradeTime() {
        return lastTradeTime;
    }

    /**
     * Sets the value of lastTradeTime.
     *
     * @param lastTradeTime the lastTradeTime to set
     */
    @JsonProperty("last_trade_time")
    public void setLastTradeTime(String lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    /**
     * Gets the value of price to earnings ratio.
     *
     * @return the price to earnings ratio
     */
    @JsonProperty("pe")
    public String getPe() {
        return pe;
    }

    /**
     * Sets the value of price to earnings ratio.
     *
     * @param pe the price to earnings ratio to set
     */
    @JsonProperty("pe")
    public void setPe(String pe) {
        this.pe = pe;
    }

    /**
     * Gets the value of earnings per share.
     *
     * @return the earnings per share
     */
    @JsonProperty("eps")
    public String getEps() {
        return eps;
    }

    /**
     * Sets the value of earnings per share.
     *
     * @param eps the earnings per share to set
     */
    @JsonProperty("eps")
    public void setEps(String eps) {
        this.eps = eps;
    }
}
