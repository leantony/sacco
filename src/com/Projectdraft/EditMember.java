/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Projectdraft;

import com.sacco.classes.Application;
import com.sacco.classes.Member;
import java.sql.SQLException;
import java.time.Instant;
import javax.security.auth.login.AccountException;
import javax.swing.JOptionPane;

/**
 *
 * @author Antony
 */
public class EditMember extends javax.swing.JInternalFrame {

    Member _member = new Member();

    /**
     * Creates new form NewJInternalFrame
     */
    public EditMember() {
        try {
            initComponents();
            _member.getMemberInfo(_member, Member.getId());
            jTextFieldFname.setText(_member.getFirstname());
            jTextFieldLname.setText(_member.getLastname());

            // auto mark the gender
            if (_member.getGender().equals("Male")) {
                jRadioButtonMale.setSelected(true);
            } else {
                jRadioButtonFemale.setSelected(true);
            }
            jXDatePickerDOB.setDate(_member.getDob());
            jTextFieldMobileNo.setText(String.valueOf(_member.getMobileno()));
            jTextFieldAddress.setText(_member.getAddress());
            jTextFieldEmail.setText(_member.getEmail());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Encountered an error while trying to get your data. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
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

        jLabelInfo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldFname = new javax.swing.JTextField();
        jTextFieldLname = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldMobileNo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldAddress = new javax.swing.JTextField();
        jTextFieldEmail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButtonReset = new javax.swing.JButton();
        jButtonSubmit = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jRadioButtonFemale = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jRadioButtonMale = new javax.swing.JRadioButton();
        jXDatePickerDOB = new org.jdesktop.swingx.JXDatePicker();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Edit Your Info");
        setDoubleBuffered(true);

        jLabelInfo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelInfo.setText("All Your details are displayed here. Feel free to edit");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("First Name");

        jTextFieldFname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldFname.setToolTipText("Enter Your first name");
        jTextFieldFname.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextFieldFname.setDragEnabled(true);

        jTextFieldLname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldLname.setToolTipText("Enter Your last name");
        jTextFieldLname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLnameActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Last Name");

        jTextFieldMobileNo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldMobileNo.setToolTipText("Enter a mobile number e.g 0712345678");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Mobile Number");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Address");

        jTextFieldAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldAddress.setToolTipText("Your Home Address e.g 768, karen");

        jTextFieldEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldEmail.setToolTipText("enter an Email Address e.g myname@domain.org");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("email Address");
        jLabel8.setToolTipText("");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("To submitt the form, press submit"));

        jButtonReset.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonReset.setText("Reset");
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        jButtonSubmit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonSubmit.setText("Submit");
        jButtonSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(jButtonSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jButtonReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select new gender", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jRadioButtonFemale.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButtonFemale.setText("Female");

        jLabel5.setText("Gender");
        jLabel5.setToolTipText("Select a gender");

        jRadioButtonMale.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButtonMale.setText("Male");
        jRadioButtonMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMaleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButtonFemale)
                    .addComponent(jRadioButtonMale))
                .addGap(65, 65, 65))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButtonMale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButtonFemale)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(48, 48, 48))
        );

        jXDatePickerDOB.setToolTipText("Select a date of birth");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Date of Birth");
        jLabel4.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(jLabelInfo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldLname)
                                    .addComponent(jTextFieldFname))
                                .addGap(72, 72, 72)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                    .addComponent(jTextFieldAddress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                    .addComponent(jTextFieldMobileNo)
                                    .addComponent(jXDatePickerDOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(72, 72, 72)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(58, 58, 58))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelInfo)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldFname, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldLname, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jXDatePickerDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldMobileNo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addGap(54, 54, 54))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jTextFieldAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldLnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLnameActionPerformed

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        // TODO add your handling code here:
        Application.clearAllTextFields(this.getContentPane());
    }//GEN-LAST:event_jButtonResetActionPerformed

    private void jButtonSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSubmitActionPerformed

        // the member names. we check if user input is ok
        if (Application.ValidateEmptyValue(jTextFieldFname, "please enter a value for firstname")) {
            _member.setFirstname(jTextFieldFname.getText());
        } else {
            // we halt execution of the function
            return;
        }
        if (Application.ValidateEmptyValue(jTextFieldLname, "please enter a value for lastname")) {
            _member.setLastname(jTextFieldLname.getText());
        } else {
            return;
        }

        // set the gender
        if (jRadioButtonFemale.isSelected()) {
            _member.setGender(jRadioButtonFemale.getText());
        } else {
            _member.setGender(jRadioButtonMale.getText());
        }

        java.util.Date dt = jXDatePickerDOB.getDate();
        if (dt == null) {
            dt = java.util.Date.from(Instant.EPOCH);
        }
        _member.setDob(new java.sql.Date(dt.getTime()));

        // validate for didgits
        long mobileno = Application.CheckIfLong(jTextFieldMobileNo.getText());
        if (mobileno == -1) {
            JOptionPane.showMessageDialog(rootPane, "please enter valid value for phone number", "Invalid phone number", JOptionPane.ERROR_MESSAGE);
            jTextFieldMobileNo.requestFocus();
            return;
        } else {
            String s = Application.AREA_CODE + Long.toString(mobileno);
            JOptionPane.showMessageDialog(rootPane, s);
            _member.setMobileno(Long.parseLong(s));
        }

        // the address
        if (Application.ValidateEmptyValue(jTextFieldAddress, "please enter a value for Address")) {
            _member.setAddress(jTextFieldAddress.getText());
        } else {
            return;
        }

        // the email
        if (Application.ValidateEmail(jTextFieldEmail.getText())) {
            _member.setEmail(jTextFieldEmail.getText());
        } else {
            JOptionPane.showMessageDialog(rootPane, "please enter a valid email address", "Invalid email", JOptionPane.ERROR_MESSAGE);
            jTextFieldEmail.requestFocus();
            return;
        }

        try {
            // edit the info
            if (_member.EditMemberInfo(_member)) {
                JOptionPane.showMessageDialog(rootPane, "Edit was a Success ", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(rootPane, "No update was made", "Warning", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (AccountException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Access denied", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "SQL error ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonSubmitActionPerformed

    private void jRadioButtonMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonMaleActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonReset;
    private javax.swing.JButton jButtonSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelInfo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButtonFemale;
    private javax.swing.JRadioButton jRadioButtonMale;
    private javax.swing.JTextField jTextFieldAddress;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldFname;
    private javax.swing.JTextField jTextFieldLname;
    private javax.swing.JTextField jTextFieldMobileNo;
    private org.jdesktop.swingx.JXDatePicker jXDatePickerDOB;
    // End of variables declaration//GEN-END:variables
}
