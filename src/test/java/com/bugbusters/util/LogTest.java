package com.bugbusters.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

/**
 * This class tests log4j.
 *
 * @author Alison Fait
 */
public class LogTest {

    private static final Logger logger = LogManager.getLogger(LogTest.class);

    /**
     * This method tests logging debug,
     * info, and error.
     */
    @Test
    void logAllLevels() {
        logger.debug("This is a DEBUG message.");
        logger.info("This is an INFO message.");
        logger.error("This is an ERROR message.");

    }
}