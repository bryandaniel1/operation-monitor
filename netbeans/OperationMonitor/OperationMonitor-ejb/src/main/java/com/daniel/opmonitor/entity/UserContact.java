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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This entity class holds contact information for a user of the
 * OperationMonitor application.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "UserContact")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserContact.findAll", query = "SELECT u FROM UserContact u")
    , @NamedQuery(name = "UserContact.findByUsername", query = "SELECT u FROM UserContact u WHERE u.username = :username")
    , @NamedQuery(name = "UserContact.findByStreet", query = "SELECT u FROM UserContact u WHERE u.street = :street")
    , @NamedQuery(name = "UserContact.findByCity", query = "SELECT u FROM UserContact u WHERE u.city = :city")
    , @NamedQuery(name = "UserContact.findByZipCode", query = "SELECT u FROM UserContact u WHERE u.zipCode = :zipCode")})
public class UserContact implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "street")
    private String street;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "zip_code")
    private String zipCode;
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private GeolocationsUser geolocationsUser;
    @JsonManagedReference
    @JoinColumn(name = "region", referencedColumnName = "region_code")
    @ManyToOne(optional = false)
    private Region region;
    @JsonManagedReference
    @JoinColumn(name = "country", referencedColumnName = "country_code")
    @ManyToOne(optional = false)
    private Country country;
    
    /**
     * Default constructor
     */
    public UserContact() {
    }

    /**
     * Parameterized constructor setting username
     *
     * @param username the username
     */
    public UserContact(String username) {
        this.username = username;
    }

    /**
     * Parameterized constructor setting username, street, city, and zipCode
     *
     * @param username the username
     * @param street the street
     * @param city the city
     * @param zipCode the zip code
     */
    public UserContact(String username, String street, String city, String zipCode) {
        this.username = username;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    /**
     * Gets the value of username
     *
     * @return the value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of username
     *
     * @param username the value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the value of street
     *
     * @return the value of street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the value of street
     *
     * @param street the value of street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the value of city
     *
     * @return the value of city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of city
     *
     * @param city the value of city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the value of zipCode
     *
     * @return the value of zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the value of zipCode
     *
     * @param zipCode the value of zipCode
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Gets the value of geolocationsUser
     *
     * @return the value of geolocationsUser
     */
    public GeolocationsUser getGeolocationsUser() {
        return geolocationsUser;
    }

    /**
     * Sets the value of geolocationsUser
     *
     * @param geolocationsUser the value of geolocationsUser
     */
    public void setGeolocationsUser(GeolocationsUser geolocationsUser) {
        this.geolocationsUser = geolocationsUser;
    }

    /**
     * Gets the value of region
     *
     * @return the value of region
     */
    public Region getRegion() {
        return region;
    }

    /**
     * Sets the value of region
     *
     * @param region the value of region
     */
    public void setRegion(Region region) {
        this.region = region;
    }

    /**
     * Gets the value of country
     *
     * @return the value of country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Sets the value of country
     *
     * @param country the value of country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserContact)) {
            return false;
        }
        UserContact other = (UserContact) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.daniel.opmonitor.entity.UserContact[ username=" + username + " ]";
    }
}
