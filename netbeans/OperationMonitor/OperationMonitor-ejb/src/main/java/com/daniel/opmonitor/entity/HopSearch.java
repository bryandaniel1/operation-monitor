/*
 * Copyright 2017 Bryan Daniel.
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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This entity class represents the relationship of a geographic location search
 * and a trace route hop.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "HopSearch")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HopSearch.findAll", query = "SELECT h FROM HopSearch h")
    , @NamedQuery(name = "HopSearch.findByHop", query = "SELECT h FROM HopSearch h WHERE h.hop = :hop")})
public class HopSearch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "hop")
    private Long hop;
    @JoinColumn(name = "hop", referencedColumnName = "hop_id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private TracerHop tracerHop;
    @JoinColumn(name = "search", referencedColumnName = "search_id")
    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    private GeolocationSearchEvent search;

    /**
     * Default constructor
     */
    public HopSearch() {
    }

    /**
     * Parameterized constructor setting hop
     *
     * @param hop
     */
    public HopSearch(Long hop) {
        this.hop = hop;
    }

    /**
     * Gets the value of hop
     *
     * @return the value of hop
     */
    public Long getHop() {
        return hop;
    }

    /**
     * Sets the value of hop
     *
     * @param hop the value of hop
     */
    public void setHop(Long hop) {
        this.hop = hop;
    }

    /**
     * Gets the value of tracerHop
     *
     * @return the value of tracerHop
     */
    public TracerHop getTracerHop() {
        return tracerHop;
    }

    /**
     * Sets the value of tracerHop
     *
     * @param tracerHop the value of tracerHop
     */
    public void setTracerHop(TracerHop tracerHop) {
        this.tracerHop = tracerHop;
    }

    /**
     * Gets the value of search
     *
     * @return the value of search
     */
    public GeolocationSearchEvent getSearch() {
        return search;
    }

    /**
     * Sets the value of search
     *
     * @param search the value of search
     */
    public void setSearch(GeolocationSearchEvent search) {
        this.search = search;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hop != null ? hop.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HopSearch)) {
            return false;
        }
        HopSearch other = (HopSearch) object;
        if ((this.hop == null && other.hop != null) || (this.hop != null && !this.hop.equals(other.hop))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.daniel.opmonitor.entity.HopSearch[ hop=" + hop + " ]";
    }

}
