/*
 * contains loan functions
 */
package project.classes;

import java.sql.*;
import java.util.Formatter;
import javax.swing.JTextArea;

/**
 *
 * @author Antony
 */
public class Loan {

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
    protected double LOAN_INTEREST = 5.5 / 100;
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
        double interest = this.LoanAmount * LOAN_INTEREST * PaybackPeriod / 12;
        this.TotalAmount = interest + this.LoanAmount;
    }

    public double getTotalAmntFromDB() {
        return TotalAmount;
    }

    /**
     * @return the LoanInterest
     */
    protected double getLoanInterest() {
        return LOAN_INTEREST;
    }

    /**
     * @param LoanInterest the LoanInterest to set
     */
    protected void setLoanInterest(double LoanInterest) {
        this.LOAN_INTEREST = LoanInterest;
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

    public long SaveMemberLoan() throws SQLException {
        // a user can only have a single loan as per program specs
        if (MyloanCount() == 1) {
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
    public boolean PayBackLoan() throws SQLException {
        getLoanInfo();
        // implies a loan id couldn't be found
        if (getId() <= 0) {
            return false;
        }
        try {
            // the user input shouldn't be greater than the total amount
            if (getAmountToPay() > TotalAmount) {
                double x = getAmountToPay() - TotalAmount;
                throw new SQLException("you are trying to pay ksh " + x + " above your total and that's not allowed");
            }

            // a member shouldn't be allowed to pay up above their total. so we bail
            if (getAmountPaid() > TotalAmount) {
                double x = getAmountPaid() - TotalAmount;
                throw new SQLException("You loan is already fully paid. You have an overpayment of ksh" + x);
            }

            // UPDATE `sacco`.`loans` SET `paidAmount`= `paidAmount` + 5000 WHERE `id`=15
            String sql = "UPDATE `sacco`.`loans` SET `paidAmount`= `paidAmount + ? WHERE  `id`=?";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, getAmountToPay());
            stmt.setLong(2, getId());
            int rows = stmt.executeUpdate();
            return rows == 0;

        } finally {
            // close resources
            close();
        }
    }

    // get loan id for current member
    public void getLoanInfo() throws SQLException {
        // SELECT `id`, `member_id`, `LoanType`, `LoanAmount`, `TotalAmount`, `PaybackDate`, `LoanPurpose`, `paidAmount` FROM `sacco`.`loans` WHERE  `id`=15;
        try {
            String sql = "SELECT `id`, `LoanAmount`, `TotalAmount`, `LoanPurpose`, `paidAmount` FROM `sacco`.`loans` WHERE  `member_id`=?";
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

    public int MyloanCount() throws SQLException {
        try {
            String sql = "SELECT COUNT('member_id') FROM sacco.loans WHERE member_id = ?";
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

    // the current member should also be able to check thir loan status
    public void checkLoanStatus(JTextArea jt) throws SQLException {
        try {
            //System.out.println(conn);
            // SELECT LoanAmount, TotalAmount, PaybackPeriod, LoanType, LoanPurpose FROM sacco.loans WHERE member_id = 5
            String sql = "SELECT member_id, LoanAmount, TotalAmount, PaybackDate, LoanType, LoanPurpose FROM sacco.loans WHERE member_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
            result = stmt.executeQuery();
            while (result.next()) {
                // display the info
                //Formatter f = new Formatter();
                //jt.append(f.format("%s %s %s %s", "Total", "Period", "Type", "purpose").toString());
                jt.append(Long.toString(result.getLong("member_id")));
                jt.append(Double.toString(result.getDouble("LoanAmount")));
                jt.append(Double.toString(result.getDouble("TotalAmount")));
                jt.append(result.getString("PaybackDate"));

                jt.append(result.getString("LoanType"));
                jt.append(result.getString("LoanPurpose"));

            }
            // display extra info
            jt.append("\n\n\n");
            jt.append("*********************************************");
            jt.append("You have " + MyloanCount() + " loans");
        } finally {
            close();
        }
    }

    protected boolean DestroyMemberLoan(long id) throws SQLException {
        try {
            //System.out.println(conn);
            String sql = "DELETE FROM sacco.Loans WHERE member_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            // PreparedStatement.setString(8, getPassword());
            int rows = stmt.executeUpdate();
            return rows == 0;

        } finally {
            // close resources
            close();
        }
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
}
