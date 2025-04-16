package com.bugbusters.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Manages the database connection lifecycle and initialization using
 * configuration from database.properties.
 */
public class Database {

    private static final Logger logger = LogManager.getLogger(Database.class);

    private static Database instance = new Database();

    private Properties properties;
    private Connection connection;

    /**
     * Instantiates the singleton Database instance and loads configuration.
     */
    public Database() {
        init();
    }

    private void init() {
        try {
            properties = PropertiesLoader.load("/database.properties");
        } catch (Exception e) {
            logger.error("Failed to load database properties", e);
        }
    }

    /**
     * Returns the singleton instance of Database.
     *
     * @return the instance
     */
    public static Database getInstance() {
        return instance;
    }

    /**
     * Returns the current connection, or null if not connected.
     *
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Establishes a connection to the database using the loaded properties.
     *
     * @throws Exception if the JDBC driver is missing or connection fails
     */
    public void connect() throws Exception {
        if (connection != null)
            return;

        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            throw new Exception("Database.connect()... Error: JDBC Driver not found");
        }

        String url = properties.getProperty("url");
        connection = DriverManager.getConnection(
                url,
                properties.getProperty("username"),
                properties.getProperty("password")
        );
    }

    /**
     * Closes the current database connection if one exists.
     */
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Failed to disconnect from database", e);
            }
        }

        connection = null;
    }

    /**
     * Executes the SQL statements found in the specified file (semicolon-delimited).
     *
     * @param sqlFile the file path relative to the classpath
     */
    public void runSQL(String sqlFile) {
        Statement stmt = null;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(classloader.getResourceAsStream(sqlFile)))) {

            connect();
            stmt = connection.createStatement();

            StringBuilder sql = new StringBuilder();
            int ch;

            while ((ch = br.read()) != -1) {
                if ((char) ch == ';') {
                    stmt.executeUpdate(sql.toString());
                    sql.setLength(0); // Clear the buffer
                } else {
                    sql.append((char) ch);
                }
            }

        } catch (SQLException se) {
            logger.error("SQL error while executing " + sqlFile, se);
        } catch (Exception e) {
            logger.error("Failed to run SQL script: " + sqlFile, e);
        } finally {
            disconnect();
        }
    }
}
