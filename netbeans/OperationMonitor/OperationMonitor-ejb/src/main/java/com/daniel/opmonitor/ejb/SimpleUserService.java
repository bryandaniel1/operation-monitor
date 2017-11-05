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
package com.daniel.opmonitor.ejb;

import com.daniel.opmonitor.entity.GeolocationsUser;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Bryan Daniel
 */
@Stateless
public class SimpleUserService implements UserService {

    /**
     * The beginning index for the salt portion of the hash
     */
    public static final int SALT_BEGIN_INDEX = 0;

    /**
     * The ending index of the salt portion of the hash
     */
    public static final int SALT_END_INDEX = 29;

    /**
     * The entity manager for the chat room entities
     */
    @PersistenceContext(unitName = "OperationMonitor-ejbPU")
    private EntityManager entityManager;

    /**
     * This method uses the given username to find the salt for the user.
     *
     * @param username the username for the user
     * @return the salt for the user or null if no salt is found
     */
    @Override
    public String findUserSalt(String username) {

        GeolocationsUser user = findGeolocationsUser(username);
        if (user != null) {
            String hash = user.getPassword();
            if (hash == null || hash.length() < SALT_END_INDEX) {
                Logger.getLogger(SimpleUserService.class.getName()).log(Level.SEVERE,
                        "SimpleUserService: A valid hash was not found for the user, {0}.",
                        user.getUsername());
            } else {
                return hash.substring(SALT_BEGIN_INDEX, SALT_END_INDEX);
            }
        }
        return null;
    }

    /**
     * This method finds the geolocations user associated with the given
     * username.
     *
     * @param username the username
     * @return the user entity or null if no user is found
     */
    @Override
    public GeolocationsUser findGeolocationsUser(String username) {

        Query query = entityManager.createNamedQuery("GeolocationsUser.findByUsername");
        GeolocationsUser user = null;
        try {
            user = (GeolocationsUser) query.setParameter("username", username).getSingleResult();
        } catch (NoResultException nre) {
            Logger.getLogger(SimpleUserService.class.getName()).log(Level.INFO,
                    "No user could be found with the username, {0}.", username);
        } catch (Exception e) {
            Logger.getLogger(SimpleUserService.class.getName()).log(Level.SEVERE,
                    "SimpleUserService: An exception occurred in the findGeolocationsUser method.", e);
        }
        return user;
    }

    /**
     * This method collects all users of the geolocations database and returns
     * them as a list.
     *
     * @return the list of all users
     */
    @Override
    public List<GeolocationsUser> findAllUsers() {
        
        Query query = entityManager.createNamedQuery("GeolocationsUser.findAll");
        List<GeolocationsUser> users = null;
        try{
            users = query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(SimpleUserService.class.getName()).log(Level.SEVERE,
                    "SimpleUserService: An exception occurred in the findAllUsers method.", e);
        }
        return users;
    }
}
