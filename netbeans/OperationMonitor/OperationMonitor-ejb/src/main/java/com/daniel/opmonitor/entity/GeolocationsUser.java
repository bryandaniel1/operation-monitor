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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This entity class represents a user of the OperationMonitor application.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "GeolocationsUser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeolocationsUser.findAll", query = "SELECT g FROM GeolocationsUser g"), 
    @NamedQuery(name = "GeolocationsUser.findByUsername", query = "SELECT g FROM GeolocationsUser g WHERE g.username = :username"), 
    @NamedQuery(name = "GeolocationsUser.findByFirstName", query = "SELECT g FROM GeolocationsUser g WHERE g.firstName = :firstName"), 
    @NamedQuery(name = "GeolocationsUser.findByLastName", query = "SELECT g FROM GeolocationsUser g WHERE g.lastName = :lastName"), 
    @NamedQuery(name = "GeolocationsUser.findByPassword", query = "SELECT g FROM GeolocationsUser g WHERE g.password = :password")})
public class GeolocationsUser implements Serializable {

    private static final long serialVersionUID = -9117976787447048486L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "geolocationsUser")
    private List<UserRole> userRoleList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "geolocationsUser")
    private UserContact userContact;

    /**
     * Default constructor
     */
    public GeolocationsUser() {
    }

    /**
     * Parameterized constructor setting username
     *
     * @param username the username
     */
    public GeolocationsUser(String username) {
        this.username = username;
    }

    /**
     * Parameterized constructor setting username, firstName, lastName, and
     * password
     *
     * @param username the user's username
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param password the password
     */
    public GeolocationsUser(String username, String firstName, String lastName, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
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
     * Gets the value of firstName
     *
     * @return the value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of firstName
     *
     * @param firstName the value of firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the value of lastName
     *
     * @return the value of lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of lastName
     *
     * @param lastName the value of lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of password
     *
     * @param password the value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the value of userRoleList
     *
     * @return the value of userRoleList
     */
    @XmlTransient
    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    /**
     * Sets the value of userRoleList
     *
     * @param userRoleList the value of userRoleList
     */
    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    /**
     * Gets the value of userContact
     *
     * @return the value of userContact
     */
    public UserContact getUserContact() {
        return userContact;
    }

    /**
     * Sets the value of userContact
     *
     * @param userContact the value of userContact
     */
    public void setUserContact(UserContact userContact) {
        this.userContact = userContact;
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
        if (!(object instanceof GeolocationsUser)) {
            return false;
        }
        GeolocationsUser other = (GeolocationsUser) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.daniel.opmonitor.entity.GeolocationsUser[ username=" + username + " ]";
    }

}
