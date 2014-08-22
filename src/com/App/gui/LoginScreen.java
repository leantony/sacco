/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.App.gui;

import com.sacco.classes.Member;
import com.sacco.classes.Session;
import com.sacco.classes.Utility;
import static com.sacco.classes.Utility.Validation.CheckIfLong;
import static com.sacco.classes.Utility.Validation.ValidateEmail;
import java.sql.SQLException;
import javax.security.auth.login.AccountException;
import javax.security.auth.login.LoginException;
import javax.swing.JOptionPane;

/**
 *
 * @author CJ
 */
public class LoginScreen extends javax.swing.JFrame {

    Member _member = new Member();
    Session _session = new Session();
    static int loginErrors = 0;

    /**
     * Creates new form Register
     */
    public LoginScreen() {
        initComponents();
        jPanelEmail.setVisible(false);
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
        jPanel1 = new javax.swing.JPanel();
        jButtonLogin = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jPanelID = new javax.swing.JPanel();
        LoginAs1 = new javax.swing.JLabel();
        jTextFieldMemberID = new javax.swing.JTextField();
        jPanelEmail = new javax.swing.JPanel();
        jTextFieldMemberEmail = new javax.swing.JTextField();
        LoginAs3 = new javax.swing.JLabel();
        jCheckBoxUseEmail = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        LoginAs2 = new javax.swing.JLabel();
        jPasswordPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Member Login");
        setLocationByPlatform(true);
        setName("Login Screen"); // NOI18N
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter UserId or Email Address to login", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jButtonLogin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonLogin.setText("Login");
        jButtonLogin.setToolTipText("log me in !");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        jButtonCancel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonCancel.setText("Cancel");
        jButtonCancel.setToolTipText("cancels the logn process and opens the welcone screen");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        LoginAs1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LoginAs1.setText("User ID");

        jTextFieldMemberID.setColumns(7);
        jTextFieldMemberID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanelIDLayout = new javax.swing.GroupLayout(jPanelID);
        jPanelID.setLayout(jPanelIDLayout);
        jPanelIDLayout.setHorizontalGroup(
            jPanelIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIDLayout.createSequentialGroup()
                .addComponent(LoginAs1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldMemberID)
                .addContainerGap())
        );
        jPanelIDLayout.setVerticalGroup(
            jPanelIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIDLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldMemberID, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LoginAs1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        jTextFieldMemberEmail.setColumns(7);
        jTextFieldMemberEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        LoginAs3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LoginAs3.setText("Email Address");

        javax.swing.GroupLayout jPanelEmailLayout = new javax.swing.GroupLayout(jPanelEmail);
        jPanelEmail.setLayout(jPanelEmailLayout);
        jPanelEmailLayout.setHorizontalGroup(
            jPanelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEmailLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(LoginAs3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldMemberEmail)
                .addContainerGap())
        );
        jPanelEmailLayout.setVerticalGroup(
            jPanelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEmailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldMemberEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LoginAs3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jCheckBoxUseEmail.setText("Use my Email to login");
        jCheckBoxUseEmail.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBoxUseEmailStateChanged(evt);
            }
        });
        jCheckBoxUseEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxUseEmailActionPerformed(evt);
            }
        });

        LoginAs2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LoginAs2.setText("Password");

        jPasswordPassword.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LoginAs2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPasswordPassword)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LoginAs2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBoxUseEmail)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButtonCancel)
                                .addGap(208, 208, 208)
                                .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE))
                            .addComponent(jPanelID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(78, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jCheckBoxUseEmail)
                .addGap(18, 18, 18)
                .addComponent(jPanelID, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed

        if (loginErrors >= 5) {
            JOptionPane.showMessageDialog(rootPane, "You have exceeded your maximmum number of login attempts");
            Utility.Exit(1);
        }
        // check input
        long id = CheckIfLong(jTextFieldMemberID.getText(), false);
        boolean useID = false;
        if (jPanelID.isVisible()) {
            if (id <= 0) {
                JOptionPane.showMessageDialog(rootPane, "Please specify a valid user ID. Should be a number", "Wrong user ID", JOptionPane.ERROR_MESSAGE);
                loginErrors++;
                jTextFieldMemberID.requestFocus();
                return;
            }
            useID = true;
        }

        String email = null;
        boolean useEmail = false;
        if (jPanelEmail.isVisible()) {
            // the email
            if (ValidateEmail(jTextFieldMemberEmail.getText())) {
                email = jTextFieldMemberEmail.getText();
                useEmail = true;
            } else {
                JOptionPane.showMessageDialog(rootPane, "please enter a valid email address", "Invalid email", JOptionPane.ERROR_MESSAGE);
                loginErrors++;
                jTextFieldMemberEmail.requestFocus();
                return;
            }
        }

        // the password
        String password = new String(jPasswordPassword.getPassword());
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please enter your password", "Empty value", JOptionPane.ERROR_MESSAGE);
            loginErrors++;
            jPasswordPassword.requestFocus();
            return;
        }

        // attempt login
        try {
            if (!_session.Login(id, password, email, useID, useEmail)) {
                JOptionPane.showMessageDialog(rootPane, "Invalid credentials specified. please try again", "Login failed", JOptionPane.INFORMATION_MESSAGE);
                loginErrors++;
                Utility.clearAllTextFields(this.getContentPane());
                jTextFieldMemberID.requestFocus();
            } else {
                this.dispose();
                new MembersIndex().setVisible(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Encountered an error when trying to log you in. Try again later " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Utility.clearAllTextFields(this.getContentPane());
            jTextFieldMemberID.requestFocus();
        } catch (AccountException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        } catch (LoginException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new WelcomeScreen().setVisible(true);
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jCheckBoxUseEmailStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBoxUseEmailStateChanged
        // TODO add your handling code here:
        if (jCheckBoxUseEmail.isSelected()) {
            jPanelEmail.setVisible(true);
            jPanelID.setVisible(false);
        } else {
            jPanelEmail.setVisible(false);
            jPanelID.setVisible(true);
        }
    }//GEN-LAST:event_jCheckBoxUseEmailStateChanged

    private void jCheckBoxUseEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxUseEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxUseEmailActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new LoginScreen().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LoginAs1;
    private javax.swing.JLabel LoginAs2;
    private javax.swing.JLabel LoginAs3;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JCheckBox jCheckBoxUseEmail;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelEmail;
    private javax.swing.JPanel jPanelID;
    private javax.swing.JPasswordField jPasswordPassword;
    private javax.swing.JTextField jTextFieldMemberEmail;
    private javax.swing.JTextField jTextFieldMemberID;
    // End of variables declaration//GEN-END:variables
}
