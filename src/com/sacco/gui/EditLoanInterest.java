/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacco.gui;

import com.sacco.classes.Admin;
import com.sacco.classes.Utility;
import static com.sacco.classes.Utility.Validation.CheckForDouble;
import com.sacco.classes.Loan;
import java.sql.SQLException;
import javax.security.auth.login.AccountException;
import javax.swing.JOptionPane;

/**
 *
 * @author Antony
 */
public class EditLoanInterest extends javax.swing.JInternalFrame {

    Admin _admin;
    Loan _loan;
    double value;

    /**
     * Creates new form NewJInternalFrame
     */
    public EditLoanInterest() {
        try {
            this._admin = new Admin();
            this._loan = new Loan();
            if (_loan.getLoanInterestFromDB() == -1) {
                JOptionPane.showMessageDialog(rootPane, "An error occured. -1 was returned as the interest which is invalid", "Error", JOptionPane.ERROR_MESSAGE);
                this.dispose();
            } else {
                initComponents();
                jLabelCurrentInterest.setText(Double.toString(Loan.getLoanInterest()));
            }
        } catch (AccountException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Access denied", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "SQL error caught", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonSave = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldNewInterest = new javax.swing.JTextField();
        jLabelCurrentInterest = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Change Percentage Loan Interest");
        setToolTipText("");
        setDoubleBuffered(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButtonSave.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonSave.setText("Save");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("New Monthly Loan Interest (%)");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Current Monthly Loan Interest (%)");
        jLabel2.setToolTipText("");

        jTextFieldNewInterest.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldNewInterest.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTextFieldNewInterestInputMethodTextChanged(evt);
            }
        });

        jLabelCurrentInterest.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(jButtonSave))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldNewInterest, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                    .addComponent(jLabelCurrentInterest, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCurrentInterest, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldNewInterest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(jButtonSave)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private double getYearlyInterest(double monthly) {
        double YearlyInterest = monthly * 12;
        return Double.parseDouble(Utility.DF.format(YearlyInterest));
    }

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        // TODO add your handling code here:
        value = CheckForDouble(jTextFieldNewInterest.getText());
        if (value <= 0 || value > 50) {
            JOptionPane.showMessageDialog(rootPane, "You've picked an invalid value. A valid interest should\nlie between 1 and 50 %", "Invalid interest", JOptionPane.INFORMATION_MESSAGE);
            jTextFieldNewInterest.requestFocus();
        } else {
            try {

                int reply = JOptionPane.showConfirmDialog(rootPane, "The interest rate will change for all future loans\n"
                        + "The yearly interest will be " + getYearlyInterest(value) + "%\nDo you really want to do this?", "prompt", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (reply == JOptionPane.YES_OPTION) {
                    if (_admin.ChangeLoanInterest(value)) {
                        JOptionPane.showMessageDialog(rootPane, "Operation succeeded", "Success", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Operation failed. Interest not changed", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "SQL error caught", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jTextFieldNewInterestInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextFieldNewInterestInputMethodTextChanged

    }//GEN-LAST:event_jTextFieldNewInterestInputMethodTextChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSave;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelCurrentInterest;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFieldNewInterest;
    // End of variables declaration//GEN-END:variables
}
