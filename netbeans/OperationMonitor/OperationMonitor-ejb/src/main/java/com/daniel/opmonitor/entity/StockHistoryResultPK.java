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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This entity class represents the primary key for a StockHistoryResult entity.
 *
 * @author Bryan Daniel
 */
@Embeddable
public class StockHistoryResultPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "symbol")
    private String symbol;
    @Basic(optional = false)
    @NotNull
    @Column(name = "history_date")
    @Temporal(TemporalType.DATE)
    private Date historyDate;

    /**
     * Default constructor
     */
    public StockHistoryResultPK() {
    }

    /**
     * Sets the value of symbol and date.
     *
     * @param symbol the stock symbol
     * @param historyDate the date
     */
    public StockHistoryResultPK(String symbol, Date historyDate) {
        this.symbol = symbol;
        this.historyDate = historyDate;
    }

    /**
     * Gets the value of symbol.
     *
     * @return the value of symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the value of symbol
     *
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets the value of historyDate.
     *
     * @return the value of historyDate
     */
    public Date getHistoryDate() {
        return historyDate;
    }

    /**
     * Sets the value of historyDate
     *
     * @param historyDate the historyDate to set
     */
    public void setHistoryDate(Date historyDate) {
        this.historyDate = historyDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (symbol != null ? symbol.hashCode() : 0);
        hash += (historyDate != null ? historyDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockHistoryResultPK)) {
            return false;
        }
        StockHistoryResultPK other = (StockHistoryResultPK) object;
        if ((this.symbol == null && other.symbol != null) || (this.symbol != null && !this.symbol.equals(other.symbol))) {
            return false;
        }
        if ((this.historyDate == null && other.historyDate != null) || (this.historyDate != null && !this.historyDate.equals(other.historyDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.daniel.opmonitor.entity.StockHistoryResultPK[ symbol=" + symbol + ", historyDate=" + historyDate + " ]";
    }

}
