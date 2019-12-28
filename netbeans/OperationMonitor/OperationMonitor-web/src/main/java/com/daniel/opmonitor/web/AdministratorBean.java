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
package com.daniel.opmonitor.web;

import com.daniel.opmonitor.ejb.UserService;
import com.daniel.opmonitor.entity.OperationMonitorUser;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 * This managed bean contains the variables and functions to support the
 * operations of the administration page.
 *
 * @author Bryan Daniel
 */
@Named(value = "administratorBean")
@ViewScoped
public class AdministratorBean implements Serializable {

    /**
     * serial version UID
     */
    private static final long serialVersionUID = -2812972131144548970L;

    /**
     * The user service
     */
    @EJB
    private UserService userService;

    /**
     * The monitor bean
     */
    @Inject
    private MonitorBean monitorBean;

    /**
     * The users of the stock search database
     */
    private List<OperationMonitorUser> users;

    /**
     * This method is executed after construction to set the value for the list
     * of users.
     */
    @PostConstruct
    public void init() {
        setUsers(userService.findAllUsers());
    }

    /**
     * Get the value of monitorBean
     *
     * @return the value of monitorBean
     */
    public MonitorBean getMonitorBean() {
        return monitorBean;
    }

    /**
     * Set the value of monitorBean
     *
     * @param monitorBean the value of monitorBean
     */
    public void setMonitorBean(MonitorBean monitorBean) {
        this.monitorBean = monitorBean;
    }

    /**
     * Get the value of users
     *
     * @return the value of users
     */
    public List<OperationMonitorUser> getUsers() {
        return users;
    }

    /**
     * Set the value of users
     *
     * @param users new value of users
     */
    public void setUsers(List<OperationMonitorUser> users) {
        this.users = users;
    }
}
