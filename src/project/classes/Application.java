/*
 * this class contains various utility functions that will be used across the application
 */
package project.classes;

import java.awt.Component;
import java.awt.Container;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Antony
 */
public class Application {

    /**
     * Exit the application
     *
     * @param status
     */
    public static void Exit(int status) {
        System.exit(status);
    }

    // check & parse user date input. doesn't work
    public static Date CheckDateInput(String date) {
        // sql date input
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd");
        try {
            Date date1 = (Date) simpleDateFormat.parse(date);
            return date1;
        } catch (ParseException ex) {
            return null;
        }
    }

    // clear all textual input
    public static void clearTextFields(Container container) {

        for (Component c : container.getComponents()) {
            if (c instanceof JTextField) {
                JTextField f = (JTextField) c;
                f.setText("");
            } else if (c instanceof Container) {
                clearTextFields((Container) c);
            }
        }
    }

    // check if a text box is empty
    public static boolean ValidateEmptyTextBox(JTextField a, String errorMsg) {
        if (errorMsg == null || errorMsg.isEmpty()) {
            errorMsg = "Please enter a value.";
        }
        if (a.getText() == null || a.getText().isEmpty()) {
            a.requestFocus();
            JOptionPane.showMessageDialog(null, errorMsg, "Empty value", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * // check if a text area is empty
     *
     * @param a
     * @param errorMsg
     * @return
     */
    public static boolean ValidateEmptyTextArea(JTextArea a, String errorMsg) {
        if (errorMsg == null || errorMsg.isEmpty()) {
            errorMsg = "Please enter a value.";
        }
        if (a.getText() == null || a.getText().isEmpty()) {
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
            if (Integer.parseInt(s) <= 0) {
                return -1;
            }
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // check if a two strings are equal
    public static boolean CheckStringEquality(String a, String b) {
        return a.equals(b);
    }

    // check if passwords match
    public static boolean CheckPasswordEquality(JPasswordField a, JPasswordField b) {
        char[] inputA = a.getPassword();
        char[] inputB = b.getPassword();
        if (inputA == null && inputB == null) {
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

    public static void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
        fb.remove(offset, length);
    }

}
