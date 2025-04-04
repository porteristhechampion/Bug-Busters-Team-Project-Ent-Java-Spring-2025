package com.bugbusters.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * The interface Properties loader.
 */
public interface PropertiesLoader {

    /**
     * The constant logger.
     */
    static final Logger logger = LogManager.getLogger(PropertiesLoader.class);

    /**
     * Load properties properties.
     *
     * @param propertiesFilePath the properties file path
     * @return the properties
     */
    default Properties loadProperties(String propertiesFilePath){
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(propertiesFilePath));
        } catch (IOException ioException) {
            logger.error(ioException);
        } catch (Exception exception) {
            logger.error(exception);
        }
        return properties;
    }
}
