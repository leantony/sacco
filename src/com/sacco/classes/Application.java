/*
 * this class contains various utility functions that will be used across the application
 */
package com.sacco.classes;

import java.awt.Component;
import java.awt.Container;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public final class Application {

    public static DecimalFormat df = new DecimalFormat("#.##");

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
    public static void clearAllTextFields(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof JTextArea) {
                JTextArea jt = (JTextArea) c;
                jt.setText("");
            } else if (c instanceof JTextField) {
                JTextField f = (JTextField) c;
                f.setText("");
            } else if (c instanceof Container) {
                clearAllTextFields((Container) c);
            }
        }
    }

    // check if a text box is empty
    public static boolean ValidateEmptyValue(Object a, String errorMsg) {
        if (a instanceof JEditorPane) {
            JEditorPane jt = (JEditorPane) a;

            if (jt.getText().trim().isEmpty()) {
                jt.requestFocus();
                JOptionPane.showMessageDialog(null, errorMsg, "Invalid input", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                return true;
            }
        }
        if (a instanceof JTextArea) {
            JTextArea jt = (JTextArea) a;

            if (jt.getText().trim().isEmpty()) {
                jt.requestFocus();
                JOptionPane.showMessageDialog(null, errorMsg, "Invalid input", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                return true;
            }
        } else if (a instanceof JTextField) {
            JTextField jt = (JTextField) a;

            if (jt.getText().trim().isEmpty()) {
                jt.requestFocus();
                JOptionPane.showMessageDialog(null, errorMsg, "Invalid input", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

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

    // check if a string input is a double
    public static double CheckForDouble(String s) {
        try {
            // disallow negative number input
            if (Double.parseDouble(s.trim()) <= 0) {
                return -1;
            }
            return Double.parseDouble(s);
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
