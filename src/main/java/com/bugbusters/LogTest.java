package com.bugbusters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogTest {
    private static final Logger logger = LogManager.getLogger(LogTest.class);

    public static void main(String[] args) {
        logger.debug("This is a DEBUG message.");
        logger.info("This is an INFO message.");
        logger.error("This is an ERROR message.");
    }
}