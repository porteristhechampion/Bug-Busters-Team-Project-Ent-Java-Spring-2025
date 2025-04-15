package com.bugbusters.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility for configuring AWS credentials with local override.
 */
public class AwsAuthUtil {

    private static final Logger logger = LogManager.getLogger(AwsAuthUtil.class);
    private static final String PROPERTIES_FILE = "aws.properties";

    public static AwsCredentialsProvider getCredentialsProvider() {
        Properties props = new Properties();

        try (InputStream input = AwsAuthUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input != null) {
                props.load(input);
                String accessKey = props.getProperty("aws.accessKeyId");
                String secretKey = props.getProperty("aws.secretAccessKey");

                if (accessKey != null && secretKey != null) {
                    logger.info("Using credentials from aws.properties");
                    return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
                }
            }
        } catch (IOException e) {
            logger.warn("Unable to load aws.properties, falling back to default provider");
        }

        logger.info("Using DefaultCredentialsProvider");
        return DefaultCredentialsProvider.create();
    }

    public static Region getRegion() {
        Properties props = new Properties();

        try (InputStream input = AwsAuthUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input != null) {
                props.load(input);
                String regionName = props.getProperty("aws.region");
                if (regionName != null) {
                    return Region.of(regionName);
                }
            }
        } catch (IOException e) {
            logger.warn("Failed to load region from aws.properties, using default region");
        }

        return Region.US_EAST_2; // Change if your default is different
    }
}
