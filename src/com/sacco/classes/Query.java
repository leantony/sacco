/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacco.classes;

import java.sql.SQLException;

/**
 *
 * @author Antony
 */
public class Query extends Member {

    private int QeryId;
    private String queryContent;

    /**
     * @return the QeryId
     */
    public int getQeryId() {
        return QeryId;
    }

    /**
     * @param QeryId the QeryId to set
     */
    public void setQeryId(int QeryId) {
        this.QeryId = QeryId;
    }

    /**
     * @return the queryContent
     */
    public String getQueryContent() {
        return queryContent;
    }

    /**
     * @param queryContent the queryContent to set
     */
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
            // close resources
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
