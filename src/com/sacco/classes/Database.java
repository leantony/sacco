/*
 * this class initializes the Database DBConnection
 */
package com.sacco.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Database implements AutoCloseable {

    private Connection DBConnection = null;
    private static Connection conn = null;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    // DB details
    private static final String HOST = "192.168.4.1";
    private static final String USER = "sacco";
    private static final String DB_NAME = "sacco";
    private static final String PASS = "sacco";

    private static final String DB_URL = "jdbc:mysql://" + HOST + "/" + DB_NAME + "";

    public Database() {
        try {
            // load the mysql jdbc driver
            Class.forName(JDBC_DRIVER);
            // create connection
            DBConnection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fatal Error!. A database connection could not be established", "Error", JOptionPane.ERROR_MESSAGE);
            Utility.Exit(-1);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Fatal Error!. The specified driver could not be loaded", "Error", JOptionPane.ERROR_MESSAGE);
            Utility.Exit(-1);
        }

    }

    public Connection getConnection() {
        if (DBConnection == null) {
            return new Database().DBConnection;
        }
        return DBConnection;
    }

    // static connection initializer, returns an open connection
    public static Connection getDBConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return conn;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fatal Error!. A database connection could not be established", "Error", JOptionPane.ERROR_MESSAGE);
            Utility.Exit(-1);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Fatal Error!. The specified driver could not be loaded", "Error", JOptionPane.ERROR_MESSAGE);
            Utility.Exit(-1);
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        if (DBConnection != null) {
            try {
                DBConnection.close();
            } catch (SQLException e) {
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }
}
