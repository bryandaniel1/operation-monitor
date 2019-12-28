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
package com.daniel.stocksclient.web;

import com.daniel.search.history.HistoricalStockPriceSearchResult;
import com.daniel.search.price.StockPriceSearchResult;
import com.daniel.stocksclient.ejb.StockSearchService;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This REST Web Service handles requests to search for stock price information.
 *
 * @author Bryan Daniel
 */
@Path("/")
@RequestScoped
public class StockSearchResource {

    /**
     * The stock price service
     */
    @EJB(beanName = "stockSearchService")
    private StockSearchService stockSearchService;

    /**
     * The logger for this class
     */
    private final Logger logger = LogManager.getLogger(StockSearchResource.class);

    /**
     * This method takes a stock symbol parameter and invokes the service to
     * find and return the company stock market data.
     *
     * @param stockSymbol the stock symbol
     * @return the response object
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("price")
    public Response findStockPrice(@FormParam("stockSymbol") String stockSymbol) {

        Response errorResponse;
        if ((errorResponse = createErrorResponse(stockSymbol)) != null) {
            return errorResponse;
        }

        StockPriceSearchResult stockPriceSearchResult = stockSearchService.findStockPrice(stockSymbol);
        return Response.ok(StocksClientUtil.toJson(stockPriceSearchResult), "application/json").build();
    }

    /**
     * This method takes a stock symbol parameter and invokes the service to
     * find and return the historical company stock market data.
     *
     * @param stockSymbol the stock symbol
     * @return the response object
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("history")
    public Response findStockHistory(@FormParam("stockSymbol") String stockSymbol) {

        Response errorResponse;
        if ((errorResponse = createErrorResponse(stockSymbol)) != null) {
            return errorResponse;
        }

        HistoricalStockPriceSearchResult historicalStockPriceSearchResult = stockSearchService.findStockHistory(stockSymbol);
        return Response.ok(StocksClientUtil.toJson(historicalStockPriceSearchResult), "application/json").build();
    }

    /**
     * Validates the existence of the required stockSymbol parameter. If it is
     * null, an error response is returned.
     *
     * @param stockSymbol the stock symbol
     * @return the error response, or null if the parameter is valid
     */
    private Response createErrorResponse(String stockSymbol) {
        if (stockSymbol == null) {
            String message = "Property 'symbol' is missing.";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(message).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        return null;
    }
}
