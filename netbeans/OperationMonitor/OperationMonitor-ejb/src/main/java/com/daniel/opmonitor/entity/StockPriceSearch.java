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
package com.daniel.opmonitor.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This entity class represents a stock price search.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "StockPriceSearch")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StockPriceSearch.findAll", query = "SELECT s FROM StockPriceSearch s")
    , @NamedQuery(name = "StockPriceSearch.findByStockPriceId", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.stockPriceId = :stockPriceId")
    , @NamedQuery(name = "StockPriceSearch.findByCurrency", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.currency = :currency")
    , @NamedQuery(name = "StockPriceSearch.findByPrice", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.price = :price")
    , @NamedQuery(name = "StockPriceSearch.findByPriceOpen", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.priceOpen = :priceOpen")
    , @NamedQuery(name = "StockPriceSearch.findByDayHigh", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.dayHigh = :dayHigh")
    , @NamedQuery(name = "StockPriceSearch.findByDayLow", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.dayLow = :dayLow")
    , @NamedQuery(name = "StockPriceSearch.findByWeekHigh", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.weekHigh = :weekHigh")
    , @NamedQuery(name = "StockPriceSearch.findByWeekLow", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.weekLow = :weekLow")
    , @NamedQuery(name = "StockPriceSearch.findByDayChange", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.dayChange = :dayChange")
    , @NamedQuery(name = "StockPriceSearch.findByChangePct", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.changePct = :changePct")
    , @NamedQuery(name = "StockPriceSearch.findByCloseYesterday", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.closeYesterday = :closeYesterday")
    , @NamedQuery(name = "StockPriceSearch.findByMarketCap", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.marketCap = :marketCap")
    , @NamedQuery(name = "StockPriceSearch.findByVolume", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.volume = :volume")
    , @NamedQuery(name = "StockPriceSearch.findByVolumeAvg", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.volumeAvg = :volumeAvg")
    , @NamedQuery(name = "StockPriceSearch.findByShares", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.shares = :shares")
    , @NamedQuery(name = "StockPriceSearch.findByStockExchangeLong", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.stockExchangeLong = :stockExchangeLong")
    , @NamedQuery(name = "StockPriceSearch.findByStockExchangeShort", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.stockExchangeShort = :stockExchangeShort")
    , @NamedQuery(name = "StockPriceSearch.findByTimezone", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.timezone = :timezone")
    , @NamedQuery(name = "StockPriceSearch.findByTimezoneName", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.timezoneName = :timezoneName")
    , @NamedQuery(name = "StockPriceSearch.findByGmtOffset", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.gmtOffset = :gmtOffset")
    , @NamedQuery(name = "StockPriceSearch.findByLastTradeTime", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.lastTradeTime = :lastTradeTime")
    , @NamedQuery(name = "StockPriceSearch.findByPe", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.pe = :pe")
    , @NamedQuery(name = "StockPriceSearch.findByEps", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.eps = :eps")
    , @NamedQuery(name = "StockPriceSearch.findBySearchDateTime", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.searchDateTime >= :searchDateTime AND s.searchDateTime < :dayAfter ORDER BY s.searchDateTime")
    , @NamedQuery(name = "StockPriceSearch.findByTimeElapsed", query = "SELECT s FROM StockPriceSearch s "
            + "WHERE s.timeElapsed = :timeElapsed")})
public class StockPriceSearch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "stock_price_id")
    private Long stockPriceId;
    @Size(max = 3)
    @Column(name = "currency")
    private String currency;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "price_open")
    private BigDecimal priceOpen;
    @Column(name = "day_high")
    private BigDecimal dayHigh;
    @Column(name = "day_low")
    private BigDecimal dayLow;
    @Column(name = "52_week_high")
    private BigDecimal weekHigh;
    @Column(name = "52_week_low")
    private BigDecimal weekLow;
    @Column(name = "day_change")
    private BigDecimal dayChange;
    @Column(name = "change_pct")
    private BigDecimal changePct;
    @Column(name = "close_yesterday")
    private BigDecimal closeYesterday;
    @Column(name = "market_cap")
    private Long marketCap;
    @Column(name = "volume")
    private Long volume;
    @Column(name = "volume_avg")
    private Double volumeAvg;
    @Column(name = "shares")
    private Long shares;
    @Size(max = 100)
    @Column(name = "stock_exchange_long")
    private String stockExchangeLong;
    @Size(max = 20)
    @Column(name = "stock_exchange_short")
    private String stockExchangeShort;
    @Size(max = 5)
    @Column(name = "timezone")
    private String timezone;
    @Size(max = 20)
    @Column(name = "timezone_name")
    private String timezoneName;
    @Column(name = "gmt_offset")
    private Long gmtOffset;
    @Size(max = 50)
    @Column(name = "last_trade_time")
    private String lastTradeTime;
    @Size(max = 20)
    @Column(name = "pe")
    private String pe;
    @Column(name = "eps")
    private BigDecimal eps;
    @Basic(optional = false)
    @NotNull
    @Column(name = "search_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date searchDateTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_elapsed")
    private Long timeElapsed;
    @JoinColumn(name = "symbol", referencedColumnName = "symbol")
    @ManyToOne(optional = false)
    private Stock symbol;

    /**
     * Default constructor
     */
    public StockPriceSearch() {
    }

    /**
     * Sets the value of stockPriceId
     *
     * @param stockPriceId the identifier
     */
    public StockPriceSearch(Long stockPriceId) {
        this.stockPriceId = stockPriceId;
    }

    /**
     * This parameterized constructor sets the initial values for instance
     * variables.
     *
     * @param stockPriceId the primary key
     * @param price the stock price
     * @param searchDateTime the date and time of the search
     * @param timeElapsed the time elapsed during the search
     */
    public StockPriceSearch(Long stockPriceId, BigDecimal price, Date searchDateTime, long timeElapsed) {
        this.stockPriceId = stockPriceId;
        this.price = price;
        this.searchDateTime = searchDateTime;
        this.timeElapsed = timeElapsed;
    }

    /**
     * Gets the value of stockPriceId.
     *
     * @return the value of stockPriceId
     */
    public Long getStockPriceId() {
        return stockPriceId;
    }

    /**
     * Sets the value of stockPriceId.
     *
     * @param stockPriceId the stockPriceId to set
     */
    public void setStockPriceId(Long stockPriceId) {
        this.stockPriceId = stockPriceId;
    }

    /**
     * Gets the value of currency.
     *
     * @return the value of currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of currency.
     *
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets the value of price.
     *
     * @return the stock price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the value of price.
     *
     * @param price the stock price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets the value of priceOpen.
     *
     * @return the value of priceOpen
     */
    public BigDecimal getPriceOpen() {
        return priceOpen;
    }

    /**
     * Sets the value of priceOpen.
     *
     * @param priceOpen the priceOpen to set
     */
    public void setPriceOpen(BigDecimal priceOpen) {
        this.priceOpen = priceOpen;
    }

    /**
     * Gets the value of dayHigh.
     *
     * @return the value of dayHigh
     */
    public BigDecimal getDayHigh() {
        return dayHigh;
    }

    /**
     * Sets the value of dayHigh.
     *
     * @param dayHigh the dayHigh to set
     */
    public void setDayHigh(BigDecimal dayHigh) {
        this.dayHigh = dayHigh;
    }

    /**
     * Gets the value of dayLow.
     *
     * @return the value of dayLow
     */
    public BigDecimal getDayLow() {
        return dayLow;
    }

    /**
     * Sets the value of dayLow.
     *
     * @param dayLow the dayLow to set
     */
    public void setDayLow(BigDecimal dayLow) {
        this.dayLow = dayLow;
    }

    /**
     * Gets the value of weekHigh.
     *
     * @return the value of weekHigh
     */
    public BigDecimal getWeekHigh() {
        return weekHigh;
    }

    /**
     * Sets the value of weekHigh.
     *
     * @param weekHigh the weekHigh to set
     */
    public void setWeekHigh(BigDecimal weekHigh) {
        this.weekHigh = weekHigh;
    }

    /**
     * Gets the value of weekLow.
     *
     * @return the value of weekLow
     */
    public BigDecimal getWeekLow() {
        return weekLow;
    }

    /**
     * Sets the value of weekLow.
     *
     * @param weekLow the weekLow to set
     */
    public void setWeekLow(BigDecimal weekLow) {
        this.weekLow = weekLow;
    }

    /**
     * Gets the value of dayChange.
     *
     * @return the value of dayChange
     */
    public BigDecimal getDayChange() {
        return dayChange;
    }

    /**
     * Sets the value of dayChange.
     *
     * @param dayChange the dayChange to set
     */
    public void setDayChange(BigDecimal dayChange) {
        this.dayChange = dayChange;
    }

    /**
     * Gets the value of the percentage change in price.
     *
     * @return the value of changePct
     */
    public BigDecimal getChangePct() {
        return changePct;
    }

    /**
     * Sets the value of the percentage change in price.
     *
     * @param changePct the changePct to set
     */
    public void setChangePct(BigDecimal changePct) {
        this.changePct = changePct;
    }

    /**
     * Gets the value of closeYesterday.
     *
     * @return the value of closeYesterday
     */
    public BigDecimal getCloseYesterday() {
        return closeYesterday;
    }

    /**
     * Sets the value of closeYesterday.
     *
     * @param closeYesterday the closeYesterday to set
     */
    public void setCloseYesterday(BigDecimal closeYesterday) {
        this.closeYesterday = closeYesterday;
    }

    /**
     * Gets the value of marketCap.
     *
     * @return the value of marketCap
     */
    public Long getMarketCap() {
        return marketCap;
    }

    /**
     * Sets the value of marketCap.
     *
     * @param marketCap the marketCap to set
     */
    public void setMarketCap(Long marketCap) {
        this.marketCap = marketCap;
    }

    /**
     * Gets the value of volume.
     *
     * @return the value of volume
     */
    public Long getVolume() {
        return volume;
    }

    /**
     * Sets the value of volume.
     *
     * @param volume the volume to set
     */
    public void setVolume(Long volume) {
        this.volume = volume;
    }

    /**
     * Gets the value of volumeAvg.
     *
     * @return the average volume
     */
    public Double getVolumeAvg() {
        return volumeAvg;
    }

    /**
     * Sets the value of volumeAvg.
     *
     * @param volumeAvg the average volume to set
     */
    public void setVolumeAvg(Double volumeAvg) {
        this.volumeAvg = volumeAvg;
    }

    /**
     * Gets the value of shares.
     *
     * @return the value of shares
     */
    public Long getShares() {
        return shares;
    }

    /**
     * Sets the value of shares.
     *
     * @param shares the shares to set
     */
    public void setShares(Long shares) {
        this.shares = shares;
    }

    /**
     * Gets the value of stockExchangeLong.
     *
     * @return the value of stockExchangeLong
     */
    public String getStockExchangeLong() {
        return stockExchangeLong;
    }

    /**
     * Sets the value of stockExchangeLong.
     *
     * @param stockExchangeLong the stockExchangeLong to set
     */
    public void setStockExchangeLong(String stockExchangeLong) {
        this.stockExchangeLong = stockExchangeLong;
    }

    /**
     * Gets the value of stockExchangeShort.
     *
     * @return the value of stockExchangeShort
     */
    public String getStockExchangeShort() {
        return stockExchangeShort;
    }

    /**
     * Sets the value of stockExchangeShort.
     *
     * @param stockExchangeShort the stockExchangeShort to set
     */
    public void setStockExchangeShort(String stockExchangeShort) {
        this.stockExchangeShort = stockExchangeShort;
    }

    /**
     * Gets the value of timezone.
     *
     * @return the value of timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Sets the value of timezone
     *
     * @param timezone the timezone to set
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * Gets the value of timezoneName.
     *
     * @return the value of timezoneName
     */
    public String getTimezoneName() {
        return timezoneName;
    }

    /**
     * Sets the value of timezoneName
     *
     * @param timezoneName the timezoneName to set
     */
    public void setTimezoneName(String timezoneName) {
        this.timezoneName = timezoneName;
    }

    /**
     * Gets the value of the Greenwich Mean Time offset.
     *
     * @return the Greenwich Mean Time offset
     */
    public Long getGmtOffset() {
        return gmtOffset;
    }

    /**
     * Sets the value of the Greenwich Mean Time offset.
     *
     * @param gmtOffset the Greenwich Mean Time offset
     */
    public void setGmtOffset(Long gmtOffset) {
        this.gmtOffset = gmtOffset;
    }

    /**
     * Gets the value of lastTradeTime.
     *
     * @return the value of lastTradeTime
     */
    public String getLastTradeTime() {
        return lastTradeTime;
    }

    /**
     * Sets the value of lastTradeTime.
     *
     * @param lastTradeTime the lastTradeTime to set
     */
    public void setLastTradeTime(String lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    /**
     * Gets the value of the price to earnings ratio.
     *
     * @return the price to earnings ratio
     */
    public String getPe() {
        return pe;
    }

    /**
     * Sets the value of the price to earnings ratio.
     *
     * @param pe the price to earnings ratio to set
     */
    public void setPe(String pe) {
        this.pe = pe;
    }

    /**
     * Gets the value of the earnings per share.
     *
     * @return the earnings per share
     */
    public BigDecimal getEps() {
        return eps;
    }

    /**
     * Sets the value of the earnings per share.
     *
     * @param eps the earnings per share to set
     */
    public void setEps(BigDecimal eps) {
        this.eps = eps;
    }

    /**
     * Gets the value of searchDateTime.
     *
     * @return the value of searchDateTime
     */
    public Date getSearchDateTime() {
        return searchDateTime;
    }

    /**
     * Sets the value of searchDateTime.
     *
     * @param searchDateTime the searchDateTime to set
     */
    public void setSearchDateTime(Date searchDateTime) {
        this.searchDateTime = searchDateTime;
    }

    /**
     * Gets the value of timeElapsed.
     *
     * @return the value of timeElapsed
     */
    public Long getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * Sets the value of timeElapsed.
     *
     * @param timeElapsed the timeElapsed to set
     */
    public void setTimeElapsed(Long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    /**
     * Gets the value of symbol.
     *
     * @return the value of symbol
     */
    public Stock getSymbol() {
        return symbol;
    }

    /**
     * Sets the value of symbol.
     *
     * @param symbol the symbol to set
     */
    public void setSymbol(Stock symbol) {
        this.symbol = symbol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stockPriceId != null ? stockPriceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockPriceSearch)) {
            return false;
        }
        StockPriceSearch other = (StockPriceSearch) object;
        if ((this.stockPriceId == null && other.stockPriceId != null) || (this.stockPriceId != null && !this.stockPriceId.equals(other.stockPriceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.daniel.opmonitor.entity.StockPriceSearch[ stockPriceId=" + stockPriceId + " ]";
    }

}
