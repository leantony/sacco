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

public class LoanPayment extends Payment {

    private long id;
    private double amount;
    private Timestamp DatePaid;
    private boolean Approved;
    private List<LoanPayment> loanPayments = new ArrayList<>();
    PreparedStatement stmt = null;
    Connection conn;
    ResultSet result = null;

    public LoanPayment() {
        this.conn = Database.getDBConnection();
    }

    /**
     * @return the id
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    protected void setId(long id) {
        this.id = id;
    }

    /**
     * @return the amount
     */
    @Override
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the DatePaid
     */
    @Override
    public Timestamp getDatePaid() {
        return DatePaid;
    }

    /**
     * @param DatePaid the DatePaid to set
     */
    @Override
    public void setDatePaid(Timestamp DatePaid) {
        this.DatePaid = DatePaid;
    }

    /**
     * @return the Approved
     */
    @Override
    public boolean isApproved() {
        return Approved;
    }

    /**
     * @param Approved the Approved to set
     */
    @Override
    public void setApproved(boolean Approved) {
        this.Approved = Approved;
    }

    /**
     *
     * @param id
     * <p>
     * The member's ID </p>
     * @param amount
     * @return
     * <p>
     * The payment ID </p>
     * @throws SQLException
     */
    @Override
    protected long recordLoanPayment(long id, double amount) throws SQLException {
        try {
            String sql = "INSERT INTO `loanpayments` (`member_id`, `Amount`, `loan_id`) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, Member.getId());
            stmt.setDouble(2, amount);
            stmt.setLong(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                // a user wasn't added
                throw new SQLException("A payment couldn't be made");
            }

            // get the returned inserted id
            result = stmt.getGeneratedKeys();
            if (result.next()) {
                setId(result.getLong(1));
                return getId();
            } else {
                throw new SQLException("The payment couldn't be made. an ID wasn't obtained");
            }
        } finally {
            // close resources
        }
    }

    /**
     *
     * @param cleared
     * @throws SQLException
     */
    @Override
    public void getOverallPaymentInfo(boolean cleared) throws SQLException {
        String sql;
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
        if (loanPayments.isEmpty()) {
            getOverallPaymentInfo(true);
        }
        jt.append("AMOUNT \t DATE \t LOAN");
        for (LoanPayment loanPayment : loanPayments) {
            jt.append(loanPayment.amount + "");
            jt.append(loanPayment.DatePaid.toLocalDateTime() + "");
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
