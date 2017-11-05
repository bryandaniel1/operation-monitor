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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This entity class represents a hop in a trace route.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "TracerHop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TracerHop.findAll", query = "SELECT t FROM TracerHop t")
    , @NamedQuery(name = "TracerHop.findByHopId", query = "SELECT t FROM TracerHop t WHERE t.hopId = :hopId")
    , @NamedQuery(name = "TracerHop.findByHopOrder", query = "SELECT t FROM TracerHop t WHERE t.hopOrder = :hopOrder")})
public class TracerHop implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "hop_id")
    private Long hopId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hop_order")
    private long hopOrder;
    @JoinColumn(name = "tracer", referencedColumnName = "tracer_id")
    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    private GeotracerEvent tracer;
    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "tracerHop")
    private HopSearch hopSearch;

    /**
     * Default constructor
     */
    public TracerHop() {
    }

    /**
     * Parameterized constructor setting hopId
     *
     * @param hopId
     */
    public TracerHop(Long hopId) {
        this.hopId = hopId;
    }

    /**
     * Parameterized constructor setting hopId and hopOrder
     *
     * @param hopId the hop ID
     * @param hopOrder the hop order
     */
    public TracerHop(Long hopId, long hopOrder) {
        this.hopId = hopId;
        this.hopOrder = hopOrder;
    }

    /**
     * Gets the value of hopId
     *
     * @return the value of hopId
     */
    public Long getHopId() {
        return hopId;
    }

    /**
     * Sets the value of hopId
     *
     * @param hopId the value of hopId
     */
    public void setHopId(Long hopId) {
        this.hopId = hopId;
    }

    /**
     * Gets the value of hopOrder
     *
     * @return the value of hopOrder
     */
    public long getHopOrder() {
        return hopOrder;
    }

    /**
     * Sets the value of hopOrder
     *
     * @param hopOrder the value of hopOrder
     */
    public void setHopOrder(long hopOrder) {
        this.hopOrder = hopOrder;
    }

    /**
     * Gets the value of tracer
     *
     * @return the value of tracer
     */
    public GeotracerEvent getTracer() {
        return tracer;
    }

    /**
     * Sets the value of tracer
     *
     * @param tracer the value of tracer
     */
    public void setTracer(GeotracerEvent tracer) {
        this.tracer = tracer;
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
        hash += (hopId != null ? hopId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TracerHop)) {
            return false;
        }
        TracerHop other = (TracerHop) object;
        if ((this.hopId == null && other.hopId != null) || (this.hopId != null && !this.hopId.equals(other.hopId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.daniel.opmonitor.entity.TracerHop[ hopId=" + hopId + " ]";
    }

}
