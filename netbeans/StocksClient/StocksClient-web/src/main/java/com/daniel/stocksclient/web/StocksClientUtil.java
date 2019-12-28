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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Contains utility functions for the Stocks Client application.
 *
 * @author Bryan Daniel
 */
public class StocksClientUtil {

    /**
     * The logger for this class
     */
    private static final Logger LOGGER = LogManager.getLogger(StocksClientUtil.class);

    /**
     * This method converts the stocks search result object to a JSON string.
     *
     * @param searchResult the search result data
     * @return the JSON string
     */
    public static String toJson(Object searchResult) {
        String json = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            json = mapper.writeValueAsString(searchResult);
        } catch (JsonProcessingException ex) {
            LOGGER.error("A JsonProcessingException occurred in the toJson method.", ex);
        }
        return json;
    }
}
