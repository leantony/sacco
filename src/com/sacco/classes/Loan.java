package com.sacco.classes;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.login.AccountException;
import javax.swing.JTextArea;

public class Loan implements AutoCloseable {

    // define loan table datatypes as potrayed by the Database
    private long id;
    private long paymentID;
    private double LoanAmount;
    private double TotalAmount; // loanAmount + interest
    // value is entered as months but displayed as years internally
    private double PaybackPeriod;
    private String LoanPurpose;
    private String LoanType;
    private Timestamp DateSubmitted;
    public List<Loan> loanInfo = new ArrayList();
    // amounts
    private double AmountToPay; // from the user
    private double AmountPaid; // from db
    // loan constants
    private static double LOAN_INTEREST;
    public static final short LOAN_CLEARED = 1;
    public static final short LOAN_NOT_CLEARED = 0;
    private boolean LOAN_APPROVED = false;
    private static double MIN_LOAN = 5000;
    private static double MAX_LOAN = 1000000;
    private static final double MAX_ALLOWED_PAYMENT = 1000000;
    private static final double MIN_ALLOWED_PAYMENT = 1000;

    Contribution _contribution;
    PreparedStatement stmt = null;
    Connection conn;
    ResultSet result = null;
    Member m;
    LoanPayment _payment;

