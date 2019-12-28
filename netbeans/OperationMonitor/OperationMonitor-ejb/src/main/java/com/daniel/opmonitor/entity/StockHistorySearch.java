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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This entity class represents a stock price history search.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "StockHistorySearch")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StockHistorySearch.findAll", query = "SELECT s FROM StockHistorySearch s")
    , @NamedQuery(name = "StockHistorySearch.findByStockHistorySearchId", query = "SELECT s FROM StockHistorySearch s "
            + "WHERE s.stockHistorySearchId = :stockHistorySearchId")
    , @NamedQuery(name = "StockHistorySearch.findBySearchDateTime", query = "SELECT s FROM StockHistorySearch s "
            + "WHERE s.searchDateTime >= :searchDateTime AND s.searchDateTime < :dayAfter ORDER BY s.searchDateTime")})
public class StockHistorySearch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "stock_history_search_id")
    private Long stockHistorySearchId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "search_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date searchDateTime;
    @ManyToMany(mappedBy = "stockHistorySearchList", fetch = FetchType.EAGER)
    private List<StockHistoryResult> stockHistoryResultList;

    /**
     * Default constructor
     */
    public StockHistorySearch() {
    }

    /**
     * Sets the value of stockHistorySearchId
     *
     * @param stockHistorySearchId the identifier
     */
    public StockHistorySearch(Long stockHistorySearchId) {
        this.stockHistorySearchId = stockHistorySearchId;
    }

    /**
     * Sets the value of stockHistorySearchId and searchDateTime.
     *
     * @param stockHistorySearchId the stockHistorySearchId to set
     * @param searchDateTime the searchDateTime to set
     */
    public StockHistorySearch(Long stockHistorySearchId, Date searchDateTime) {
        this.stockHistorySearchId = stockHistorySearchId;
        this.searchDateTime = searchDateTime;
    }

    /**
     * Gets the value of stockHistorySearchId.
     *
     * @return the value of stockHistorySearchId
     */
    public Long getStockHistorySearchId() {
        return stockHistorySearchId;
    }

    /**
     * Sets the value of stockHistorySearchId
     *
     * @param stockHistorySearchId the stockHistorySearchId to set
     */
    public void setStockHistorySearchId(Long stockHistorySearchId) {
        this.stockHistorySearchId = stockHistorySearchId;
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
     * Sets the value of searchDateTime
     *
     * @param searchDateTime the searchDateTime to set
     */
    public void setSearchDateTime(Date searchDateTime) {
        this.searchDateTime = searchDateTime;
    }

    @XmlTransient
    public List<StockHistoryResult> getStockHistoryResultList() {
        return stockHistoryResultList;
    }

    /**
     * Sets the value of stockHistoryResultList
     *
     * @param stockHistoryResultList the stockHistoryResultList to set
     */
    public void setStockHistoryResultList(List<StockHistoryResult> stockHistoryResultList) {
        this.stockHistoryResultList = stockHistoryResultList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stockHistorySearchId != null ? stockHistorySearchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockHistorySearch)) {
            return false;
        }
        StockHistorySearch other = (StockHistorySearch) object;
        if ((this.stockHistorySearchId == null && other.stockHistorySearchId != null) || (this.stockHistorySearchId != null && !this.stockHistorySearchId.equals(other.stockHistorySearchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.daniel.opmonitor.entity.StockHistorySearch[ stockHistorySearchId=" + stockHistorySearchId + " ]";
    }

}
