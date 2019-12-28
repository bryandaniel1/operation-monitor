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

import com.daniel.opmonitor.entity.Stock;
import com.daniel.opmonitor.entity.StockHistoryResult;
import com.daniel.opmonitor.entity.StockHistoryResultPK;
import com.daniel.opmonitor.entity.StockHistorySearch;
import com.daniel.opmonitor.entity.StockPriceSearch;
import com.daniel.search.history.HistoricalStockPrice;
import com.daniel.search.history.HistoricalStockPriceSearchEvent;
import com.daniel.search.history.HistoricalStockPriceSearchResult;
import com.daniel.search.price.StockPrice;
import com.daniel.search.price.StockPriceSearchEvent;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This implementation of StockSearchService contains methods for accessing and
 * storing stock search data.
 *
 * @author Bryan Daniel
 */
@Stateless
public class SimpleStockSearchService implements StockSearchService {

    /**
     * The date format for history results
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * The EJB context
     */
    @Resource
    private EJBContext context;

    /**
     * The logger for this class
     */
    private final Logger logger = LogManager.getLogger(SimpleStockSearchService.class);

    /**
     * The entity manager for the chat room entities
     */
    @PersistenceContext(unitName = "OperationMonitor-ejbPU")
    private EntityManager entityManager;

    @Override
    public List<StockPriceSearch> findStockPriceSearchEvents(Date dateOccurred) {

        Query query = entityManager.createNamedQuery("StockPriceSearch.findBySearchDateTime");
        List<StockPriceSearch> stockPriceSearches = null;

        // getting a Calendar set to provide the next day's date
        Calendar dayAfter = Calendar.getInstance();
        dayAfter.setTime(dateOccurred);
        dayAfter.add(Calendar.DATE, 1);

        try {
            stockPriceSearches = query.setParameter("searchDateTime", dateOccurred)
                    .setParameter("dayAfter", dayAfter.getTime()).getResultList();
        } catch (Exception e) {
            logger.error("An exception occurred in the findStockPriceSearchEvents method.", e);
        }
        return stockPriceSearches;
    }

    @Override
    public List<StockHistorySearch> findStockHistorySearchEvents(Date dateOccurred) {

        Query query = entityManager.createNamedQuery("StockHistorySearch.findBySearchDateTime");
        List<StockHistorySearch> stockHistorySearches = null;

        // getting a Calendar set to provide the next day's date
        Calendar dayAfter = Calendar.getInstance();
        dayAfter.setTime(dateOccurred);
        dayAfter.add(Calendar.DATE, 1);

        try {
            stockHistorySearches = query.setParameter("searchDateTime", dateOccurred)
                    .setParameter("dayAfter", dayAfter.getTime()).getResultList();
        } catch (Exception e) {
            logger.error("An exception occurred in the findStockHistorySearchEvents method.", e);
        }
        return stockHistorySearches;
    }

    @Override
    public boolean storeStockPriceSearchEvent(StockPriceSearchEvent stockPriceSearchEvent) {

        try {
            StockPriceSearch stockPriceSearch = new StockPriceSearch();
            stockPriceSearch.setStockPriceId(null);
            Date searchDate = Date.from(stockPriceSearchEvent.getTimeSearched().atZone(ZoneId.systemDefault()).toInstant());
            stockPriceSearch.setSearchDateTime(searchDate);
            stockPriceSearch.setTimeElapsed(stockPriceSearchEvent.getTimeElapsed());
            populateStockPriceValues(stockPriceSearchEvent, stockPriceSearch);
            entityManager.persist(stockPriceSearch);
            entityManager.flush();
            return true;
        } catch (ValidationException ve) {
            logger.error(MessageFormat.format("SimpleStockSearchService: A ValidationException occurred in the storeStockPriceSearchResult method: {0}",
                    ve.getMessage()), ve);
            context.setRollbackOnly();
        } catch (Exception e) {
            logger.error(MessageFormat.format("SimpleStockSearchService: An Exception occurred in the storeStockPriceSearchResult method: {0}",
                    e.getMessage()), e);
            context.setRollbackOnly();
        }
        return false;
    }

