/*
 * contains loan functions
 */
package com.sacco.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.security.auth.login.AccountException;
import javax.swing.JTextArea;

/**
 *
 * @author Antony
 */
public class Loan {

    private static final short LOAN_CLEARED = 1;
    private static final short LOAN_NOT_CLEARED = 0;

    // define loan table datatypes as potrayed by the database
    private long id;
    private double LoanAmount;
    private double TotalAmount;

    // value is in months
    private int PaybackPeriod;
    private String LoanPurpose;
    private String LoanType;

    // amounts
    private double AmountToPay;
    private double AmountPaid;

    // loan constants
    private double LOAN_INTEREST = 30.0;
    PreparedStatement stmt = null;
    Connection conn;
    ResultSet result = null;
    Member m;

    public Loan() {
        this.conn = null;
        this.m = new Member();
        database d = new database();
        conn = d.getConnection();
    }

    // allow the user to know their pending loan amount to pay, after making a payment to their loan
    public double getPendingAmount() {
        return TotalAmount - getAmountPaid();
    }

    /**
     * @return the LoanAmount
     */
    public double getLoanAmount() {
        return LoanAmount;
    }

    /**
     * @param LoanAmount the LoanAmount to set
     */
    public void setLoanAmount(double LoanAmount) {
        this.LoanAmount = LoanAmount;
    }

    /**
     * @return the TotalAmount
     */
    private double getTotalAmount() {
        setTotalAmount();
        return TotalAmount;
    }

    /**
     * @param TotalAmount the TotalAmount to set
     */
    private void setTotalAmount() {
        // interest + 100 * amount
        double interest = this.LoanAmount * getLOAN_INTEREST() * PaybackPeriod / 12;
        this.TotalAmount = interest + this.LoanAmount;
    }

    public double getTotalAmntFromDB() {
        return TotalAmount;
    }

    /**
     * @return the LoanInterest
     */
    protected double getLoanInterest() {
        return getLOAN_INTEREST() / 100;
    }

    /**
     * @param LoanInterest the LoanInterest to set
     * @throws javax.security.auth.login.AccountException
     */
    protected void setLoanInterest(double LoanInterest) throws AccountException {
        this.setLOAN_INTEREST(LoanInterest);
    }

    /**
     * @return the PaybackPeriod
     */
    public int getPaybackPeriod() {
        return PaybackPeriod;
    }

    /**
     * @param PaybackDate the PaybackPeriod to set
     */
    public void setPaybackPeriod(int PaybackDate) {
        this.PaybackPeriod = PaybackDate;
    }

    /**
     * @return the LoanPurpose
     */
    public String getLoanPurpose() {
        return LoanPurpose;
    }

    /**
     * @param LoanPurpose the LoanPurpose to set
     */
    public void setLoanPurpose(String LoanPurpose) {
        this.LoanPurpose = LoanPurpose;
    }

    /**
     * @return the LoanType
     */
    public String getLoanType() {
        return LoanType;
    }

    /**
     * @param LoanType the LoanType to set
     */
    public void setLoanType(String LoanType) {
        this.LoanType = LoanType;
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

    /**
     * @return the AmountToPay
     */
    public double getAmountToPay() {
        return AmountToPay;
    }

    /**
     * @param AmountToPay the AmountToPay to set
     */
    public void setAmountToPay(double AmountToPay) {
        this.AmountToPay = AmountToPay;
    }

    /**
     * @return the AmountPaid
     */
    public double getAmountPaid() {
        return AmountPaid;
    }

    /**
     * @param AmountPaid the AmountPaid to set
     */
    private void setAmountPaid(double AmountPaid) {
        this.AmountPaid = AmountPaid;
    }

    // allow members to request loans
    public long RequestLoan() throws SQLException {
        // a user can only have a single loan as per program specs
        if (GetLoanCount() == 1) {
            return -1;
        }
        try {
            //System.out.println(conn);
            // INSERT INTO `sacco`.`loans` (`member_id`, `LoanType`, `LoanAmount`, `TotalAmount`, `PaybackPeriod`, `LoanPurpose`) VALUES (5, 'tghgfh', 100, 100, '2014-07-20', '1gthytt');
            String sql = "INSERT INTO `sacco`.`loans` (`member_id`, `LoanType`, `LoanAmount`, `TotalAmount`, `PaybackDate`, `LoanPurpose`) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, Member.getId());
            stmt.setString(2, getLoanType());
            stmt.setDouble(3, getLoanAmount());
            stmt.setDouble(4, getTotalAmount());
            stmt.setInt(5, getPaybackPeriod());
            stmt.setString(6, getLoanPurpose());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                // a user wasn't added
                throw new SQLException("The loan couldn't be saved");
            }

            // get the returned inserted id
            result = stmt.getGeneratedKeys();
            if (result.next()) {
                setId(result.getLong(1));
                return getId();
            } else {
                throw new SQLException("The loan couldn't be saved. an ID wasn't obtained");
            }
        } finally {
            // close resources
            close();
        }
    }

