/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacco.gui;

import com.sacco.classes.Admin;
import java.sql.SQLException;
import javax.security.auth.login.AccountException;
import javax.swing.JOptionPane;

/**
 *
 * @author Antony
 */
public class ApproveContribution extends javax.swing.JInternalFrame {

    Admin _admin;

    /**
     * Creates new form ApproveContribution
     */
    public ApproveContribution() {
        try {
            this._admin = new Admin();
            // TODO add your handling code here:
            _admin.DisplayAllContributions(jComboBoxContributions);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "SQL error", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        } catch (AccountException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Access denied", JOptionPane.ERROR_MESSAGE);
            this.dispose();
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

        jPanel2 = new javax.swing.JPanel();
        jComboBoxContributions = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jTextMId = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldFname = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldDateApproved = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldLname = new javax.swing.JTextField();
        jTextFieldPayMethod = new javax.swing.JTextField();
        jTextFieldDateContributed = new javax.swing.JTextField();
        jTextFieldAmnt = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jRadioButtonApprove = new javax.swing.JRadioButton();
        jRadioButtonReject = new javax.swing.JRadioButton();
        jRadioButtonDelete = new javax.swing.JRadioButton();
        jButtonSave = new javax.swing.JButton();

        setTitle("(Administrator) Contributions");
        setToolTipText("");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "lookup contributions by their ID's", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jComboBoxContributions.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBoxContributions.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxContributionsItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jComboBoxContributions, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 247, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jComboBoxContributions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Contribution Info"));
        jPanel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextMId.setEditable(false);
        jTextMId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Member ID");

        jTextFieldFname.setEditable(false);
        jTextFieldFname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel2.setText("First Name");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Last Name");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Amount");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Payment Method");

        jTextFieldDateApproved.setEditable(false);
        jTextFieldDateApproved.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Date Contributed");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Date Approved");

        jTextFieldLname.setEditable(false);
        jTextFieldLname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextFieldPayMethod.setEditable(false);
        jTextFieldPayMethod.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextFieldDateContributed.setEditable(false);
        jTextFieldDateContributed.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextFieldAmnt.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(97, 97, 97))
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextMId, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(jTextFieldFname)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldDateContributed, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldPayMethod, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldAmnt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldLname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(jTextFieldDateApproved)))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextMId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldFname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldLname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAmnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPayMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(47, 47, 47)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDateContributed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(47, 47, 47)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDateApproved, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Action", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jRadioButtonApprove.setText("Approve");

        jRadioButtonReject.setText("Reject");

        jRadioButtonDelete.setText("Delete");

        jButtonSave.setText("Save");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButtonApprove)
                    .addComponent(jRadioButtonReject)
                    .addComponent(jRadioButtonDelete)
                    .addComponent(jButtonSave))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jRadioButtonApprove)
                .addGap(18, 18, 18)
                .addComponent(jRadioButtonReject)
                .addGap(18, 18, 18)
                .addComponent(jRadioButtonDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addComponent(jButtonSave)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxContributionsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxContributionsItemStateChanged

    }//GEN-LAST:event_jComboBoxContributionsItemStateChanged

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSave;
    private javax.swing.JComboBox jComboBoxContributions;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButtonApprove;
    private javax.swing.JRadioButton jRadioButtonDelete;
    private javax.swing.JRadioButton jRadioButtonReject;
    private javax.swing.JTextField jTextFieldAmnt;
    private javax.swing.JTextField jTextFieldDateApproved;
    private javax.swing.JTextField jTextFieldDateContributed;
    private javax.swing.JTextField jTextFieldFname;
    private javax.swing.JTextField jTextFieldLname;
    private javax.swing.JTextField jTextFieldPayMethod;
    private javax.swing.JTextField jTextMId;
    // End of variables declaration//GEN-END:variables
}
