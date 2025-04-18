package com.bugbusters.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.providers.DefaultAwsRegionProviderChain;

import java.util.Properties;

/**
 * This class is a utility class for resolving AWS credentials and region settings.
 * It first attempts to use the standard AWS SDK default provider chain for credentials
 * and region. If that fails, it falls back to loading values from a local properties
 * file. As a final fallback for region, a hardcoded default value is used.
 *
 * @author Justin Gritton-Bell
 */
public class AwsAuthUtil {

    private static final Logger logger = LogManager.getLogger(AwsAuthUtil.class);

    private static final String PROPERTIES_FILE = "/aws.properties";
    private static final Region DEFAULT_REGION = Region.US_EAST_2;

    /**
     * Attempts to resolve AWS credentials using the standard AWS SDK default provider chain.
     * If that fails it falls back to static credentials loaded from aws.properties.
     *
     * @return a valid AwsCredentialsProvider
     * @throws RuntimeException if no valid credentials are found
     */
    public static AwsCredentialsProvider getCredentialsProvider() {
        try {
            AwsCredentialsProvider defaultProvider = DefaultCredentialsProvider.create();
            defaultProvider.resolveCredentials(); // test early to trigger any failures
            logger.info("Using credentials from DefaultCredentialsProvider");
            return defaultProvider;
        } catch (Exception e) {
            logger.warn("DefaultCredentialsProvider failed: " + e.getMessage());
        }

        Properties props = PropertiesLoader.load(PROPERTIES_FILE);
        String accessKey = props.getProperty("aws.accessKeyId");
        String secretKey = props.getProperty("aws.secretAccessKey");

        if (accessKey != null && secretKey != null) {
            logger.info("Using credentials from aws.properties");
            return StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKey, secretKey));
        }

        throw new RuntimeException("No valid AWS credentials found in environment or properties file.");
    }

    /**
     * Attempts to resolve the AWS region using the default AWS provider chain.
     * Falls back to the aws.properties file, if still invalid, defaults to a
     * hardcoded default region.
     *
     * @return the resolved AWS Region
     */
    public static Region getRegion() {
        try {
            Region envRegion = new DefaultAwsRegionProviderChain().getRegion();
            if (envRegion != null) {
                logger.info("Using region from DefaultAwsRegionProviderChain: " + envRegion);
                return envRegion;
            }
        } catch (Exception e) {
            logger.warn("DefaultAwsRegionProviderChain failed: " + e.getMessage());
        }

        // Fallback to aws.properties
        Properties props = PropertiesLoader.load(PROPERTIES_FILE);
        String regionName = props.getProperty("aws.region");
        if (regionName != null) {
            try {
                Region propRegion = Region.of(regionName);
                logger.info("Using region from aws.properties: " + propRegion);
                return propRegion;
            } catch (IllegalArgumentException e) {
                logger.warn("Invalid region format in aws.properties: " + regionName);
            }
        }

        logger.warn("Using hardcoded default region: " + DEFAULT_REGION);
        return DEFAULT_REGION;
    }
}
