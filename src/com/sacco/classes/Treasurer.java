package com.sacco.classes;

import java.sql.SQLException;
import javax.swing.JTextArea;

public class Treasurer extends Member {

    public static int TREASURER_POSITION_ID = 3;
    Contribution _contrib;
    Loan _loan;
    LoanPayment _loanPay;

    public Treasurer() {
        this._loanPay = new LoanPayment();
        this._loan = new Loan();
        this._contrib = new Contribution();
    }

    public void ViewLoanAndContributionTotals(JTextArea jt) throws SQLException {
        jt.setText("");
        jt.append("Totals ==>\n\n");
        jt.append("Total number of Loans granted : " + _loan.GetOverallLoanCount(2) + "\n");
        jt.append("Loans fully paid : " + _loan.GetOverallLoanCount(1) + "\n");
        jt.append("Loans not paid : " + _loan.GetOverallLoanCount(0) + "\n");
        jt.append("Total Amount Loaned : Ksh" + _loanPay.GetLoanTotal(2) + " (This includes all loans regardless of whether they hav been paid or not) \n");
        jt.append("Amount total of loans paid : Ksh" + _loanPay.getPaymentsTotal() + "\n");
        jt.append("\n\n");
        jt.append("Total Interest Acquired From loans : Ksh" + Utility.DF.format(_loanPay.getInterestTotal()));
        jt.append("\n");
    }
}
