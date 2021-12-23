package fr.kizyow.bot.database;

import fr.kizyow.bot.configurations.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

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
     * Query through the database with a request
     *
     * @param query The SQL request
     * @return A ResultSet
     */
    public static ResultSet executeQuery(String query) {

        if (isConnected()) {

            try {

                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                return statement.executeQuery(query);

            } catch (SQLException e) {
                logger.error("An error occurred while trying to query a request to the database", e);
            }

        }

        return null;

    }

    /**
     * Query through the database with a prepared request
     *
     * @param query      The prepared SQL request
     * @param parameters The parameters of the request
     * @return A ResultSet
     */
    public static ResultSet executePreparedQuery(String query, Object... parameters) {

        if (isConnected()) {

            try {

                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                for (int i = 1; i <= parameters.length; i++) {
                    Object parameter = parameters[i - 1];
                    preparedStatement.setObject(i, parameter);
                }

                return preparedStatement.executeQuery();

            } catch (SQLException e) {
                logger.error("An error occurred while trying to query a prepared request to the database", e);
            }

        }

        return null;

    }

    /**
     * Update multiples lines in the database
     *
     * @param request The SQL request
     * @return The number of lines updated
     */
    public static int execute(String request) {

        if (isConnected()) {

            try {

                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                return statement.executeUpdate(request);

            } catch (SQLException e) {
                logger.error("An error occurred while trying to update a request to the database", e);
            }

        }

        return -1;

    }

    /**
     * Update multiples lines in the database
     *
     * @param request    The prepared SQL request
     * @param parameters The parameters of the request
     * @return The numbers of lines updated
     */
    public static int executePreparedUpdate(String request, Object... parameters) {

        if (isConnected()) {

            try {

                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(request);
                for (int i = 1; i <= parameters.length; i++) {
                    Object parameter = parameters[i - 1];
                    preparedStatement.setObject(i, parameter);
                }

                return preparedStatement.executeUpdate();

            } catch (SQLException e) {
                logger.error("An error occurred while trying to query a prepared update to the database", e);
            }

        }

        return -1;

    }

    /**
     * Check if a table exists or not
     *
     * @param table The table name
     * @return a boolean
     */
    public static boolean tableExist(String table) {

        ResultSet resultSet = executePreparedQuery(
                "SELECT EXISTS(SELECT 1 FROM information_schema.tables WHERE table_name = ?)",
                table);

        try {
            return resultSet != null && resultSet.next() && resultSet.getBoolean(1);
        } catch (SQLException e) {
            logger.error("An error occurred while checking if a table exist in the database", e);
            return false;
        }

    }

    /**
     * Get the connection of the Database directly
     *
     * @return A Connection to the Database
     */
    public static Connection getConnection() {
        return Database.getInstance().connection;
    }

    /**
     * Check if we're connected to the database and the Connection is still valid
     *
     * @return a boolean
     */
    public static boolean isConnected() {

        try {
            Connection connection = getConnection();
            return connection != null && !connection.isClosed() && connection.isValid(5);
        } catch (SQLException e) {
            logger.error("An error occurred while checking the connection to the database", e);
            return false;
        }

    }

    public static void closeInstance() {

        if (isConnected()) {
            Connection connection = getConnection();
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            instance = null;
        }

    }

    private static Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

}
