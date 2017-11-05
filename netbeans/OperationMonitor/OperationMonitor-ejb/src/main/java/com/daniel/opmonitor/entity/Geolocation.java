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
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This entity class represents a geographic location.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "Geolocation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Geolocation.findAll", query = "SELECT g FROM Geolocation g")
    , 
    @NamedQuery(name = "Geolocation.findByLocationId", query = "SELECT g FROM Geolocation g "
            + "WHERE g.locationId = :locationId")
    , 
    @NamedQuery(name = "Geolocation.findByIpAddress", query = "SELECT g FROM Geolocation g "
            + "WHERE g.ipAddress = :ipAddress")
    , 
    @NamedQuery(name = "Geolocation.findByLatitude", query = "SELECT g FROM Geolocation g "
            + "WHERE g.latitude = :latitude")
    , 
    @NamedQuery(name = "Geolocation.findByLongitude", query = "SELECT g FROM Geolocation g "
            + "WHERE g.longitude = :longitude")
    , 
    @NamedQuery(name = "Geolocation.findByCity", query = "SELECT g FROM Geolocation g "
            + "WHERE g.city = :city")
    , 
    @NamedQuery(name = "Geolocation.findByZipCode", query = "SELECT g FROM Geolocation g "
            + "WHERE g.zipCode = :zipCode")
    , 
    @NamedQuery(name = "Geolocation.findByTimeZone", query = "SELECT g FROM Geolocation g "
            + "WHERE g.timeZone = :timeZone")
    , 
    @NamedQuery(name = "Geolocation.findByMetroCode", query = "SELECT g FROM Geolocation g "
            + "WHERE g.metroCode = :metroCode")
    , 
    @NamedQuery(name = "Geolocation.findUniqueGeolocation", query = "SELECT g FROM Geolocation g "
            + "WHERE g.ipAddress = :ipAddress AND g.latitude = :latitude AND g.longitude = :longitude")})
public class Geolocation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ip_address")
    private String ipAddress;
    @Basic(optional = false)
    @NotNull
    @Max(value = 90)
    @Min(value = -90)
    @Column(name = "latitude")
    private BigDecimal latitude;
    @Basic(optional = false)
    @NotNull
    @Max(value = 180)
    @Min(value = -180)
    @Column(name = "longitude")
    private BigDecimal longitude;
    @Size(max = 50)
    @Column(name = "city")
    private String city;
    @Size(max = 12)
    @Column(name = "zip_code")
    private String zipCode;
    @Size(max = 50)
    @Column(name = "time_zone")
    private String timeZone;
    @Column(name = "metro_code")
    private Integer metroCode;
    @JsonManagedReference
    @JoinColumn(name = "country", referencedColumnName = "country_code")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Country country;
    @JsonManagedReference
    @JoinColumn(name = "region", referencedColumnName = "region_code")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Region region;
    @JsonBackReference
    @OneToMany(mappedBy = "location")
    private List<GeolocationSearchEvent> geolocationSearchEventList;

    /**
     * Default constructor
     */
    public Geolocation() {
    }

    /**
     * Parameterized constructor setting locationId
     *
     * @param locationId
     */
    public Geolocation(Long locationId) {
        this.locationId = locationId;
    }

    /**
     * Parameterized constructor setting locationId, ipAddress, latitude, and
     * longitude
     *
     * @param locationId the ID
     * @param ipAddress the IP address
     * @param latitude the latitude
     * @param longitude the longitude
     */
    public Geolocation(Long locationId, String ipAddress, BigDecimal latitude, BigDecimal longitude) {
        this.locationId = locationId;
        this.ipAddress = ipAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Gets the value of locationId
     *
     * @return the value of locationId
     */
    public Long getLocationId() {
        return locationId;
    }

    /**
     * Sets the value of locationId
     *
     * @param locationId the value of locationId
     */
    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    /**
     * Gets the value of ipAddress
     *
     * @return the value of ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Sets the value of ipAddress
     *
     * @param ipAddress the value of ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Gets the value of latitude
     *
     * @return the value of latitude
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of latitude
     *
     * @param latitude the value of latitude
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the value of longitude
     *
     * @return the value of longitude
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of longitude
     *
     * @param longitude the value of longitude
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
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
     * Gets the value of timeZone
     *
     * @return the value of timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * Sets the value of timeZone
     *
     * @param timeZone the value of timeZone
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Gets the value of metroCode
     *
     * @return the value of metroCode
     */
    public Integer getMetroCode() {
        return metroCode;
    }

    /**
     * Sets the value of metroCode
     *
     * @param metroCode the value of metroCode
     */
    public void setMetroCode(Integer metroCode) {
        this.metroCode = metroCode;
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
     * Gets the value of geolocationSearchEventList
     *
     * @return the value of geolocationSearchEventList
     */
    @XmlTransient
    public List<GeolocationSearchEvent> getGeolocationSearchEventList() {
        return geolocationSearchEventList;
    }

    /**
     * Sets the value of geolocationSearchEventList
     *
     * @param geolocationSearchEventList the value of geolocationSearchEventList
     */
    public void setGeolocationSearchEventList(List<GeolocationSearchEvent> geolocationSearchEventList) {
        this.geolocationSearchEventList = geolocationSearchEventList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationId != null ? locationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Geolocation)) {
            return false;
        }
        Geolocation other = (Geolocation) object;
        if ((this.locationId == null && other.locationId != null) || (this.locationId != null && !this.locationId.equals(other.locationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.daniel.opmonitor.entity.Geolocation[ locationId=" + locationId + " ]";
    }

}