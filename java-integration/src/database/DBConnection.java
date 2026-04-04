package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Handler
 * Manages JDBC connection
 */
public class DBConnection {

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/academic_management";
    private static final String USER = "";
    private static final String PASS = ""; // Update if password exists

    private static Connection connection = null;

    /**
     * Get database connection
     * Creates new connection if not exists
     */
    public static Connection getConnection() throws SQLException {
        try {

            // Create new connection if null
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
                System.out.println("✓ Database connection established!");
            }
            return connection;

        } catch (SQLException e) {
            System.err.println("ERROR: Database connection failed!");
            throw e;
        }
    }

    /*
     * Close database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed!");
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Failed to close connection!");
            e.printStackTrace();
        }
    }
}
