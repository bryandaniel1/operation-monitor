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
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This entity class represents a day within a price history for a stock.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "StockHistoryResult")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StockHistoryResult.findAll", query = "SELECT s FROM StockHistoryResult s")
    , @NamedQuery(name = "StockHistoryResult.findBySymbol", query = "SELECT s FROM StockHistoryResult s WHERE s.stockHistoryResultPK.symbol = :symbol")
    , @NamedQuery(name = "StockHistoryResult.findByHistoryDate", query = "SELECT s FROM StockHistoryResult s WHERE s.stockHistoryResultPK.historyDate = :historyDate")
    , @NamedQuery(name = "StockHistoryResult.findByOpen", query = "SELECT s FROM StockHistoryResult s WHERE s.open = :open")
    , @NamedQuery(name = "StockHistoryResult.findByClose", query = "SELECT s FROM StockHistoryResult s WHERE s.close = :close")
    , @NamedQuery(name = "StockHistoryResult.findByHigh", query = "SELECT s FROM StockHistoryResult s WHERE s.high = :high")
    , @NamedQuery(name = "StockHistoryResult.findByLow", query = "SELECT s FROM StockHistoryResult s WHERE s.low = :low")
    , @NamedQuery(name = "StockHistoryResult.findByVolume", query = "SELECT s FROM StockHistoryResult s WHERE s.volume = :volume")})
public class StockHistoryResult implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StockHistoryResultPK stockHistoryResultPK;
    @Column(name = "open")
    private BigDecimal open;
    @Column(name = "close")
    private BigDecimal close;
    @Column(name = "high")
    private BigDecimal high;
    @Column(name = "low")
    private BigDecimal low;
    @Column(name = "volume")
    private Long volume;
    @JoinTable(name = "StockHistorySearchResult", joinColumns = {
        @JoinColumn(name = "symbol", referencedColumnName = "symbol")
        , @JoinColumn(name = "history_date", referencedColumnName = "history_date")}, inverseJoinColumns = {
        @JoinColumn(name = "stock_history_search_id", referencedColumnName = "stock_history_search_id")})
    @ManyToMany
    private List<StockHistorySearch> stockHistorySearchList;
    @JoinColumn(name = "symbol", referencedColumnName = "symbol", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Stock stock;

    /**
     * Default constructor
     */
    public StockHistoryResult() {
    }

    /**
     * Sets the primary key value.
     *
     * @param stockHistoryResultPK the primary key
     */
    public StockHistoryResult(StockHistoryResultPK stockHistoryResultPK) {
        this.stockHistoryResultPK = stockHistoryResultPK;
    }

    /**
     * Sets the value of symbol and date.
     *
     * @param symbol the stock symbol
     * @param historyDate the date
     */
    public StockHistoryResult(String symbol, Date historyDate) {
        this.stockHistoryResultPK = new StockHistoryResultPK(symbol, historyDate);
    }

    /**
     * Gets the value of the primary key.
     *
     * @return the primary key
     */
    public StockHistoryResultPK getStockHistoryResultPK() {
        return stockHistoryResultPK;
    }

    /**
     * Sets the value of the primary key.
     *
     * @param stockHistoryResultPK the primary key to set
     */
    public void setStockHistoryResultPK(StockHistoryResultPK stockHistoryResultPK) {
        this.stockHistoryResultPK = stockHistoryResultPK;
    }

    /**
     * Gets the value of open.
     *
     * @return the value of open
     */
    public BigDecimal getOpen() {
        return open;
    }

    /**
     * Sets the value of open
     *
     * @param open the open to set
     */
    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    /**
     * Gets the value of close.
     *
     * @return the value of close
     */
    public BigDecimal getClose() {
        return close;
    }

    /**
     * Sets the value of close
     *
     * @param close the close to set
     */
    public void setClose(BigDecimal close) {
        this.close = close;
    }

    /**
     * Gets the value of high.
     *
     * @return the value of high
     */
    public BigDecimal getHigh() {
        return high;
    }

    /**
     * Sets the value of high
     *
     * @param high the high to set
     */
    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    /**
     * Gets the value of low.
     *
     * @return the value of low
     */
    public BigDecimal getLow() {
        return low;
    }

    /**
     * Sets the value of low
     *
     * @param low the low to set
     */
    public void setLow(BigDecimal low) {
        this.low = low;
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
     * Sets the value of volume
     *
     * @param volume the volume to set
     */
    public void setVolume(Long volume) {
        this.volume = volume;
    }

    /**
     * Gets the value of stockHistorySearchList.
     *
     * @return the value of stockHistorySearchList
     */
    @XmlTransient
    public List<StockHistorySearch> getStockHistorySearchList() {
        return stockHistorySearchList;
    }

    /**
     * Sets the value of stockHistorySearchList
     *
     * @param stockHistorySearchList the stockHistorySearchList to set
     */
    public void setStockHistorySearchList(List<StockHistorySearch> stockHistorySearchList) {
        this.stockHistorySearchList = stockHistorySearchList;
    }

    /**
     * Gets the value of stock.
     *
     * @return the value of stock
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * Sets the value of stock
     *
     * @param stock the stock to set
     */
    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stockHistoryResultPK != null ? stockHistoryResultPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockHistoryResult)) {
            return false;
        }
        StockHistoryResult other = (StockHistoryResult) object;
        if ((this.stockHistoryResultPK == null && other.stockHistoryResultPK != null) || (this.stockHistoryResultPK != null && !this.stockHistoryResultPK.equals(other.stockHistoryResultPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.daniel.opmonitor.entity.StockHistoryResult[ stockHistoryResultPK=" + stockHistoryResultPK + " ]";
    }

}
