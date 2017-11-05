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

import com.daniel.opmonitor.ejb.GeolocationService;
import com.daniel.opmonitor.entity.GeolocationSearchEvent;
import com.daniel.opmonitor.entity.GeotracerEvent;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This managed bean supports the operations of the monitor page.
 *
 * @author Bryan Daniel
 */
@Named(value = "monitorBean")
@SessionScoped
public class MonitorBean implements Serializable {

    /**
     * The event types
     */
    public enum EventType {
        SEARCH("Search"),
        TRACER("Tracer");

        /**
         * The event type label
         */
        private final String label;

        /**
         * This private constructor sets the value of the label.
         *
         * @param label the label
         */
        private EventType(String label) {
            this.label = label;
        }

        /**
         * Get the value of label
         *
         * @return the value of label
         */
        public String getLabel() {
            return label;
        }
    }

    /**
     * serial version UID
     */
    private static final long serialVersionUID = -3169450582569772788L;

    /**
     * The geolocation service
     */
    @EJB
    private GeolocationService geolocationService;

    /**
     * The geolocation search event
     */
    private List<GeolocationSearchEvent> geolocationSearchEvents;

    /**
     * The list of tracer events
     */
    private List<GeotracerEvent> geotracerEvents;

    /**
     * The selected event type
     */
    private EventType selectedEventType;

    /**
     * The selected date
     */
    private Date selectedDate;

    /**
     * This method invokes a search method according to the event type selected.
     */
    public void search() {

        setGeolocationSearchEvents(null);
        setGeotracerEvents(null);
        switch (selectedEventType) {
            case SEARCH:
                findGeolocationSearchEvents();
                if (getGeolocationSearchEvents() == null || getGeolocationSearchEvents().isEmpty()) {
                    FacesMessage message = new FacesMessage("No geolocation search events were found for the selected date.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
                break;
            case TRACER:
                findGeotracerEvents();
                if (getGeotracerEvents() == null || getGeotracerEvents().isEmpty()) {
                    FacesMessage message = new FacesMessage("No geotracer events were found for the selected date.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
                break;
            default:
                break;
        }
    }

    /**
     * This method finds all geolocation search events for the selected date
     */
    public void findGeolocationSearchEvents() {
        setGeolocationSearchEvents(geolocationService.findGeolocationSearchEvents(selectedDate));
    }

    /**
     * This method finds all geotracer events for the selected date
     */
    public void findGeotracerEvents() {
        setGeotracerEvents(geolocationService.findGeotracerEvents(selectedDate));
    }

    /**
     * This method signs out the signed-in user and invalidates the session.
     *
     * @return the sign in page
     */
    public String signOut() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.logout();
            ((HttpSession) context.getExternalContext().getSession(false)).invalidate();
        } catch (ServletException e) {
            Logger.getLogger(SignInBean.class.getName()).log(Level.SEVERE,
                    "Failed to sign out user!", e);
        }
        return "/signin?faces-redirect=true";
    }

    /**
     * Get the value of eventTypes
     *
     * @return the value of eventTypes
     */
    public EventType[] getEventTypes() {
        return EventType.values();
    }

    /**
     * Get the value of geolocationSearchEvents
     *
     * @return the value of geolocationSearchEvents
     */
    public List<GeolocationSearchEvent> getGeolocationSearchEvents() {
        return geolocationSearchEvents;
    }

    /**
     * Set the value of geolocationSearchEvents
     *
     * @param geolocationSearchEvents new value of geolocationSearchEvents
     */
    public void setGeolocationSearchEvents(List<GeolocationSearchEvent> geolocationSearchEvents) {
        this.geolocationSearchEvents = geolocationSearchEvents;
    }

    /**
     * Get the value of geotracerEvents
     *
     * @return the value of geotracerEvents
     */
    public List<GeotracerEvent> getGeotracerEvents() {
        return geotracerEvents;
    }

    /**
     * Set the value of geotracerEvents
     *
     * @param geotracerEvents new value of geotracerEvents
     */
    public void setGeotracerEvents(List<GeotracerEvent> geotracerEvents) {
        this.geotracerEvents = geotracerEvents;
    }

    /**
     * Get the value of selectedEventType
     *
     * @return the value of selectedEventType
     */
    public EventType getSelectedEventType() {
        return selectedEventType;
    }

    /**
     * Set the value of selectedEventType
     *
     * @param selectedEventType new value of selectedEventType
     */
    public void setSelectedEventType(EventType selectedEventType) {
        this.selectedEventType = selectedEventType;
    }

    /**
     * Get the value of selectedDate
     *
     * @return the value of selectedDate
     */
    public Date getSelectedDate() {
        return selectedDate;
    }

    /**
     * Set the value of selectedDate
     *
     * @param selectedDate new value of selectedDate
     */
    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }
}
