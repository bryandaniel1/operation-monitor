/*
 * Copyright 2019 Bryan Daniel.
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

import com.daniel.opmonitor.entity.OperationMonitorUser;
import java.util.List;
import javax.ejb.Local;

/**
 * This interface contains functionality for accessing and managing data of
 * OperationMonitor users.
 *
 * @author Bryan Daniel
 */
@Local
public interface UserService {

    /**
     * This method uses the given username to find the salt for the user.
     *
     * @param username the username
     * @return the salt for the user
     */
    public String findUserSalt(String username);

    /**
     * This method finds the Operation Monitor user associated with the given
     * username.
     *
     * @param username the username
     * @return the user entity
     */
    public OperationMonitorUser findOperationMonitorUser(String username);

    /**
     * This method collects all users of the stock search database and returns
     * them as a list.
     *
     * @return the list of all users
     */
    public List<OperationMonitorUser> findAllUsers();
}
