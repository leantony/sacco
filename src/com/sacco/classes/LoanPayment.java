package com.sacco.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.login.AccountException;
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
            // close();
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
                p.amount = Double.parseDouble(Utility.DF.format(result.getDouble("Amount")));
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

    private int addExcessToContributions(double excess) throws SQLException {
        if (excess == 0) {
            // just do nothing and return 1
            return 1;
        }
        conn = new Database().getConnection();
        String sql = "INSERT INTO `contributions` (`member_id`, `Amount`, `paymentMethod`, `Approved`) VALUES (?, ?, ?, ?)";
        stmt = conn.prepareStatement(sql);
        stmt.setLong(1, Member.getId());
        stmt.setDouble(2, excess);
        stmt.setString(3, "EXCESS");
        stmt.setBoolean(4, true);
        return stmt.executeUpdate();
    }

    // this shud be calld only after payment >= Loan+interest
    private boolean clearLoan(double excess, long id) throws SQLException {
        String sql = "UPDATE `loans` SET cleared = ? WHERE `id`=?";
        stmt = conn.prepareStatement(sql);
        stmt.setDouble(1, Loan.LOAN_CLEARED);
        stmt.setLong(2, id);
        int rows = stmt.executeUpdate();
        return addExcessToContributions(excess) == rows;
    }

    // _loan payback function
    public boolean PayBackLoan(Loan loan) throws SQLException, AccountException {
        // get the uncleared loans info for current user
        loan.getLoanInfo(Loan.LOAN_NOT_CLEARED, false);
        long l_id = 0;
        double amount_p = 0;
        double t_amnt = 0;
        // fill the variables above
        for (Loan _loan : loan.loanInfo) {
            if (_loan.getId() <= 0) {
                throw new SQLException("A loan id wasn't obtained");
            }
            l_id = _loan.getId();
            amount_p = _loan.getAmountPaid();
            t_amnt = _loan.getTotalAmount();
            break;
        }
        // check the amount paid and total to pay
        if (amount_p >= t_amnt) {
            double excess = amount_p - t_amnt;
            clearLoan(excess, l_id);
            throw new AccountException("You loan is fully paid.\nYour overpayment of ksh " + Utility.DF.format(excess) + " will be added to your contributions");
        } else {
            conn = new Database().getConnection();
            try {
                String sql = "UPDATE `loans` SET `paidAmount`= `paidAmount` + ? WHERE `id`=?";
                stmt = conn.prepareStatement(sql);
                stmt.setDouble(1, loan.getAmountToPay());
                stmt.setLong(2, l_id);
                // record payment in db
                loan.setPaymentID(loan._payment.recordLoanPayment(l_id, loan.getAmountToPay()));
                // update loan paid amnt
                return stmt.executeUpdate() == 1 && loan.getPaymentID() != -1;
            } finally {
                close();
            }
        }
    }

    public double GetLoanTotal(int cleared) throws SQLException {
        String sql;
        conn = new Database().getConnection();
        if (cleared == 0) {
            sql = "SELECT SUM(LoanAmount) FROM loans WHERE cleared = 0";
        } else if (cleared == 1) {
            sql = "SELECT SUM(LoanAmount) FROM loans WHERE cleared = 1";
        } else {
            sql = "SELECT SUM(LoanAmount) FROM loans";
        }
        try {
            stmt = conn.prepareStatement(sql);
            result = stmt.executeQuery();
            if (result.next()) {
                return result.getDouble(1);
            } else {
                throw new SQLException("a sum could not be made");
            }
        } finally {
            close();
        }
    }

    public double getPaymentsTotal() throws SQLException {
        conn = new Database().getConnection();
        String sql = "SELECT SUM(paidAmount) FROM loans";
        try {
            stmt = conn.prepareStatement(sql);
            result = stmt.executeQuery();
            if (result.next()) {
                return result.getDouble(1);
            } else {
                throw new SQLException("a sum could not be made");
            }
        } finally {
            close();
        }
    }

    public double getInterestTotal() throws SQLException {
        conn = new Database().getConnection();
        String sql = "SELECT SUM(TotalAmount) FROM loans WHERE cleared = 1 OR cleared = 0";
        try {
            stmt = conn.prepareStatement(sql);
            result = stmt.executeQuery();
            if (result.next()) {
                return result.getDouble(1);
            } else {
                throw new SQLException("a sum could not be made");
            }
        } finally {
            close();
        }
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
