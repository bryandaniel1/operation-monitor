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
package com.daniel.search;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the JSON geographic location data transmitted back to
 * the client after the service call.
 * 
 * The data will appear in the following format.
 * <p>
 * {<br>
 * "ip": "string",<br>
 * "country_code": "string",<br>
 * "country_name": "string",<br>
 * "region_code": "string",<br>
 * "region_name": "string",<br>
 * "city": "string",<br>
 * "zip_code": "string",<br>
 * "time_zone": "string",<br>
 * "latitude": float,<br>
 * "longitude": float,<br>
 * "metro_code": integer<br>
 * }
 * </p>
 *
 * @author Bryan Daniel
 */
@XmlRootElement(name = "geolocation")
public class GeolocationResult {
    
    /**
     * The IP address of the geographic location
     */
    @XmlElement(name = "ip")
    private String ipAddress;

    /**
     * The 2-letter country code
     */
    @XmlElement(name = "country_code")
    private String countryCode;

    /**
     * The name of the country
     */
    @XmlElement(name = "country_name")
    private String countryName;

    /**
     * The code of the region within the country
     */
    @XmlElement(name = "region_code")
    private String regionCode;

    /**
     * The name of the region within the country
     */
    @XmlElement(name = "region_name")
    private String regionName;

    /**
     * The city of the geographic location
     */
    @XmlElement(name = "city")
    private String city;

    /**
     * The postal code
     */
    @XmlElement(name = "zip_code")
    private String zipCode;

    /**
     * The time zone
     */
    @XmlElement(name = "time_zone")
    private String timeZone;

    /**
     * The latitude
     */
    @XmlElement(name = "latitude")
    private float latitude;

    /**
     * The longitude
     */
    @XmlElement(name = "longitude")
    private float longitude;

    /**
     * The metro code
     */
    @XmlElement(name = "metro_code")
    private int metroCode;

    /**
     * Get the value of ipAddress
     *
     * @return the value of ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Set the value of ipAddress
     *
     * @param ipAddress new value of ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Get the value of countryCode
     *
     * @return the value of countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Set the value of countryCode
     *
     * @param countryCode new value of countryCode
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Get the value of countryName
     *
     * @return the value of countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Set the value of countryName
     *
     * @param countryName new value of countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Get the value of regionCode
     *
     * @return the value of regionCode
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * Set the value of regionCode
     *
     * @param regionCode new value of regionCode
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * Get the value of regionName
     *
     * @return the value of regionName
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * Set the value of regionName
     *
     * @param regionName new value of regionName
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * Get the value of city
     *
     * @return the value of city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the value of city
     *
     * @param city new value of city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get the value of zipCode
     *
     * @return the value of zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Set the value of zipCode
     *
     * @param zipCode new value of zipCode
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Get the value of timeZone
     *
     * @return the value of timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * Set the value of timeZone
     *
     * @param timeZone new value of timeZone
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Get the value of latitude
     *
     * @return the value of latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Set the value of latitude
     *
     * @param latitude new value of latitude
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * Get the value of longitude
     *
     * @return the value of longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Set the value of longitude
     *
     * @param longitude new value of longitude
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * Get the value of metroCode
     *
     * @return the value of metroCode
     */
    public int getMetroCode() {
        return metroCode;
    }

    /**
     * Set the value of metroCode
     *
     * @param metroCode new value of metroCode
     */
    public void setMetroCode(int metroCode) {
        this.metroCode = metroCode;
    }
}
