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

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This entity class represents a stock price.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "Stock")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s")
    , @NamedQuery(name = "Stock.findBySymbol", query = "SELECT s FROM Stock s WHERE s.symbol = :symbol")
    , @NamedQuery(name = "Stock.findByName", query = "SELECT s FROM Stock s WHERE s.name = :name")})
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "symbol")
    private String symbol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "symbol")
    private List<StockPriceSearch> stockPriceSearchList;
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stock")
    private List<StockHistoryResult> stockHistoryResultList;

    /**
     * Default constructor
     */
    public Stock() {
    }

    /**
     * Sets the value of symbol.
     *
     * @param symbol the stock symbol
     */
    public Stock(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Sets the value of symbol and name.
     *
     * @param symbol the stock symbol
     * @param name the stock name
     */
    public Stock(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    /**
     * Gets the value of symbol.
     *
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the value of symbol.
     *
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets the value of name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of stock price searches.
     *
     * @return the stock price search list
     */
    @XmlTransient
    public List<StockPriceSearch> getStockPriceSearchList() {
        return stockPriceSearchList;
    }

    /**
     * Sets the value of the stock price search list
     *
     * @param stockPriceSearchList the list of stock price searches
     */
    public void setStockPriceSearchList(List<StockPriceSearch> stockPriceSearchList) {
        this.stockPriceSearchList = stockPriceSearchList;
    }

    /**
     * Gets the list of stock history search results.
     *
     * @return the stock price history results list
     */
    @XmlTransient
    public List<StockHistoryResult> getStockHistoryResultList() {
        return stockHistoryResultList;
    }

    /**
     * Sets the value of the stock history search results.
     *
     * @param stockHistoryResultList the list of stock price history results
     */
    public void setStockHistoryResultList(List<StockHistoryResult> stockHistoryResultList) {
        this.stockHistoryResultList = stockHistoryResultList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (symbol != null ? symbol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stock)) {
            return false;
        }
        Stock other = (Stock) object;
        if ((this.symbol == null && other.symbol != null) || (this.symbol != null && !this.symbol.equals(other.symbol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.daniel.opmonitor.entity.Stock[ symbol=" + symbol + " ]";
    }

}
