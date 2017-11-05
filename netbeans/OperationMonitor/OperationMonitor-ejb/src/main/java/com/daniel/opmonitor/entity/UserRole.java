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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This entity class holds a user's role.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "UserRole")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u")
    , @NamedQuery(name = "UserRole.findByUsername", query = "SELECT u FROM UserRole u WHERE u.userRolePK.username = :username")
    , @NamedQuery(name = "UserRole.findByRole", query = "SELECT u FROM UserRole u WHERE u.userRolePK.role = :role")})
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserRolePK userRolePK;
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GeolocationsUser geolocationsUser;

    /**
     * Default constructor
     */
    public UserRole() {
    }

    /**
     * Parameterized constructor setting userRolePK
     *
     * @param userRolePK the userRolePK
     */
    public UserRole(UserRolePK userRolePK) {
        this.userRolePK = userRolePK;
    }

    /**
     * Parameterized constructor setting username and role
     *
     * @param username the username
     * @param role the role
     */
    public UserRole(String username, String role) {
        this.userRolePK = new UserRolePK(username, role);
    }

    /**
     * Gets the value of userRolePK
     *
     * @return the value of userRolePK
     */
    public UserRolePK getUserRolePK() {
        return userRolePK;
    }

    /**
     * Sets the value of userRolePK
     *
     * @param userRolePK the value of userRolePK
     */
    public void setUserRolePK(UserRolePK userRolePK) {
        this.userRolePK = userRolePK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userRolePK != null ? userRolePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRole)) {
            return false;
        }
        UserRole other = (UserRole) object;
        if ((this.userRolePK == null && other.userRolePK != null) || (this.userRolePK != null && !this.userRolePK.equals(other.userRolePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.daniel.opmonitor.entity.UserRole[ userRolePK=" + userRolePK + " ]";
    }

}
