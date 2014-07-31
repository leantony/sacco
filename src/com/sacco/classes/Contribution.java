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
import java.sql.Statement;

/**
 *
 * @author Antony
 */
public class Contribution {

    // contribution constants which can only be changed by the admin
    private static double MIN_CONTRIBUTION = 1000;
    private static double MAX_CONTRIBUTION = 1000000;

    /**
     * @return the MIN_CONTRIBUTION
     */
    public static double getContributionMin() {
        return MIN_CONTRIBUTION;
    }

    /**
     * @param min
     */
    public static void setContributionMin(double min) {
        MIN_CONTRIBUTION = min;
    }

    /**
     * @return the MAX_CONTRIBUTION
     */
    public static double getMaxContribution() {
        return MAX_CONTRIBUTION;
    }

    /**
     * @param max
     */
    public static void setMaxContribution(double max) {
        MAX_CONTRIBUTION = max;
    }

    // member contributions
    private long id;
    private int amount;
    private String paymentMethod;
    Member m;

    PreparedStatement stmt = null;
    Connection conn;
    ResultSet result = null;

    public Contribution() {
        this.conn = null;
        this.m = new Member();
        database d = new database();
        conn = d.getConnection();
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return the paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     *
     * @return @throws SQLException
     */
    public long makeContribution() throws SQLException {
        try {
            // INSERT INTO `sacco`.`contributions` (`member_id`, `Amount`, `paymentMethod`, `Approved`) VALUES (6, 5000, 'cash', 'yes');
            String sql = "INSERT INTO `sacco`.`contributions` (`member_id`, `Amount`, `paymentMethod`) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, Member.getId());
            stmt.setDouble(2, getAmount());
            stmt.setString(3, getPaymentMethod());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                // a contribution wasn't made
                throw new SQLException("The contribution wasn't submitted successfully");
            }

            // get the returned inserted id
            result = stmt.getGeneratedKeys();
            if (result.next()) {
                setId(result.getLong(1));
                return getId();
            } else {
                throw new SQLException("The contribution couldn't be saved. an ID wasn't obtained");
            }
        } finally {
            // close resources
            close();
        }
    }

    public int getMemberContributions(int ApprovedStatus) throws SQLException {
        String sql;
        if (ApprovedStatus == 0) {
            sql = "SELECT COUNT('member_id') FROM contributions WHERE member_id = ? AND Approved = 0";
        } else {
            sql = "SELECT COUNT('member_id') FROM contributions WHERE member_id = ? AND Approved = 1";
        }
        try {

            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());

            result = stmt.executeQuery();
            int rows;
            if (result.next()) {
                rows = result.getInt(1);
                return rows;
            } else {
                throw new SQLException("a count could not be made");
            }

        } finally {
            // close resources
            close();
        }
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

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    private void setId(long id) {
        this.id = id;
    }

}
