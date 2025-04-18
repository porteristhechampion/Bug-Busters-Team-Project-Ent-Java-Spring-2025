package com.bugbusters.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class LogTest {
    private static final Logger logger = LogManager.getLogger(LogTest.class);

    @Test
    void logAllLevels() {
        logger.debug("This is a DEBUG message.");
        logger.info("This is an INFO message.");
        logger.error("This is an ERROR message.");

    }
}