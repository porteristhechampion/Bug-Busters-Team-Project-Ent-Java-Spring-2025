package com.bugbusters.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for loading properties files from the classpath.
 *
 * @author Justin Gritton-Bell
 */
public final class PropertiesLoader {

    private static final Logger logger = LogManager.getLogger(PropertiesLoader.class);

    /**
     * Constructor to prevent instantiation.
     */
    private PropertiesLoader() {
    }

    /**
     * Loads a .properties file from the classpath into a Properties object.
     *
     * @param filePath properties file path
     * @return properties object || null
     */
    public static Properties load(String filePath) {
        Properties properties = new Properties();

        try (InputStream input = PropertiesLoader.class.getResourceAsStream(filePath)) {
            if (input != null) {
                properties.load(input);
            } else {
                logger.warn("Properties file not found: " + filePath);
            }
        } catch (Exception e) {
            logger.error("Failed to load properties file: " + filePath, e);
        }

        return properties;
    }
}
