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
 * This entity class represents a region in a country.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "Region")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Region.findAll", query = "SELECT r FROM Region r")
    , @NamedQuery(name = "Region.findByRegionName", query = "SELECT r FROM Region r WHERE r.regionName = :regionName")})
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "region_code")
    private String regionCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "region_name")
    private String regionName;
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "region")
    private List<Geolocation> geolocationList;
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "region")
    private List<UserContact> userContactList;

    /**
     * Default constructor
     */
    public Region() {
    }

    /**
     * Parameterized constructor setting regionCode
     *
     * @param regionCode
     */
    public Region(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * Parameterized constructor setting regionCode and regionName
     *
     * @param regionCode the region code
     * @param regionName the region name
     */
    public Region(String regionCode, String regionName) {
        this.regionCode = regionCode;
        this.regionName = regionName;
    }

    /**
     * Gets the value of regionCode
     *
     * @return the value of regionCode
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * Sets the value of regionCode
     *
     * @param regionCode the value of regionCode
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * Gets the value of regionName
     *
     * @return the value of regionName
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * Sets the value of regionName
     *
     * @param regionName the value of regionName
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * Gets the value of geolocationList
     *
     * @return the value of geolocationList
     */
    @XmlTransient
    public List<Geolocation> getGeolocationList() {
        return geolocationList;
    }

    /**
     * Sets the value of geolocationList
     *
     * @param geolocationList the value of geolocationList
     */
    public void setGeolocationList(List<Geolocation> geolocationList) {
        this.geolocationList = geolocationList;
    }

    /**
     * Gets the value of userContactList
     *
     * @return the value of userContactList
     */
    @XmlTransient
    public List<UserContact> getUserContactList() {
        return userContactList;
    }

    /**
     * Sets the value of userContactList
     *
     * @param userContactList the value of userContactList
     */
    public void setUserContactList(List<UserContact> userContactList) {
        this.userContactList = userContactList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regionCode != null ? regionCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Region)) {
            return false;
        }
        Region other = (Region) object;
        if ((this.regionCode == null && other.regionCode != null) || (this.regionCode != null && !this.regionCode.equals(other.regionCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.daniel.opmonitor.entity.Region[ regionCode=" + regionCode + " ]";
    }
}