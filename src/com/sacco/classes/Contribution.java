package com.sacco.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTextArea;

public class Contribution {

    private long id;
    private int amount;
    private String paymentMethod;
    // contribution constants which should only be changed by the admin
    private static double MIN_CONTRIBUTION = 1000;
    private static double MAX_CONTRIBUTION = 1000000;
    Member _member;

    PreparedStatement stmt = null;
    Connection conn;
    ResultSet result = null;

    public Contribution() {
        this.conn = null;
        this._member = new Member();
        conn = new Database().getConnection();
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public static double getContributionMin() {
        return MIN_CONTRIBUTION;
    }

    public static void setContributionMin(double min) {
        MIN_CONTRIBUTION = min;
    }

    public static double getMaxContribution() {
        return MAX_CONTRIBUTION;
    }

    public static void setMaxContribution(double max) {
        MAX_CONTRIBUTION = max;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public long makeContribution() throws SQLException {
        try {
            String sql = "INSERT INTO `contributions` (`member_id`, `Amount`, `paymentMethod`) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, Member.getId());
            stmt.setDouble(2, getAmount());
            stmt.setString(3, getPaymentMethod());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("The contribution wasn't submitted successfully");
            }
            result = stmt.getGeneratedKeys();
            if (result.next()) {
                setId(result.getLong(1));
                return getId();
            } else {
                throw new SQLException("The contribution couldn't be saved. an ID wasn't obtained");
            }
        } finally {
            close();
        }
    }

    public double getAvgContributions(long id, int ApprovedStatus) throws SQLException {
        if (!Member.isAdmin()) {
            id = Member.getId();
        }
        String sql;
        if (ApprovedStatus == 0) {
            sql = "SELECT AVG(Amount) FROM contributions WHERE member_id = ? AND Approved = 0";
        } else {
            sql = "SELECT AVG(Amount) FROM contributions WHERE member_id = ? AND Approved = 1";
        }
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
            result = stmt.executeQuery();
            double avg;
            if (result.next()) {
                avg = result.getDouble(1);
                return avg;
            } else {
                throw new SQLException("could not determine the contributions average");
            }
        } finally {
            close();
        }
    }

    public double getTotalContributions(long id, int ApprovedStatus) throws SQLException {
        if (!Member.isAdmin()) {
            id = Member.getId();
        }
        String sql;
        if (ApprovedStatus == 0) {
            sql = "SELECT SUM(Amount) FROM contributions WHERE member_id = ? AND Approved = 0";
        } else {
            sql = "SELECT SUM(Amount) FROM contributions WHERE member_id = ? AND Approved = 1";
        }
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
            result = stmt.executeQuery();
            double avg;
            if (result.next()) {
                avg = result.getDouble(1);
                return avg;
            } else {
                throw new SQLException("could not determine the contributions average");
            }
        } finally {
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
            close();
        }
    }

    public void ViewAllMemberContribuitons(JTextArea jt) throws SQLException {
        jt.setText("");
        try {
            String sql = "SELECT * FROM contributions WHERE member_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
            result = stmt.executeQuery();
            jt.append("Here are all your contributions\n\n");
            jt.append("AMOUNT(KSH)\t\tPAYMENT_METHOD\t\tDATE_CONTRIBUTED\t\tAPPROVED\n\n");
            while (result.next()) {
                jt.append(Application.df.format(result.getDouble("Amount")));
                jt.append("\t\t");
                jt.append(result.getString("paymentMethod"));
                jt.append("\t\t\t");
                jt.append(result.getTimestamp("DateOfContribution").toString());
                jt.append("\t\t");
                if (result.getBoolean("Approved")) {
                    jt.append("Yes");
                } else {
                    jt.append("No");
                }
                jt.append("\n");
            }
            jt.append("\n");
            jt.append("==================================================================================\n\n");
            jt.append("You have currently contributed " + getMemberContributions(3) + " times (We only count the ones appoved, including excess)\n");
            jt.append("Your Averege contributions are Ksh " + Application.df.format(getAvgContributions(Member.getId(), 1)) + "\n");
            jt.append("Your total contributions are Ksh " + getTotalContributions(Member.getId(), 1) + " (Only Approved ones)\n");
            jt.append("AMOUNT  ==> represents the amount(s) you've contributed\n");
            jt.append("PAYMENT_METHOD ==> method you chose to pay the contribution\n");
            jt.append("EXCESS in the payment method refers to overpayments which were added from your paid loans\n");
            jt.append("EXCESS payments are added automatically when you overpay a loan\n");
            jt.append("APPROVED  ==> contribution approved by the sacco or not\n\n");
            jt.append("==================================================================================\n");
        } finally {
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
}
