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
package com.daniel.stocksclient.ejb;

import java.io.IOException;
import java.text.MessageFormat;
import javax.ws.rs.NotFoundException;
import org.apache.log4j.Logger;

/**
 * Contains basic functionality for StockSearchService implementations.
 *
 * @author Bryan Daniel
 */
public class StockSearchTemplate {

    /**
     * The World Trading API token is a property set in GlassFish System
     * Properties with this key.
     */
    private static final String WORLD_TRADING_API_TOKEN = "world_trading_api_token";

    /**
     * Returns the value of the API token for the stock information web service.
     *
     * @return the API token
     */
    protected String getApiToken() {
        return System.getProperty(WORLD_TRADING_API_TOKEN);
    }

    /**
     * Logs the exception for the given stock symbol.
     *
     * @param exception the exception
     * @param stockSymbol the stock symbol
     * @param logger the logger to use
     */
    protected void logException(Exception exception, String stockSymbol, Logger logger) {
        if (NotFoundException.class.isInstance(exception)) {
            logger.info(MessageFormat.format("The request for stock symbol, {0}, returned a \"404 Not Found\" response.",
                    stockSymbol));
        } else if (IOException.class.isInstance(exception)) {
            logger.error(MessageFormat.format("An I/O exception occurred searching for stock symbol: {0}",
                    stockSymbol), exception);
        } else {
            logger.error(MessageFormat.format("An exception occurred searching for stock symbol: {0}",
                    stockSymbol), exception);
        }
    }
}
