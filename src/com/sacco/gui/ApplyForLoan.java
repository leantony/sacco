/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacco.gui;

import com.sacco.classes.Utility;
import static com.sacco.classes.Utility.Validation.*;
import com.sacco.classes.Loan;
import java.sql.SQLException;
import javax.security.auth.login.AccountException;
import javax.swing.JOptionPane;

/**
 *
 * @author Antony
 */
public class ApplyForLoan extends javax.swing.JInternalFrame {

    Loan _loan = new Loan();
    double allowed_loan = 0;

    /**
     * Creates new form NewJInternalFrame
     *
     */
    public ApplyForLoan() {
        try {
            initComponents();
            // check the interest
            if (_loan.getLoanInterestFromDB() == -1) {
                jLabel1.setText("A problem has occured. please bear with us as we fix it");
                DisplayComponents(false);
            }
            allowed_loan = _loan.GetAllowedLoan();
            jLabelOfferedLoan.setText(Utility.DF.format(allowed_loan) + "");

            if (allowed_loan < Loan.MIN_LOAN()) {
                jLabel1.setText("<html><p><b>Your loan offering of ksh " + jLabelOfferedLoan.getText() + " is less than our allowed minimmum of ksh " + Loan.MIN_LOAN() + " </p><br><p>Please top up your sacco contributions</p> </html>");
                DisplayComponents(false);
            } else {
                jLabel1.setText("<html>We offer loans of between Ksh "
                        + Loan.MIN_LOAN() + " and " + Loan.getMaxLoanAmount()
                        + " for a period of 60 months (5 years) <br>Our current monthly interest rate is " + _loan.getLoanInterestFromDB()
                        + " percent. To apply for one, simply fill in all the fields below and press the submitt button <br> "
                        + "You can apply for another as soon as youve completed your loan payment</html>");
            }

        } catch (SQLException ex) {
            jLabel1.setText("Welcome.Please apply for a loan by filling in the form below");
        } catch (AccountException ex) {
            jLabel1.setText(ex.getMessage());
            DisplayComponents(false);
        }
    }

    private void DisplayComponents(boolean display) {
        jPanel1.setVisible(display);
        jPanel4.setVisible(display);
        jButtonSubmit.setEnabled(display);
        jButtonReset.setEnabled(display);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaLoanPurpose = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxLoanType = new javax.swing.JComboBox();
        jTextFieldLoanAmnt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldPayBackPeriod = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabelOfferedLoan = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jButtonCancel = new javax.swing.JButton();
        jButtonReset = new javax.swing.JButton();
        jButtonSubmit = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Loan Application Form");
        setDoubleBuffered(true);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Please describe why you need the loan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jTextAreaLoanPurpose.setColumns(20);
        jTextAreaLoanPurpose.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jTextAreaLoanPurpose.setRows(5);
        jScrollPane2.setViewportView(jTextAreaLoanPurpose);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Loan Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Loan Type");

        jComboBoxLoanType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBoxLoanType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "secured", "unsecured" }));
        jComboBoxLoanType.setToolTipText("Type of loan you require. We offer both secured and unsecured loans");

        jTextFieldLoanAmnt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldLoanAmnt.setToolTipText("The amount you need as a loan");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Amount (Ksh)");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Payback Period (Months)");

        jTextFieldPayBackPeriod.setToolTipText("We allow a maximmum of 90 months");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Your Maximmum allowed loan (KSH)");

        jLabelOfferedLoan.setBackground(new java.awt.Color(255, 153, 51));
        jLabelOfferedLoan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelOfferedLoan.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBoxLoanType, 0, 134, Short.MAX_VALUE)
                    .addComponent(jTextFieldLoanAmnt)
                    .addComponent(jLabelOfferedLoan, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(jTextFieldPayBackPeriod))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxLoanType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldLoanAmnt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPayBackPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOfferedLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(36, 36, 36))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonCancel.setText("Cancel");
        jButtonCancel.setToolTipText("closes the loan submission form");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonReset.setText("Reset");
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        jButtonSubmit.setText("Submit");
        jButtonSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonReset)
                .addGap(193, 193, 193)
                .addComponent(jButtonSubmit)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        // TODO add your handling code here:
        Utility.clearAllTextFields(this.getContentPane());
    }//GEN-LAST:event_jButtonResetActionPerformed

    private void jButtonSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSubmitActionPerformed
        // TODO add your handling code here:
        // validate for didgits
        int amnt = CheckIfInteger(jTextFieldLoanAmnt.getText(), true);
        if (amnt == -1) {
            JOptionPane.showMessageDialog(rootPane, "please enter valid value for the amount", "wrong input type", JOptionPane.ERROR_MESSAGE);
            jTextFieldLoanAmnt.requestFocus();
            return;
        } else if (amnt < Loan.MIN_LOAN() | amnt > Loan.getMaxLoanAmount()) {
            JOptionPane.showMessageDialog(rootPane, "sorry .currently we offer loans of between " + Loan.MIN_LOAN() + " and " + Loan.getMaxLoanAmount(), "wrong input range", JOptionPane.ERROR_MESSAGE);
            jTextFieldLoanAmnt.requestFocus();
            return;
        } else {
            _loan.setLoanAmount(amnt);
        }

        // set the loan type
        _loan.setLoanType(jComboBoxLoanType.getSelectedItem().toString());

        int months = CheckIfInteger(jTextFieldPayBackPeriod.getText(), true);
        // the date by which payments should be resolved
        if (months == -1) {
            JOptionPane.showMessageDialog(rootPane, "Enter the number of months", "wrong input type", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (months > 60) {
            JOptionPane.showMessageDialog(rootPane, "We do not allow periods of more than 5 years", "wrong input type", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            //JOptionPane.showMessageDialog(rootPane, months);
            _loan.setPaybackPeriod(months);
        }

        // the loan purpose
        if (ValidateEmptyValue(jTextAreaLoanPurpose, "please enter a loan purpose")) {
            _loan.setLoanPurpose(jTextAreaLoanPurpose.getText());
        } else {
            return;
        }

        // save the loan
        try {
//            JOptionPane.showMessageDialog(rootPane, "According to your contributions, you can be able to recieve a max loan of ksh " + max_amnt + "\n You can now submitt your loan", "Information", JOptionPane.INFORMATION_MESSAGE);
            if (amnt > allowed_loan) {
                JOptionPane.showMessageDialog(rootPane, "Please change your loan to be less or equal to your allowed maximmum");
                jTextFieldLoanAmnt.requestFocus();
            } else {
                _loan.GetLoan(amnt);
                JOptionPane.showMessageDialog(rootPane, "Thank You. Your loan submission was successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "SQL error", "Error", JOptionPane.ERROR_MESSAGE);
            // set focus on the first field
            jTextFieldLoanAmnt.requestFocus();
        } catch (AccountException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Not elligible", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButtonSubmitActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JButton jButtonSubmit;
    private javax.swing.JComboBox jComboBoxLoanType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelOfferedLoan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaLoanPurpose;
    private javax.swing.JTextField jTextFieldLoanAmnt;
    private javax.swing.JTextField jTextFieldPayBackPeriod;
    // End of variables declaration//GEN-END:variables
}
