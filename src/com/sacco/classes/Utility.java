/*
 * this class contains various utility functions that will be used across the application
 */
package com.sacco.classes;

import java.awt.Component;
import java.awt.Container;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;

public final class Utility {

    public static DecimalFormat DF = new DecimalFormat("#.##");
    public static DateFormat DT = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    public static String AREA_CODE = "254";
    public static String EXTENDED_AREA_CODE = "2547";

    // exit the app
    public static void Exit(int status) {
        System.exit(status);
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

    public static class Validation {

        // check & parse user date input
        public static boolean CheckDateInput(String date) {
            DateValidator dt = DateValidator.getInstance();
            return dt.isValid(date);
        }

        public static boolean ValidateEmail(String email) {
            EmailValidator emailValidator = EmailValidator.getInstance();
            return emailValidator.isValid(email);
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

        public static boolean checkMobileNo(long Mno) {
            String m = Long.toString(Mno);
            return m.startsWith(EXTENDED_AREA_CODE) && m.length() == 12;
        }

        public static long CheckIfLong(String s, boolean zeroOrLess) {
            try {
                // disallow solo 0 or negative number input
                if (Long.parseLong(s.trim()) <= 0 && zeroOrLess) {
                    return -1;
                }
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                return -1;
            }
        }

        // check if a string input is integer
        public static int CheckIfInteger(String s, boolean zeroOrLess) {
            try {
                // disallow solo 0 or negative number input
                if (Integer.parseInt(s.trim()) <= 0 && zeroOrLess) {
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
}