    public Loan() {
        this._contribution = new Contribution();
        this.m = new Member();
        this._payment = new LoanPayment();
        try {
            Loan.LOAN_INTEREST = getLoanInterestFromDB();
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

    public static double getLoanInterest() {
        return Loan.LOAN_INTEREST;
    }

    public final double getLoanInterestFromDB() throws SQLException {
        try {
            this.conn = new Database().getConnection();
            String sql = "SELECT value FROM settings WHERE name = 'interest'";
            stmt = conn.prepareStatement(sql);
            result = stmt.executeQuery();
            if (result.next()) {
                return result.getDouble("value");
            } else {
                return -1;
            }
        } finally {
            close();
        }
    }

    public double getPaybackPeriod() {
        return PaybackPeriod;
    }

    public void setPaybackPeriod(double PaybackDate) {
        this.PaybackPeriod = PaybackDate / 12; // convert to years
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

    public List<Loan> getLoanList() {
        return loanInfo;
    }

    public void setPaymentID(long paymentID) {
        this.paymentID = paymentID;
    }

    public static double MAX_ALLOWED_PAYMENT() {
        return MAX_ALLOWED_PAYMENT;
    }

    public static double MIN_ALLOWED_PAYMENT() {
        return MIN_ALLOWED_PAYMENT;
    }

    public static double MIN_LOAN() {
        return MIN_LOAN;
    }

    public static void setMinLoanAmount(double min) {
        MIN_LOAN = min;
    }

    public static double getMaxLoanAmount() {
        return MAX_LOAN;
    }

    public static void setMaxLoanAmount(double max) {
        MAX_LOAN = max;
    }

    public Timestamp getDateSubmitted() {
        return DateSubmitted;
    }

    private void setTotalAmount(double loanAmnt) {
        // I = P * R * T
        double arate = LOAN_INTEREST * 12 / 100;
        double i = loanAmnt * arate * getPaybackPeriod();
        this.TotalAmount = i + loanAmnt;
//        double payment = loanAmnt * Math.pow((1 + arate), getPaybackPeriod());
//        this.loanAmnt = payment + loanAmnt;
    }

    public double GetAllowedLoan() throws AccountException, SQLException {
        // we determine their loan amnt based on average contributions and how many times they have validly contributed
        double AvgContribution = _contribution.getAvgContributions(Member.getId(), 2);
        int contributionCount = _contribution.getMemberContributions(1);
        // a member should only apply for a loan if they've at least contributed once. 
        // so we count their approved contributions, and if they are 0 we alert them
        if (contributionCount == 0) {
            throw new AccountException("You are not elligible to apply for a loan since youve not yet contributed to the sacco");
        }
        // a user can only apply for a loan if they do't have any pending loans. so we get the count of their uncleared loans
        // in the db, a loan is uncleared if it's cleared status is 0. implies it hasn't been fully paid
        if (GetMemberLoanCount(LOAN_NOT_CLEARED, false) >= 1) {
            throw new AccountException("Please wait for your submitted loan to be approved");
        }
        double multiplier;
        // at least 1.5x
        if (AvgContribution >= 5000 && contributionCount <= 5) {
            multiplier = 1.5;
            return multiplier * AvgContribution;
        } else {
            // max loan should be 3x their contributions
            multiplier = 3.0;
            return multiplier * AvgContribution;
        }
    }

    // allow members to request loans
    public long GetLoan(double amount) throws SQLException, AccountException {
        if (GetMemberLoanCount(LOAN_NOT_CLEARED, true) >= 1) {
            throw new AccountException("You have pending loans to pay. Please clear them first to be able to continue");
        }
        this.conn = new Database().getConnection();
        setTotalAmount(amount);
        try {
            String sql = "INSERT INTO `loans` "
                    + "(`member_id`, `LoanType`, `LoanAmount`, "
                    + "`TotalAmount`, `PaybackDate`, `LoanPurpose`) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, Member.getId());
            stmt.setString(2, getLoanType());
            stmt.setDouble(3, getLoanAmount());
            stmt.setDouble(4, getTotalAmount());
            stmt.setDouble(5, getPaybackPeriod());
            stmt.setString(6, getLoanPurpose());
            int rows = stmt.executeUpdate();
            if (rows == 0) {
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
            close();
        }
    }

    public int GetOverallLoanCount(int cleared) throws SQLException {
        String sql;
        this.conn = new Database().getConnection();
        if (cleared == 0) {
            sql = "SELECT COUNT(id) FROM loans WHERE cleared = 0";
        } else if (cleared == 1) {
            sql = "SELECT COUNT(id) FROM loans WHERE cleared = 1";
        } else {
            sql = "SELECT COUNT(id) FROM loans";
        }
        try {
            stmt = conn.prepareStatement(sql);
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

    public int GetMemberLoanCount(int cleared, boolean ApprovedStatus) throws SQLException {
        String sql;
        this.conn = new Database().getConnection();
        if (cleared == 0 && ApprovedStatus) {
            sql = "SELECT COUNT(member_id) FROM loans WHERE member_id = ? AND Approved = 1";
        } else if (cleared == 0 && !ApprovedStatus) {
            sql = "SELECT COUNT(member_id) FROM loans WHERE member_id = ? AND cleared = 0 AND Approved = 0";
        } else if (cleared == 1 && ApprovedStatus) {
            sql = "SELECT COUNT(member_id) FROM loans WHERE member_id = ? AND cleared = 1 AND Approved = 1";
        } else {
            sql = "SELECT COUNT(member_id) FROM loans WHERE member_id = ?";
        }
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
            result = stmt.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            } else {
                throw new SQLException("a count could not be made");
            }
        } finally {
            close();
        }
    }

    public void getLoanInfo(int cleared, boolean allMembers) throws SQLException {
        String sql;
        this.conn = new Database().getConnection();
        if (allMembers && Member.isAdmin()) {
            sql = "SELECT * FROM `loans`";
            stmt = conn.prepareStatement(sql);
        } else if (cleared == 0) {
            sql = "SELECT * FROM `loans` WHERE member_id = ? AND cleared = 0";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
        } else if (cleared == 1) {
            sql = "SELECT * FROM `loans` WHERE member_id = ? AND cleared = 1";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
        } else {
            sql = "SELECT * FROM `loans` WHERE member_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
        }
        try {
            result = stmt.executeQuery();
            while (result.next()) {
                Loan l = new Loan();
                l.id = result.getLong("id");
                l.LoanType = result.getString("LoanType");
                l.LoanAmount = result.getDouble("LoanAmount");
                l.TotalAmount = result.getDouble("TotalAmount");
                l.AmountPaid = result.getDouble("paidAmount");
                l.DateSubmitted = result.getTimestamp("DateSubmitted");
                l.PaybackPeriod = result.getDouble("paybackDate");
                l.LoanType = result.getString("loanType");
                l.LOAN_APPROVED = result.getBoolean("Approved");
                loanInfo.add(l);
            }
        } finally {
            close();
        }
    }

    public void PrintLoanStatus(JTextArea jt, int cleared, boolean DisplayExtraInfo, boolean allMembers) throws SQLException {
        jt.setText("");
        this.conn = new Database().getConnection();
        loanInfo.clear();
        if (Member.isAdmin()) {
            getLoanInfo(cleared, allMembers);
        } else {
            getLoanInfo(cleared, DisplayExtraInfo);
        }
        double la, pa = 0, ta = 0;
        Date dt = Date.valueOf(LocalDate.now());
        jt.append("LOAN_AMOUNT\tTOTAL_AMOUNT\tLOAN_PERIOD(Months)\tAPPROVED\tLOAN_TYPE\tPAID_AMOUNT\n \n");
        for (Loan loan : loanInfo) {
            la = Double.parseDouble(Utility.DF.format(loan.getLoanAmount()));
            pa = Double.parseDouble(Utility.DF.format(loan.getAmountPaid()));
            ta = Double.parseDouble(Utility.DF.format(loan.getTotalAmount()));
            jt.append(la + "\t\t");
            jt.append(ta + "\t");
            jt.append("\t\t" + Utility.DF.format(loan.getPaybackPeriod() * 12) + "\t");
            if (loan.LOAN_APPROVED) {
                jt.append("YES\t");
            } else {
                jt.append("NO\t");
            }
            jt.append(loan.getLoanType() + "\t");
            jt.append(pa + "\n");
            dt = new Date(loan.getDateSubmitted().getTime());
        }
        jt.append("\n");
        if (DisplayExtraInfo) {
            jt.append("==============================================================================\n\n");
            jt.append("For your selected option, You currently have " + GetMemberLoanCount(cleared, true) + " loans\n");
            jt.append("Regarding your current loan, you've paid ksh " + pa + " since " + dt + "\n");
            jt.append("You owe the sacco ksh " + Utility.DF.format(ta - pa) + ". \t Note: A negative value indicates an overpayment\n\n");
            jt.append("LOAN_AMOUNT  ==> represents the amount(s) you took as a loan\n");
            jt.append("TOTAL_AMOUNT ==> loan + interest\n");
            jt.append("PAID_AMOUNT  ==> represents the amount you paid for each loan\n");
            jt.append("LOAN_PERIOD  ==> represents the number of Months you chose to fulfil your loan payment\n\n");
            jt.append("==============================================================================\n\n");
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
