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
 * This entity class represents a country.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "Country")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c")
    , @NamedQuery(name = "Country.findByCountryName", query = "SELECT c FROM Country c WHERE c.countryName = :countryName")})
public class Country implements Serializable {    

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "country_code")
    private String countryCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "country_name")
    private String countryName;
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private List<Geolocation> geolocationList;
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private List<UserContact> userContactList;

    /**
     * Default constructor
     */
    public Country() {
    }

    /**
     * Parameterized constructor setting countryCode
     *
     * @param countryCode
     */
    public Country(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Parameterized constructor setting countryCode and countryName
     *
     * @param countryCode the country code
     * @param countryName the country name
     */
    public Country(String countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    /**
     * Gets the value of countryCode
     *
     * @return the value of countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of countryCode
     *
     * @param countryCode the value of countryCode
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Gets the value of countryName
     *
     * @return the value of countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the value of countryName
     *
     * @param countryName the value of countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
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
        hash += (countryCode != null ? countryCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country) object;
        if ((this.countryCode == null && other.countryCode != null) || (this.countryCode != null && !this.countryCode.equals(other.countryCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.daniel.opmonitor.entity.Country[ countryCode=" + countryCode + " ]";
    }
}