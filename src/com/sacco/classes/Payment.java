/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacco.classes;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author Antony
 */
public abstract class Payment {

    public Payment() {
    }

    /**
     * @return the id
     */
    public abstract long getId();

    /**
     * @param id the id to set
     */
    protected abstract void setId(long id);

    /**
     * @return the amount
     */
    public abstract double getAmount();

    /**
     * @param amount the amount to set
     */
    public abstract void setAmount(double amount);

    /**
     * @return the DatePaid
     */
    public abstract Timestamp getDatePaid();

    /**
     * @param DatePaid the DatePaid to set
     */
    public abstract void setDatePaid(Timestamp DatePaid);

    /**
     * @return the Approved
     */
    public abstract boolean isApproved();

    /**
     * @param Approved the Approved to set
     */
    public abstract void setApproved(boolean Approved);

    /**
     *
     * @param id
     * <p>
     * The member's ID </p>
     * @return
     * <p>
     * The payment ID </p>
     * @throws SQLException
     */
    protected abstract long recordLoanPayment(long id, double amount) throws SQLException;

    /**
     *
     * @param cleared
     * @throws SQLException
     */
    public abstract void getOverallPaymentInfo(boolean cleared) throws SQLException;

}
