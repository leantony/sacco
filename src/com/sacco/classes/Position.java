/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacco.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Antony
 */
public class Position implements AutoCloseable {

    private int id;
    private String name;

    // for Database stuff
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet result = null;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public long getPositionId(String posname) throws SQLException {
        this.conn = new Database().getConnection();
        try {
            String sql = "SELECT `id` FROM positions WHERE `name` LIKE ? LIMIT 1";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + posname + "%");
            result = stmt.executeQuery();
            if (result.next()) {
                setId(result.getInt("id"));
                return getId();
            }
        } finally {
            close();
        }
        return -1;
    }

    public int checkMemberPosition(long memberID, boolean anyMember) throws SQLException {
        if (!Member.isAdmin() || !anyMember) {
            memberID = Member.getId();
        }
        conn = new Database().getConnection();
        try {
            String sql = "SELECT id FROM positions JOIN members_positions ON positions.id = members_positions.position_id AND member_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, memberID);
            result = stmt.executeQuery();
            while (result.next()) {
                if (result.getInt("id") == Admin.ADMIN_POSITION_ID) {
                    Member.setAdmin(true);
                    return 1;
                }
                if (result.getInt("id") == Secretary.SEC_POSITION_ID) {
                    return 2;
                } else {
                    return result.getInt("id");
                }
            }
        } finally {
            close();
        }
        return -1;
    }

    @Override
    public void close() {
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
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
            }
        }
    }
}
