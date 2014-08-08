package com.sacco.classes;

import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.security.auth.login.AccountException;
import javax.swing.JTextArea;

public class Loan {

    private static final short LOAN_CLEARED = 1;
    private static final short LOAN_NOT_CLEARED = 0;
    // contribution values which can only be changed by the admin
    private static double MIN_LOAN = 10000;
    private static double MAX_LOAN = 1000000;
    private static final double MAX_ALLOWED_PAYMENT = 1000000;
    private static final double MIN_ALLOWED_PAYMENT = 1000;

    /**
     * @return the MAX_ALLOWED_PAYMENT
     */
    public static double getMAX_ALLOWED_PAYMENT() {
        return MAX_ALLOWED_PAYMENT;
    }

    /**
     * @return the MIN_ALLOWED_PAYMENT
     */
    public static double getMIN_ALLOWED_PAYMENT() {
        return MIN_ALLOWED_PAYMENT;
    }
    // format the cas
    DecimalFormat df = new DecimalFormat("#.##");

    /**
     *
     * @return
     */
    public static double getMIN_LOAN() {
        return MIN_LOAN;
    }

    /**
     *
     * @param min
     */
    public static void setMinLoanAmount(double min) {
        MIN_LOAN = min;
    }

    /**
     *
     * @return
     */
    public static double getMaxLoanAmount() {
        return MAX_LOAN;
    }

    /**
     *
     * @param max
     */
    public static void setMaxLoanAmount(double max) {
        MAX_LOAN = max;
    }
    Contribution c = new Contribution();

    // define loan table datatypes as potrayed by the Database
    private long id;
    private long paymentID;
    private double LoanAmount;
    private double TotalAmount; // loanAmount + interest

    // value is in months
    private int PaybackPeriod;
    private String LoanPurpose;
    private String LoanType;

    // amounts
    private double AmountToPay; // from the user
    private double AmountPaid; // from db

    // loan constants
    private static double LOAN_INTEREST;
    PreparedStatement stmt = null;
    Connection conn;
    ResultSet result = null;
    Member m;

    /**
     * instatiating the loan class by establishing a db connection, getting the
     * loan interest and instatiating the member class
     */
    public Loan() {
        this.conn = Database.getDBConnection();
        this.m = new Member();

        try {
            Loan.LOAN_INTEREST = getLoanInterest();
        } catch (SQLException ex) {
            // if we get an error, default to 5
            Loan.LOAN_INTEREST = 5;
        }
    }

    // allow the user to know their pending loan amount to pay, after making a payment to their loan
    public double getPendingAmount() {
        return getTotalAmount() - getAmountPaid();
    }

