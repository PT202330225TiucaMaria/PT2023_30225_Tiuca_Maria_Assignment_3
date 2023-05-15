package Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This class provides a factory for creating database connections and managing resources.
 * It uses the singleton design pattern to ensure only one instance of the factory is created.
 *
 * TODO: Add specific details about the purpose and functionality of this class.
 *
 * Usage:
 * - To get a database connection, use the getConnection() method.
 * - To close a connection, statement, or result set, use the close() methods.
 *
 * Note: Make sure to configure the database connection details in the constants.
 *
 * @author Tiuca Maria
 */
public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/ordersmanagement";
    private static final String USER = "root";
    private static final String PASS = "HarryPotter04";
    private static ConnectionFactory singleInstance = new ConnectionFactory();
    /**
     * Constructs a new ConnectionFactory object.
     * It registers the JDBC driver.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates a new database connection.
     *
     * @return The created Connection object.
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }
    /**
     * Retrieves a database connection.
     *
     * @return The Connection object.
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }
    /**
     * Closes a database connection.
     *
     * @param connection The Connection object to close.
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }
    /**
     * Closes a statement.
     *
     * @param statement The Statement object to close.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }
    /**
     * Closes a result set.
     *
     * @param resultSet The ResultSet object to close.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }
}
