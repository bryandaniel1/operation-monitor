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

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This entity class represents a geographic location search.
 * @author Bryan Daniel
 */
@Entity
@Table(name = "GeolocationSearchEvent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeolocationSearchEvent.findAll", query = "SELECT g FROM GeolocationSearchEvent g")
    , 
    @NamedQuery(name = "GeolocationSearchEvent.findBySearchId", query = "SELECT g FROM GeolocationSearchEvent g "
            + "WHERE g.searchId = :searchId")
    , 
    @NamedQuery(name = "GeolocationSearchEvent.findByTimeSearched", query = "SELECT g FROM GeolocationSearchEvent g "
            + "WHERE g.timeSearched = :timeSearched")
    , 
    @NamedQuery(name = "GeolocationSearchEvent.findByTimeElapsed", query = "SELECT g FROM GeolocationSearchEvent g "
            + "WHERE g.timeElapsed = :timeElapsed")
    , 
    @NamedQuery(name = "GeolocationSearchEvent.findEventsByDay", query = "SELECT g FROM GeolocationSearchEvent g "
            + "WHERE g.timeSearched >= :daySearched AND g.timeSearched < :dayAfter ORDER BY g.timeSearched")})
public class GeolocationSearchEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_id")
    private Long searchId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_searched")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeSearched;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_elapsed")
    private long timeElapsed;
    @JsonManagedReference
    @JoinColumn(name = "location", referencedColumnName = "location_id")
    @ManyToOne(optional = false)
    private Geolocation location;
    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "search")
    private HopSearch hopSearch;

    /**
     * Default constructor
     */
    public GeolocationSearchEvent() {
    }

    /**
     * Parameterized constructor setting searchId
     *
     * @param searchId
     */
    public GeolocationSearchEvent(Long searchId) {
        this.searchId = searchId;
    }

    /**
     * Parameterized constructor setting searchId, timeSearched, and timeElapsed
     *
     * @param searchId the ID
     * @param timeSearched the time searched
     * @param timeElapsed the time elapsed
     */
    public GeolocationSearchEvent(Long searchId, Date timeSearched, long timeElapsed) {
        this.searchId = searchId;
        this.timeSearched = timeSearched;
        this.timeElapsed = timeElapsed;
    }

    /**
     * Gets the value of searchId
     *
     * @return the value of searchId
     */
    public Long getSearchId() {
        return searchId;
    }

    /**
     * Sets the value of searchId
     *
     * @param searchId the value of searchId
     */
    public void setSearchId(Long searchId) {
        this.searchId = searchId;
    }

    /**
     * Gets the value of timeSearched
     *
     * @return the value of timeSearched
     */
    public Date getTimeSearched() {
        return timeSearched;
    }

    /**
     * Sets the value of timeSearched
     *
     * @param timeSearched the value of timeSearched
     */
    public void setTimeSearched(Date timeSearched) {
        this.timeSearched = timeSearched;
    }

    /**
     * Gets the value of timeElapsed
     *
     * @return the value of timeElapsed
     */
    public long getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * Sets the value of timeElapsed
     *
     * @param timeElapsed the value of timeElapsed
     */
    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    /**
     * Gets the value of location
     *
     * @return the value of location
     */
    public Geolocation getLocation() {
        return location;
    }

    /**
     * Sets the value of location
     *
     * @param location the value of location
     */
    public void setLocation(Geolocation location) {
        this.location = location;
    }

    /**
     * Gets the value of hopSearch
     *
     * @return the value of hopSearch
     */
    public HopSearch getHopSearch() {
        return hopSearch;
    }

    /**
     * Sets the value of hopSearch
     *
     * @param hopSearch the value of hopSearch
     */
    public void setHopSearch(HopSearch hopSearch) {
        this.hopSearch = hopSearch;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (searchId != null ? searchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeolocationSearchEvent)) {
            return false;
        }
        GeolocationSearchEvent other = (GeolocationSearchEvent) object;
        if ((this.searchId == null && other.searchId != null) || (this.searchId != null && !this.searchId.equals(other.searchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.daniel.opmonitor.entity.GeolocationSearchEvent[ searchId=" + searchId + " ]";
    }
    
}
