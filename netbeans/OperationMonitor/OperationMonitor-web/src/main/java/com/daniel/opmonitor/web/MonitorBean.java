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

import com.daniel.opmonitor.ejb.StockSearchService;
import com.daniel.opmonitor.entity.StockHistorySearch;
import com.daniel.opmonitor.entity.StockPriceSearch;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
        SEARCH("Stock Price Search"),
        HISTORY("Stock History Search");

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
     * The logger for this class
     */
    private final Logger logger = LogManager.getLogger(MonitorBean.class);

    /**
     * The stock search data service
     */
    @EJB
    private StockSearchService stockSearchService;

    /**
     * The list of stock price search events
     */
    private List<StockPriceSearch> stockPriceSearchEvents;

    /**
     * The list of stock history search events
     */
    private List<StockHistorySearch> stockHistorySearchEvents;

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

        setStockPriceSearchEvents(null);
        setStockHistorySearchEvents(null);
        switch (selectedEventType) {
            case SEARCH:
                findStockPriceSearchEvents();
                if (getStockPriceSearchEvents() == null || getStockPriceSearchEvents().isEmpty()) {
                    FacesMessage message = new FacesMessage("No stock price searches were found for the selected date.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
                break;
            case HISTORY:
                findStockHistorySearchEvents();
                if (getStockHistorySearchEvents() == null || getStockHistorySearchEvents().isEmpty()) {
                    FacesMessage message = new FacesMessage("No stock price history searches were found for the selected date.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
                break;
            default:
                break;
        }
    }

    /**
     * This method finds all stock price search events for the selected date.
     */
    public void findStockPriceSearchEvents() {
        setStockPriceSearchEvents(stockSearchService.findStockPriceSearchEvents(selectedDate));
    }

    /**
     * This method finds all stock history events for the selected date.
     */
    public void findStockHistorySearchEvents() {
        setStockHistorySearchEvents(stockSearchService.findStockHistorySearchEvents(selectedDate));
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
            logger.error("Failed to sign out user!", e);
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
     * Get the value of stockPriceSearchEvents
     *
     * @return the value of stockPriceSearchEvents
     */
    public List<StockPriceSearch> getStockPriceSearchEvents() {
        return stockPriceSearchEvents;
    }

    /**
     * Set the value of stockPriceSearchEvents
     *
     * @param stockPriceSearchEvents new value of stockPriceSearchEvents
     */
    public void setStockPriceSearchEvents(List<StockPriceSearch> stockPriceSearchEvents) {
        this.stockPriceSearchEvents = stockPriceSearchEvents;
    }

    /**
     * Get the value of stockHistorySearchEvents
     *
     * @return the value of stockHistorySearchEvents
     */
    public List<StockHistorySearch> getStockHistorySearchEvents() {
        return stockHistorySearchEvents;
    }

    /**
     * Set the value of stockHistorySearchEvents
     *
     * @param stockHistorySearchEvents new value of stockHistorySearchEvents
     */
    public void setStockHistorySearchEvents(List<StockHistorySearch> stockHistorySearchEvents) {
        this.stockHistorySearchEvents = stockHistorySearchEvents;
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
