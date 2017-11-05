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
package com.daniel.opmonitor.web;

import com.daniel.opmonitor.ejb.UserService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;

/**
 * This managed bean supports the operations of the sign-in page.
 *
 * @author Bryan Daniel
 */
@Named(value = "signInBean")
@ViewScoped
public class SignInBean implements Serializable {

    /**
     * The name for the user role
     */
    public static final String USER_ROLE = "user";

    /**
     * The name for the administrator role
     */
    public static final String ADMINISTRATOR_ROLE = "administrator";

    /**
     * serial version UID
     */
    private static final long serialVersionUID = -1424138516357001762L;

    /**
     * The user service
     */
    @EJB
    private UserService userService;

    /**
     * The username
     */
    private String username;

    /**
     * The password
     */
    private String password;

    @PostConstruct
    public void init() {
    }

    /**
     * This method invokes the user service to authenticate the user before
     * allowing access to the monitor area.
     *
     * @return the main monitor page
     */
    public String signIn() {
        FacesContext context = FacesContext.getCurrentInstance();

        String salt = userService.findUserSalt(username);
        if (salt == null) {
            context.addMessage(null, new FacesMessage("Sign in failed."));
        } else {
            String passwordHashed = BCrypt.hashpw(getPassword(), salt);
            try {
                ExternalContext externalContext = context.getExternalContext();
                HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
                request.login(username, passwordHashed);
                if (request.getUserPrincipal() != null) {
                    if (request.isUserInRole(USER_ROLE) || request.isUserInRole(ADMINISTRATOR_ROLE)) {
                        externalContext.getSessionMap().put("user", 
                                userService.findGeolocationsUser(username));
                        return "/monitor/index?faces-redirect=true&includeViewParams=true";
                    } else {
                        context.addMessage(null, new FacesMessage("You are not authorized to use the operation monitor."));
                    }
                }
            } catch (ServletException e) {
                context.addMessage(null, new FacesMessage("Sign in failed."));
            }
        }
        return "error";
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
     * @param username the new value of username
     */
    public void setUsername(String username) {
        this.username = username;
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
     * @param password the new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