    // loan payback function
    public boolean PayBackLoan() throws SQLException, AccountException {
        getLoanInfo();
        // implies a loan id couldn't be found
        if (getId() <= 0) {
            return false;
        }
        try {
            // the user input shouldn't be greater than the total amount
            if (getAmountToPay() > TotalAmount) {
                double x = getAmountToPay() - TotalAmount;
                throw new AccountException("you are trying to pay ksh " + x + " above your total and that's not allowed");
            }

            // a member shouldn't be allowed to pay up above their total. so we bail
            if (getAmountPaid() > TotalAmount) {
                double x = getAmountPaid() - TotalAmount;
                throw new AccountException("You loan is already fully paid. \nYou will have an overpayment of " + x + " .Overpayments aren't allowed");
            }

            // UPDATE `sacco`.`loans` SET `paidAmount`= `paidAmount` + 5000 WHERE `id`=15
            String sql = "UPDATE `sacco`.`loans` SET `paidAmount`= `paidAmount` + ? WHERE  `id`=?";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, getAmountToPay());
            stmt.setLong(2, getId());
            if (TotalAmount == getAmountPaid()) {
                int rows = stmt.executeUpdate();
                // clear the loan so that user's count is updated
                if (clearLoan()) {
                    return rows == 1;
                } else {
                    throw new AccountException("could not clear your loan");
                }

            }
            int rows = stmt.executeUpdate();
            return rows == 1;

        } finally {
            // close resources
            close();
        }
    }

    private boolean clearLoan() throws SQLException {
        try {
            String sql = "UPDATE `sacco`.`loans` SET cleared = ? WHERE `id`=?";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, LOAN_CLEARED);
            stmt.setLong(2, getId());

            int rows = stmt.executeUpdate();
            return rows == 1;

        } finally {
            // close resources
            close();
        }

    }

    // get loan info for current member. change this one coz ka hujanotice you are being redudant
    public void getLoanInfo() throws SQLException {
        // SELECT `id`, `member_id`, `LoanType`, `LoanAmount`, `TotalAmount`, `PaybackDate`, `LoanPurpose`, `paidAmount` FROM `sacco`.`loans` WHERE  `id`=15;
        try {
            String sql = "SELECT * FROM `sacco`.`loans` WHERE  `member_id`=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
            result = stmt.executeQuery();
            if (result.next()) {
                // set the loan id
                setId(result.getLong("id"));
                // get the amount total/ paid
                TotalAmount = result.getDouble("TotalAmount");
                setAmountPaid(result.getDouble("paidAmount"));
            }

        } finally {
            close();
        }
    }

    // the current member should be free to update their payback date
    public boolean ChangeLoanPayBackDate() throws SQLException {
        try {
            //System.out.println(conn);
            // UPDATE `sacco`.`loans` SET `PaybackPeriod`='2015-07-20' WHERE  `id`=1;
            String sql = "UPDATE `sacco`.`loans` SET `PaybackDate`=? WHERE  `id`= ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.PaybackPeriod);
            stmt.setLong(2, getId());

            int rows = stmt.executeUpdate();
            return rows == 0;

        } finally {
            // close resources
            close();
        }
    }

    public int GetLoanCount() throws SQLException {
        try {
            String sql = "SELECT COUNT('member_id') FROM sacco.loans WHERE member_id = ? AND cleared = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
            stmt.setShort(2, LOAN_NOT_CLEARED);
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

    // the current member should also be able to check thir loan status
    public void PrintLoanStatus(JTextArea jt) throws SQLException {
        try {
            double la, pa = 0, ta = 0;
            jt.setText("");
            //System.out.println(conn);
            // SELECT LoanAmount, TotalAmount, PaybackPeriod, LoanType, LoanPurpose FROM sacco.loans WHERE member_id = 5
            String sql = "SELECT member_id, LoanAmount, TotalAmount, PaybackDate, LoanType, LoanPurpose, paidAmount FROM sacco.loans WHERE member_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
            result = stmt.executeQuery();
            jt.append("MEMBER ID \t LOAN_AMOUNT \t TOTAL_AMOUNT \t LOAN_PERIOD \t LOAN_TYPE \t LOAN_PURPOSE \t \t PAID_AMOUNT\n \n");
            while (result.next()) {
                la = result.getDouble("LoanAmount");
                pa = result.getDouble("paidAmount");
                ta = result.getDouble("TotalAmount");
                // display the info
                jt.append(Long.toString(result.getLong("member_id")));
                jt.append("\t");
                jt.append(la + "");
                jt.append("\t\t");
                jt.append(ta + "");
                jt.append("\t\t");
                jt.append(result.getString("PaybackDate"));
                jt.append("\t\t");
                jt.append(result.getString("LoanType"));
                jt.append("\t");
                jt.append(result.getString("LoanPurpose"));
                jt.append("\t\t");
                jt.append(pa + "");
            }
            // display extra info
            jt.append("\n\n");
            jt.append("You have " + GetLoanCount() + " loans to pay up\n");
            jt.append("You owe the sacco ksh " + (ta - pa) + "\n");
            jt.append("You have currently paid ksh " + pa + "\n");
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

    /**
     * @return the LOAN_INTEREST
     */
    private double getLOAN_INTEREST() {
        return LOAN_INTEREST;
    }

    /**
     * @param LOAN_INTEREST the LOAN_INTEREST to set
     * @throws javax.security.auth.login.AccountException
     */
    public void setLOAN_INTEREST(double LOAN_INTEREST) throws AccountException {
        if (Member.isAdmin()) {
            this.LOAN_INTEREST = LOAN_INTEREST / 100;
        } else {
            throw new AccountException("You are not allowed to perform this action");
        }
    }

}
