package com.sacco.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;

public class LoanPayment extends Payment implements AutoCloseable {

    private long id;
    private double amount;
    private Timestamp DatePaid;
    private boolean Approved;
    private List<LoanPayment> loanPayments = new ArrayList<>();
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet result = null;

    public LoanPayment() {

    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    protected void setId(long id) {
        this.id = id;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public Timestamp getDatePaid() {
        return DatePaid;
    }

    @Override
    public void setDatePaid(Timestamp DatePaid) {
        this.DatePaid = DatePaid;
    }

    @Override
    public boolean isApproved() {
        return Approved;
    }

    @Override
    public void setApproved(boolean Approved) {
        this.Approved = Approved;
    }

    @Override
    protected long recordLoanPayment(long loanID, double amount) throws SQLException {
        this.conn = new Database().getConnection();
        try {
            String sql = "INSERT INTO `loanpayments` (`member_id`, `Amount`, `loan_id`) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, Member.getId());
            stmt.setDouble(2, amount);
            stmt.setLong(3, loanID);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                return -1;
            }
            result = stmt.getGeneratedKeys();
            if (result.next()) {
                setId(result.getLong(1));
                return getId();
            } else {
                return -1;
            }
        } finally {
            close();
        }
    }

    @Override
    public void getOverallPaymentInfo(boolean cleared) throws SQLException {
        String sql;
        this.conn = new Database().getConnection();
        loanPayments.clear();
        sql = "SELECT loanAmount, loanPurpose, Amount, DatePaid FROM loans JOIN loanpayments ON loans.id = loanpayments.loan_id AND loans.member_id = ? AND loanpayments.Approved = ?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
            stmt.setBoolean(2, cleared);
            result = stmt.executeQuery();
            while (result.next()) {
                LoanPayment p = new LoanPayment();
                p.amount = Double.parseDouble(Application.df.format(result.getDouble("Amount")));
                p.DatePaid = result.getTimestamp("DatePaid");
                loanPayments.add(p);
            }
        } finally {
            close();
        }
    }

    public void printMemberLoanPayments(JTextArea jt) throws SQLException {
        jt.setText("");
        if (loanPayments.isEmpty()) {
            getOverallPaymentInfo(true);
        }
        jt.append("AMOUNT \t TIME \n\n");
        for (LoanPayment loanPayment : loanPayments) {
            jt.append(loanPayment.amount + "\t");
            jt.append(loanPayment.DatePaid.toString() + "\t");
            jt.append("\n");
        }
        jt.append("\n");
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
