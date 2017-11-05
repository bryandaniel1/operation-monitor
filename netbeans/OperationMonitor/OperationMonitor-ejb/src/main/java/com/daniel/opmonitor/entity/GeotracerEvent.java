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
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This entity class represents a geographic tracer execution.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "GeotracerEvent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeotracerEvent.findAll", query = "SELECT g FROM GeotracerEvent g")
    , 
    @NamedQuery(name = "GeotracerEvent.findByTracerId", query = "SELECT g FROM GeotracerEvent g "
            + "WHERE g.tracerId = :tracerId")
    , 
    @NamedQuery(name = "GeotracerEvent.findByTimeExecuted", query = "SELECT g FROM GeotracerEvent g "
            + "WHERE g.timeExecuted = :timeExecuted")
    , 
    @NamedQuery(name = "GeotracerEvent.findByTimeElapsed", query = "SELECT g FROM GeotracerEvent g "
            + "WHERE g.timeElapsed = :timeElapsed")
    , 
    @NamedQuery(name = "GeotracerEvent.findEventsByDay", query = "SELECT g FROM GeotracerEvent g "
            + "WHERE g.timeExecuted >= :daySearched AND g.timeExecuted < :dayAfter ORDER BY g.timeExecuted")})
public class GeotracerEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracer_id")
    private Long tracerId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_executed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeExecuted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_elapsed")
    private long timeElapsed;
    @JoinTable(name = "TracerSearch", joinColumns = {
        @JoinColumn(name = "tracer", referencedColumnName = "tracer_id")}, inverseJoinColumns = {
        @JoinColumn(name = "search", referencedColumnName = "search_id")})
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "tracer")
    private List<TracerHop> tracerHopList;

    /**
     * Default constructor
     */
    public GeotracerEvent() {
    }

    /**
     * Parameterized constructor setting tracerId
     *
     * @param tracerId
     */
    public GeotracerEvent(Long tracerId) {
        this.tracerId = tracerId;
    }

    /**
     * Parameterized constructor setting tracerId, timeExecuted, and timeElapsed
     *
     * @param tracerId the ID
     * @param timeExecuted the time executed
     * @param timeElapsed the time elapsed
     */
    public GeotracerEvent(Long tracerId, Date timeExecuted, long timeElapsed) {
        this.tracerId = tracerId;
        this.timeExecuted = timeExecuted;
        this.timeElapsed = timeElapsed;
    }

    /**
     * Gets the value of tracerId
     *
     * @return the value of tracerId
     */
    public Long getTracerId() {
        return tracerId;
    }

    /**
     * Sets the value of tracerId
     *
     * @param tracerId the value of tracerId
     */
    public void setTracerId(Long tracerId) {
        this.tracerId = tracerId;
    }

    /**
     * Gets the value of timeExecuted
     *
     * @return the value of timeExecuted
     */
    public Date getTimeExecuted() {
        return timeExecuted;
    }

    /**
     * Sets the value of timeExecuted
     *
     * @param timeExecuted the value of timeExecuted
     */
    public void setTimeExecuted(Date timeExecuted) {
        this.timeExecuted = timeExecuted;
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
     * Gets the value of tracerHopList
     *
     * @return the value of tracerHopList
     */
    @XmlTransient
    public List<TracerHop> getTracerHopList() {
        return tracerHopList;
    }

    /**
     * Sets the value of tracerHopList
     *
     * @param tracerHopList the value of tracerHopList
     */
    public void setTracerHopList(List<TracerHop> tracerHopList) {
        this.tracerHopList = tracerHopList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tracerId != null ? tracerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeotracerEvent)) {
            return false;
        }
        GeotracerEvent other = (GeotracerEvent) object;
        if ((this.tracerId == null && other.tracerId != null) || (this.tracerId != null && !this.tracerId.equals(other.tracerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.daniel.opmonitor.entity.GeotracerEvent[ tracerId=" + tracerId + " ]";
    }

}