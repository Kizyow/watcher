package fr.kizyow.bot.database;

import fr.kizyow.bot.configurations.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection connection;

    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    private static Database instance;

    /**
     * Connect to the database
     */
    private Database() {

        logger.info("Loading database instance...");
        ConfigManager configManager = new ConfigManager();
        DatabaseCredentials databaseCredentials = configManager.loadConfig("database.yml", DatabaseCredentials.class);

        try {
            this.connection = DriverManager.getConnection(databaseCredentials.getURI(), databaseCredentials.getUsername(), databaseCredentials.getPassword());
            logger.info("The connection has been successfully established with the database");
        } catch (SQLException e) {
            logger.error("An error occurred while trying to connect to the database", e);
        }

    }

    /**
     * Get the connection of the Database directly
     * @return A Connection to the Database
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Check if we're connected to the database and the Connection is still valid
     * @return a boolean
     */
    public boolean isConnected(){

        try {
            return connection != null && !connection.isClosed() && connection.isValid(5);
        } catch (SQLException e) {
            logger.error("An error occurred while checking the connection to the database", e);
            return false;
        }

    }

    /**
     * Get the Database instance
     * @return An Database object
     */
    public static Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

}
