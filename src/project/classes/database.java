/*
 * this class initializes the database DBConnection
 */
package project.classes;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Antony
 */
public class database {

    public Connection DBConnection = null;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";
    // you can change this here
    private static final String USER = "root";
    private static final String PASS = "zx 0Anto9;;";
    // private static final String DB_NAME = "Sacco";

    /**
     * initialize DBConnection
     */
    public database() {
        // load the MySQL driver
        try {
            Class.forName(JDBC_DRIVER);// DBConnection
            DBConnection = DriverManager.getConnection(DB_URL, USER, PASS);
            //System.out.println(getConnect());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fatal Error!. A database connection could not be established");
            Application.Exit(-1);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Fatal Error!. The specified driver could not be loaded");
            Application.Exit(-1);
        }

    }

    /**
     * @return the DBConnection object
     */
    public Connection getConnection() {
        return DBConnection;
    }

    public void CloseDBconnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }
}
