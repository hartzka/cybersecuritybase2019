package sec.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class for handling database settings.
 */
public class DataBaseManager {

    private String path;
   
    public DataBaseManager() {
    }

    /**
     * Sets up the database variables. Run this before openConnection.
     *
     * @param path
     */
    public void setup(String path) {
        this.path = path;
    }

    /**
     * Sets up the database.
     *
     * @throws SQLException
     */
    public void createTablesIfAbsent() throws SQLException {
        Connection conn = openConnection();
        conn.prepareStatement("create table if not exists Signup ("
                + "  id INTEGER PRIMARY KEY,"
                + "  name VARCHAR(128),"
                + "  address VARCHAR(128)"
                + ");").executeUpdate();
        conn.close();
    }

    /**
     * Get a connection from DriverManager. Run setup before this.
     *
     * @return
     * @throws SQLException
     */
    public Connection openConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(path);
        return conn;
    }
}
