/*
 * this class contains various utility functions that will be used across the application
 */
package com.sacco.classes;

import java.awt.Component;
import java.awt.Container;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;

public final class Application {

    public static DecimalFormat df = new DecimalFormat("#.##");
    public static String AREA_CODE = "254";
    private static Pattern pattern;
    private static Matcher matcher;
    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public Application() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    // exit the app
    public static void Exit(int status) {
        System.exit(status);
    }

    // check & parse user date input
    public static boolean CheckDateInput(String date) {
        // sql date input
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd");
//        try {
//            Date d = simpleDateFormat.parse(date);
//            return new java.sql.Date(d.getTime());
//        } catch (ParseException ex) {
//            return null;
//        }
        DateValidator dt = DateValidator.getInstance();
        return dt.isValid(date);
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

    public static boolean ValidateEmail(String email) {
//        pattern = Pattern.compile(EMAIL_PATTERN);
//        matcher = pattern.matcher(email);
//        return matcher.matches();
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

    // check if a string input is long
    public static long CheckIfLong(String s) {
        try {
            // disallow solo 0 or negative number input
            if (Long.parseLong(s.trim()) <= 0) {
                return -1;
            }
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // check if a string input is integer
    public static int CheckIfInteger(String s) {
        try {
            // disallow solo 0 or negative number input
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
