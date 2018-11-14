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
package com.daniel.tracer.ejb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This class contains functionality for executing the traceroute function and
 * returning the IP addresses in the path.
 *
 * @author Bryan Daniel
 */
public class TracerouteRunner implements Callable<LinkedList<String>> {
    
    /**
     * The no response indicator for a hop
     */
    public static final String NO_RESPONSE = "no response";

    /**
     * The logger for this class
     */
    private final Logger logger = LogManager.getLogger(TracerouteRunner.class);

    /**
     * The command used for the task
     */
    private String command;

    /**
     * This default constructor sets the command to null.
     */
    public TracerouteRunner() {
        command = null;
    }

    /**
     * This parameterized constructor sets the value of command.
     *
     * @param command the command
     */
    public TracerouteRunner(String command) {
        this.command = command;
    }

    /**
     * This method executes the traceroute command and returns the resulting
     * list of IP addresses.
     *
     * @return the list of IP addresses
     * @throws Exception
     */
    @Override
    public LinkedList<String> call() throws Exception {
        LinkedList<String> hopAddresses = new LinkedList<>();
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        int iteration = 0;
        while ((line = reader.readLine()) != null) {
            logger.info(line);
            if (iteration > 0) {
                StringTokenizer tokenizer = new StringTokenizer(line, " ");
                tokenizer.nextToken();
                String address = tokenizer.nextToken();
                if (address.equals("*")) {
                    hopAddresses.add(NO_RESPONSE);
                } else {
                    hopAddresses.add(address);
                }
            }
            iteration++;
        }
        return hopAddresses;
    }

    /**
     * Set the value of command
     *
     * @param command new value of command
     */
    public void setCommand(String command) {
        this.command = command;
    }
}