    public double getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(double LoanAmount) {
        this.LoanAmount = LoanAmount;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public final double getLoanInterest() throws SQLException {
        try {
            String sql = "SELECT value FROM settings WHERE name = 'interest'";
            stmt = conn.prepareStatement(sql);
            result = stmt.executeQuery();
            if (result.next()) {
                return result.getDouble("value");
            }

        } finally {
            // close resources
            close();
        }
        // default to 5 if an error occurs
        return 5;
    }

    // get the total amount a loan shud have, using the formula I=PRT, A = I+P
    private void setTotalAmount() {
        double interest = this.LoanAmount * LOAN_INTEREST / 100 * PaybackPeriod / 12;
        this.setTotalAmount(interest + this.LoanAmount);
    }

    public int getPaybackPeriod() {
        return PaybackPeriod;
    }

    public void setPaybackPeriod(int PaybackDate) {
        this.PaybackPeriod = PaybackDate;
    }

    public String getLoanPurpose() {
        return LoanPurpose;
    }

    public void setLoanPurpose(String LoanPurpose) {
        this.LoanPurpose = LoanPurpose;
    }

    public String getLoanType() {
        return LoanType;
    }

    public void setLoanType(String LoanType) {
        this.LoanType = LoanType;
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public double getAmountToPay() {
        return AmountToPay;
    }

    public void setAmountToPay(double AmountToPay) {
        this.AmountToPay = AmountToPay;
    }

    public double getAmountPaid() {
        return AmountPaid;
    }

    private void setAmountPaid(double AmountPaid) {
        this.AmountPaid = AmountPaid;
    }

    public long getPaymentID() {
        return paymentID;
    }

    private void setPaymentID(long paymentID) {
        this.paymentID = paymentID;
    }

    // allow members to request loans
    public long RequestLoan() throws SQLException, AccountException {
        // a user can only apply for a loan if they do't have any pending loans. so we get the count of their uncleared loans
        // in the db, a loan is uncleared if it's cleared status is 0. implies it hasn't been fully paid
        if (GetLoanCount(LOAN_NOT_CLEARED) == 1) {
            throw new AccountException("You have pending loans to pay. Please clear them first to be able to continue");
        }
        // a member should only apply for a loan if they've at least contributed once. 
        // so we count their approved contributions, and if they are 0 we alert them
        if (c.getMemberContributions(1) == 0) {
            throw new AccountException("You are not elligible to apply for a loan since youve not yet contributed to the sacco");
        }
        try {
            // set their total payment
            setTotalAmount();
            String sql = "INSERT INTO `loans` "
                    + "(`member_id`, `LoanType`, `LoanAmount`, "
                    + "`TotalAmount`, `PaybackDate`, `LoanPurpose`) "
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
        // only an uncleared loan should be the one a member should pay for
        getLoanInfo(LOAN_NOT_CLEARED);
        // implies a loan id couldn't be found
        if (getId() <= 0) {
            throw new SQLException("A loan id wasn't obtained");
        }

        // a member shouldn't be allowed to pay up above their total. so we notify them if that happens as they keep paying
        if (getAmountPaid() >= getTotalAmount()) {
            // get their excess payment
            double excess = getAmountPaid() - getTotalAmount();
            // clear the loan, since now the user has either overpaid or has equally paid the loan fully
            clearLoan(excess);
            // not really an indication of an error, but since the function return T/F this is the best way to do so
            throw new AccountException("You loan is now fully paid. \nYour overpayment of ksh " + excess + " will be added to your contributions");
        } else {
            // implies that the user hasn't paid enough, so we allow them to pay up
            try {
                String sql = "UPDATE `loans` SET `paidAmount`= `paidAmount` + ? WHERE  `id`=?";
                stmt = conn.prepareStatement(sql);
                stmt.setDouble(1, getAmountToPay());
                stmt.setLong(2, getId());
                // record payment
                int rows = stmt.executeUpdate();
                setPaymentID(recordPayment(getId()));
                return rows == 1;

            } finally {
                // close resources
                close();
            }
        }
    }

    // this shud only occur after payment >= Loan+interest
    /**
     *
     * @param excess
     * @return
     * @throws SQLException
     */
    protected boolean clearLoan(double excess) throws SQLException {
        try {
            // no need to record a contribution if the excess is 0
            String sql = "UPDATE `loans` SET cleared = ? WHERE `id`=?";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, LOAN_CLEARED);
            stmt.setLong(2, getId());
            int rows = stmt.executeUpdate();
            return addExcessToContributions(excess) == rows;

        } finally {
            // close resources
            close();
        }
    }

    private int addExcessToContributions(double excess) throws SQLException {
        String sql = "INSERT INTO `contributions` (`member_id`, `Amount`, `paymentMethod`, `Approved`) VALUES (?, ?, ?, ?)";
        stmt = conn.prepareStatement(sql);
        stmt.setLong(1, Member.getId());
        stmt.setDouble(2, excess);
        stmt.setString(3, "EXCESS");
        stmt.setBoolean(4, true);
        int rows = stmt.executeUpdate();
        return rows;
    }
// record any loan payments, in a loan payments table to allow members to see them

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    protected long recordPayment(long id) throws SQLException {
        try {
            String sql = "INSERT INTO `loanpayments` (`member_id`, `Amount`, `loan_id`) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, Member.getId());
            stmt.setDouble(2, getLoanAmount());
            stmt.setLong(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                // a user wasn't added
                throw new SQLException("A payment couldn't be made");
            }

            // get the returned inserted id
            result = stmt.getGeneratedKeys();
            if (result.next()) {
                setPaymentID(result.getLong(1));
                return getPaymentID();
            } else {
                throw new SQLException("The payment couldn't be made. an ID wasn't obtained");
            }
        } finally {
            // close resources
        }
    }

    // get loan info for current member. change this one coz ka hujanotice you are being redudant
    /**
     *
     * @param cleared
     * @throws SQLException
     */
    public void getLoanInfo(int cleared) throws SQLException {
        String sql;
        if (cleared == 0) {
            sql = "SELECT * FROM `loans` WHERE member_id = ? AND cleared = 0";
        } else if (cleared == 1) {
            sql = "SELECT * FROM `loans` WHERE member_id = ? AND cleared = 1";
        } else {
            sql = "SELECT * FROM `loans` WHERE member_id = ? AND cleared = 1 OR cleared = 0";
        }
        // SELECT `id`, `member_id`, `LoanType`, `LoanAmount`, `TotalAmount`, `PaybackDate`, `LoanPurpose`, `paidAmount` FROM `sacco`.`loans` WHERE  `id`=15;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
            result = stmt.executeQuery();
            if (result.next()) {
                // set the loan id
                setId(result.getLong("id"));
                // get the amount total/ paid
                setLoanType(result.getString("LoanType"));
                setLoanAmount(result.getDouble("LoanAmount"));
                setTotalAmount(result.getDouble("TotalAmount"));
                setAmountPaid(result.getDouble("paidAmount"));
            }

        } finally {
            close();
        }
    }

    /**
     *
     * @param cleared
     * <p>
     * 0 = gets the count of all uncleared loans for the member </p>
     * <p>
     * 1 = get the count of all cleared loans for the member </p>
     * <p>
     * any = get the count of all cleared or uncleared loans for the member </p>
     * @return
     * <p>
     * A count of the loans </p>
     * @throws SQLException
     */
    public int GetLoanCount(int cleared) throws SQLException {
        //JOptionPane.showMessageDialog(null, Member.getId());
        String sql;
        if (cleared == 0) {
            sql = "SELECT COUNT('member_id') FROM loans WHERE member_id = ? AND cleared = 0";
        } else if (cleared == 1) {
            sql = "SELECT COUNT('member_id') FROM loans WHERE member_id = ? AND cleared = 1";
        } else {
            sql = "SELECT COUNT('member_id') FROM loans WHERE member_id = ? AND cleared = 1 OR cleared = 0";
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

    // the current member should also be able to check thir loan status
    public void PrintLoanStatus(JTextArea jt, int cleared) throws SQLException {
        String sql;
        if (cleared == 0) {
            sql = "SELECT * FROM loans WHERE member_id = ? AND cleared = 0";
        } else if (cleared == 1) {
            sql = "SELECT * FROM loans WHERE member_id = ? AND cleared = 1";
        } else {
            sql = "SELECT * FROM loans WHERE member_id = ? AND cleared = 1 OR cleared = 0";
        }
        try {
            double la, pa = 0, ta = 0;
            java.sql.Date d = Date.valueOf(LocalDate.now());
            jt.setText("");
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
            result = stmt.executeQuery();
            jt.append("LOAN_AMOUNT \t TOTAL_AMOUNT \t LOAN_PERIOD \t LOAN_TYPE \t\t PAID_AMOUNT\n \n");
            while (result.next()) {
                la = Double.parseDouble(df.format(result.getDouble("LoanAmount")));
                pa = Double.parseDouble(df.format(result.getDouble("paidAmount")));
                ta = Double.parseDouble(df.format(result.getDouble("TotalAmount")));
                d = result.getDate("DateSubmitted");
                // display the info
                jt.append(la + "");
                jt.append("\t\t");
                jt.append(ta + "");
                jt.append("\t\t");
                jt.append(result.getString("PaybackDate"));
                jt.append("\t\t");
                jt.append(result.getString("LoanType"));
                jt.append("\t\t");
                jt.append(pa + "\n");
            }
            // display extra info
            jt.append("\n");
            jt.append("==============================================================================\n\n");
            jt.append("You currently have " + GetLoanCount(cleared) + " loans\n");
            jt.append("Regarding your current loan, you've paid ksh " + pa + " since " + d.toLocalDate().format(DateTimeFormatter.ISO_DATE) + "\n");
            jt.append("You owe the sacco ksh " + (ta - pa) + ". \t Note: A negative value indicates an overpayment\n");
            jt.append("==============================================================================\n\n");
            jt.append("LOAN_AMOUNT  ==> represents the amount(s) you took as a loan\n");
            jt.append("TOTAL_AMOUNT ==> calculated as; loan x interestRate x time\n");
            jt.append("PAID_AMOUNT  ==> represents the amount you paid for each loan\n");
            jt.append("LOAN_PERIOD  ==> Time (months) you chose to fulfil your loan payment");
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
     * @param TotalAmount the TotalAmount to set
     */
    private void setTotalAmount(double TotalAmount) {
        this.TotalAmount = TotalAmount;
    }
}
