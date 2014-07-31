/*
 * this class contains various utility functions that will be used across the application
 */
package com.sacco.classes;

import java.awt.Component;
import java.awt.Container;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Application {

    // exit the app
    public static void Exit(int status) {
        System.exit(status);
    }

    // check & parse user date input
    public static java.sql.Date CheckDateInput(String date) {
        // sql date input
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd");
        try {
            Date d = simpleDateFormat.parse(date);
            return new java.sql.Date(d.getTime());
        } catch (ParseException ex) {
            return null;
        }
    }

    // clear all textual input
    public static void clearTextFields(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof JTextArea) {
                JTextArea jt = (JTextArea) c;
                jt.setText("");
            } else if (c instanceof JTextField) {
                JTextField f = (JTextField) c;
                f.setText("");
            } else if (c instanceof Container) {
                clearTextFields((Container) c);
            }
        }
    }

    // check if a text box is empty
    public static boolean ValidateEmptyTextBox(JTextField a, String errorMsg) {
        if (errorMsg.isEmpty()) {
            errorMsg = "Please enter a value for " + a.getName();
        }
        if (a.getText().trim().isEmpty()) {
            a.requestFocus();
            JOptionPane.showMessageDialog(null, errorMsg, "Empty value", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // check if a text area is empty
    public static boolean ValidateEmptyTextArea(JTextArea a, String errorMsg) {
        if (errorMsg.isEmpty()) {
            errorMsg = "Please enter a value for " + a.getName();
        }
        if (a.getText().trim().isEmpty()) {
            a.requestFocus();
            JOptionPane.showMessageDialog(null, errorMsg, "Empty value", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // check if a string input is a number
    public static int CheckIfNumber(String s) {
        try {
            // disallow negative number input
            if (Integer.parseInt(s.trim()) <= 0) {
                return -1;
            }
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // check if passwords match
    public static boolean CheckPasswordEquality(JPasswordField a, JPasswordField b) {
        char[] inputA = a.getPassword();
        char[] inputB = b.getPassword();
        if (inputA.length == 0 && inputB.length == 0) {
            return false;
        }
        if (Arrays.equals(inputA, inputB)) {
            return true;
        } else {
            // clear the input
            Arrays.fill(inputA, '0');
            Arrays.fill(inputB, '0');
            return false;
        }
    }
}