    /**
     * Populates the new stock price search entity with the values contained in
     * the stock price search event.
     *
     * @param stockPriceSearchEvent the stock price search event
     * @param stockPriceSearch the stock price search entity
     */
    private void populateStockPriceValues(StockPriceSearchEvent stockPriceSearchEvent, StockPriceSearch stockPriceSearch) {

        List<StockPrice> stockPriceList = stockPriceSearchEvent.getStockPriceSearchResult().getStockPriceData();
        StockPrice stockPriceData = null;
        if (stockPriceList != null && !stockPriceList.isEmpty()) {
            stockPriceData = stockPriceList.get(0);
        }
        if (stockPriceData != null) {
            Stock stock = entityManager.find(Stock.class, stockPriceData.getSymbol());
            if (stock == null) {
                stock = storeStock(stockPriceData.getSymbol(), stockPriceData.getName());
            }
            stockPriceSearch.setChangePct(new BigDecimal(stockPriceData.getChangePct()));
            stockPriceSearch.setCloseYesterday(new BigDecimal(stockPriceData.getCloseYesterday()));
            stockPriceSearch.setCurrency(stockPriceData.getCurrency());
            stockPriceSearch.setDayChange(new BigDecimal(stockPriceData.getDayChange()));
            stockPriceSearch.setDayHigh(new BigDecimal(stockPriceData.getDayHigh()));
            stockPriceSearch.setDayLow(new BigDecimal(stockPriceData.getDayLow()));
            stockPriceSearch.setEps(new BigDecimal(stockPriceData.getEps()));
            stockPriceSearch.setGmtOffset(Long.valueOf(stockPriceData.getGmtOffset()));
            stockPriceSearch.setLastTradeTime(stockPriceData.getLastTradeTime());
            stockPriceSearch.setMarketCap(Long.valueOf(stockPriceData.getMarketCap()));
            stockPriceSearch.setPe(stockPriceData.getPe());
            stockPriceSearch.setPrice(new BigDecimal(stockPriceData.getPrice()));
            stockPriceSearch.setPriceOpen(new BigDecimal(stockPriceData.getPriceOpen()));
            stockPriceSearch.setShares(Long.valueOf(stockPriceData.getShares()));
            stockPriceSearch.setStockExchangeLong(stockPriceData.getStockExchangeLong());
            stockPriceSearch.setStockExchangeShort(stockPriceData.getStockExchangeShort());
            stockPriceSearch.setSymbol(stock);
            stockPriceSearch.setTimeElapsed(stockPriceSearchEvent.getTimeElapsed());
            stockPriceSearch.setTimezone(stockPriceData.getTimezone());
            stockPriceSearch.setTimezoneName(stockPriceData.getTimezoneName());
            stockPriceSearch.setVolume(Long.valueOf(stockPriceData.getVolume()));
            stockPriceSearch.setVolumeAvg(Double.valueOf(stockPriceData.getVolumeAvg()));
        }
    }

    /**
     * Saves a new stock in the database.
     *
     * @param symbol the stock symbol
     * @param name the stock name
     * @return the new stock entity
     */
    private Stock storeStock(String symbol, String name) {
        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setName(name);
        entityManager.persist(stock);
        entityManager.flush();
        return stock;
    }

