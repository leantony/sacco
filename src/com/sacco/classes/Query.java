package com.sacco.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {

    private int QeryId;
    private String queryContent;
    PreparedStatement stmt = null;
    Connection conn;
    ResultSet result = null;

    public Query() {
        this.conn = Database.getDBConnection();
    }

    public int getQeryId() {
        return QeryId;
    }

    public void setQeryId(int QeryId) {
        this.QeryId = QeryId;
    }

    public String getQueryContent() {
        return queryContent;
    }

    public void setQueryContent(String queryContent) {
        this.queryContent = queryContent;
    }

    public boolean makeQuery() throws SQLException {
        try {
            String sql = "INSERT INTO `sacco`.`queries` (`query`, `member_id`) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, getQueryContent());
            stmt.setLong(2, Member.getId());
            int rows = stmt.executeUpdate();
            return rows == 1;
        } finally {
            close();
        }
    }

    public boolean ClearQuery() {
        return false;
    }

    private void close() {
        if (result != null) {
            try {
                result.close();
            } catch (SQLException e) {
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
            }
        }
    }
}
