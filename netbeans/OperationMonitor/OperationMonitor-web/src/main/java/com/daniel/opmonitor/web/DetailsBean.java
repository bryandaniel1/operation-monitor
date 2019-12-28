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

import com.daniel.opmonitor.entity.StockHistoryResult;
import com.daniel.opmonitor.entity.StockHistorySearch;
import com.daniel.opmonitor.entity.StockPriceSearch;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This managed bean holds the information and functionality to support the
 * event details page.
 *
 * @author Bryan Daniel
 */
@Named(value = "detailsBean")
@SessionScoped
public class DetailsBean implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 2829701574790099787L;

    /**
     * The selected stock price data search event
     */
    private StockPriceSearch selectedStockPriceSearchEvent;

    /**
     * The stock history search event
     */
    private StockHistorySearch selectedStockHistorySearchEvent;

    /**
     * The days for the selected stock history search event
     */
    private List<StockHistoryResult> historyResults;

    /**
     * This method clears any stored history search event and returns the
     * details page target.
     *
     * @return the target for the details page
     */
    public String viewSearchEvent() {
        setSelectedStockHistorySearchEvent(null);
        setHistoryResults(null);
        return "/monitor/details";
    }

    /**
     * This method clears any stored search event and returns the details page
     * target.
     *
     * @return the target for the details page
     */
    public String viewHistorySearchEvent() {
        setSelectedStockPriceSearchEvent(null);
        historyResults = new ArrayList<>();

        selectedStockHistorySearchEvent.getStockHistoryResultList().forEach((result) -> {
            historyResults.add(result);
        });
        return "/monitor/details";
    }

    /**
     * Get the value of selectedStockPriceSearchEvent
     *
     * @return the value of selectedStockPriceSearchEvent
     */
    public StockPriceSearch getSelectedStockPriceSearchEvent() {
        return selectedStockPriceSearchEvent;
    }

    /**
     * Set the value of selectedStockPriceSearchEvent
     *
     * @param selectedStockPriceSearchEvent new value of
     * selectedStockPriceSearchEvent
     */
    public void setSelectedStockPriceSearchEvent(StockPriceSearch selectedStockPriceSearchEvent) {
        this.selectedStockPriceSearchEvent = selectedStockPriceSearchEvent;
    }

    /**
     * Get the value of selectedStockHistorySearchEvent
     *
     * @return the value of selectedStockHistorySearchEvent
     */
    public StockHistorySearch getSelectedStockHistorySearchEvent() {
        return selectedStockHistorySearchEvent;
    }

    /**
     * Set the value of selectedStockHistorySearchEvent
     *
     * @param selectedStockHistorySearchEvent new value of
     * selectedStockHistorySearchEvent
     */
    public void setSelectedStockHistorySearchEvent(StockHistorySearch selectedStockHistorySearchEvent) {
        this.selectedStockHistorySearchEvent = selectedStockHistorySearchEvent;
    }

    /**
     * Get the value of historyResults
     *
     * @return the value of historyResults
     */
    public List<StockHistoryResult> getHistoryResults() {
        return historyResults;
    }

    /**
     * Set the value of historyResults
     *
     * @param historyResults new value of historyResults
     */
    public void setHistoryResults(List<StockHistoryResult> historyResults) {
        this.historyResults = historyResults;
    }
}