    @Override
    public boolean storeHistoricalStockPriceSearchEvent(HistoricalStockPriceSearchEvent historicalStockPriceSearchEvent) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        try {
            StockHistorySearch stockHistorySearch = new StockHistorySearch();
            stockHistorySearch.setStockHistorySearchId(null);
            Date searchDate = Date.from(historicalStockPriceSearchEvent.getTimeSearched().atZone(ZoneId.systemDefault()).toInstant());
            stockHistorySearch.setSearchDateTime(searchDate);
            entityManager.persist(stockHistorySearch);
            entityManager.flush();

            HistoricalStockPriceSearchResult historicalStockPriceSearchResult
                    = historicalStockPriceSearchEvent.getHistoricalStockPriceSearchResult();
            Map<String, HistoricalStockPrice> results = historicalStockPriceSearchResult.getHistory();
            List<StockHistoryResult> stockHistoryResultList = new ArrayList<>();
            stockHistorySearch.setStockHistoryResultList(stockHistoryResultList);

            // each day in history is stored
            results.keySet().forEach(historicalStockPriceKey -> {
                HistoricalStockPrice historicalStockPrice = results.get(historicalStockPriceKey);

                StockHistoryResultPK historyResultKey = new StockHistoryResultPK();
                historyResultKey.setSymbol(historicalStockPriceSearchResult.getName());
                LocalDateTime localDateTime = LocalDateTime.from(LocalDate
                        .parse(historicalStockPriceKey, dateTimeFormatter).atStartOfDay());
                Date historyDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                historyResultKey.setHistoryDate(historyDate);

                StockHistoryResult historyResultEntity = entityManager.find(StockHistoryResult.class, historyResultKey);
                if (historyResultEntity == null) {
                    historyResultEntity = storeStockhistoryResult(historyResultKey, historicalStockPrice);
                }
                List<StockHistorySearch> searchList = historyResultEntity.getStockHistorySearchList();
                if (searchList == null) {
                    searchList = new ArrayList<>();
                }
                searchList.add(stockHistorySearch);
                stockHistoryResultList.add(historyResultEntity);
            });
            entityManager.flush();
            return true;
        } catch (ConstraintViolationException e) {
            logger.error("SimpleStockSearchService: A ConstraintViolationException occurred in the storeHistoricalStockPriceSearchEvent method.");
            e.getConstraintViolations().forEach(err -> logger.error(err.toString()));
            context.setRollbackOnly();
        } catch (ValidationException ve) {
            logger.error("SimpleStockSearchService: A ValidationException occurred in the storeHistoricalStockPriceSearchEvent method.",
                    ve);
            context.setRollbackOnly();
        } catch (Exception e) {
            logger.error(MessageFormat.format("SimpleStockSearchService: An Exception occurred in the storeHistoricalStockPriceSearchEvent method: {0}",
                    e.getMessage()), e);
            context.setRollbackOnly();
        }
        return false;
    }

    /**
     * Creates and saves a new stock history result with the search data
     * provided.
     *
     * @param historyResultKey the stock history result key
     * @param historicalStockPrice the search data
     * @return the new entity
     * @throws NumberFormatException
     */
    private StockHistoryResult storeStockhistoryResult(StockHistoryResultPK historyResultKey,
            HistoricalStockPrice historicalStockPrice) throws NumberFormatException {

        StockHistoryResult historyResultEntity = new StockHistoryResult();
        Stock stock = entityManager.find(Stock.class, historyResultKey.getSymbol());
        if (stock == null) {
            throw new ValidationException("Parent entity, Stock, does not exists for this history result.");
        }
        historyResultEntity.setStock(stock);
        historyResultEntity.setStockHistoryResultPK(historyResultKey);
        historyResultEntity.setClose(new BigDecimal(historicalStockPrice.getClose()));
        historyResultEntity.setHigh(new BigDecimal(historicalStockPrice.getHigh()));
        historyResultEntity.setLow(new BigDecimal(historicalStockPrice.getLow()));
        historyResultEntity.setOpen(new BigDecimal(historicalStockPrice.getOpen()));
        historyResultEntity.setVolume(Long.valueOf(historicalStockPrice.getVolume()));
        entityManager.persist(historyResultEntity);
        entityManager.flush();
        return historyResultEntity;
    }
}
