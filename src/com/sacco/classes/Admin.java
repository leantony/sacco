package com.sacco.classes;

import static com.sacco.classes.Member.isAdmin;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.security.auth.login.AccountException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class Admin extends Member {

    public static int ADMIN_POS_ID = 1;

    // handy, since it will be always checked on instance creation
    public Admin() throws AccountException {
        // check if the user is who they claim to be
        if (!Member.isAdmin()) {
            throw new AccountException("Are you really an admin?. Check again");
        }
    }

    /**
     *
     * @param id
     * @param status
     * <p>
     * Status code 0 -> Deactivate account </p>
     * <p>
     * Status code 1 -> delete an activated account </p>
     * <p>
     * Status code 2 -> Activate an account </p>
     * <p>
     * Status code Any -> Forcefully delete an account </p>
     * @return
     * @throws SQLException
     * @throws AccountException
     */
    public boolean AlterMemberAccount(long id, int status) throws SQLException, AccountException {
        String sql;
        if (status == 0) {
            // deactivate account
            sql = "UPDATE members SET active = 0 WHERE id = ?";
        } else if (status == 1) {
            // delete only an acitvated account
            sql = "DELETE FROM members WHERE id = ? AND active = 1";
        } else if (status == 2) {
            sql = "UPDATE members SET active = 1 WHERE id = ?";
        } else {
            // delete any
            sql = "DELETE FROM members WHERE id = ?";
        }
        try {

            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            int rows = stmt.executeUpdate();
            return rows == 1;

        } finally {
            // close resources
            close();
        }
    }

    private ResultSet getAllMemberInfo() throws SQLException {
        String sql = "SELECT * FROM members";
        stmt = conn.prepareStatement(sql);
        result = stmt.executeQuery();
        return result;
    }

    /**
     *
     * @param combobox
     * @throws SQLException
     */
    public void DisplayAllMembers(JComboBox combobox) throws SQLException {
        DefaultComboBoxModel Model = new DefaultComboBoxModel();
        try {
            result = getAllMemberInfo();
            while (result.next()) {
                long memberID = result.getLong("id");
                Model.addElement(memberID);

            }
            combobox.setModel(Model);
        } finally {
            close();
        }

    }

    /**
     *
     * @param jt
     * @throws SQLException
     * @throws AccountException
     */
    public void DisplayAllMembers(JTextArea jt) throws SQLException, AccountException {
        jt.setText("");
        try {
            result = getAllMemberInfo();
            jt.append("Here are all the members in the sacco \n\n");
            jt.append("FIRSTNAME\t LASTNAME \t GENDER \t DATE_OF_BIRTH \t ADDRESS \t EMAIL\n\n");
            while (result.next()) {
                // store logged in userID in mem
                setId(id);
                jt.append(result.getString("firstname"));
                jt.append("\t");
                jt.append(result.getString("lastname"));
                jt.append("\t");
                jt.append(result.getString("gender"));
                jt.append("\t");
                jt.append(result.getDate("dob").toString());
                jt.append("\t\t");
                jt.append(result.getString("address"));
                jt.append("\t");
                jt.append(result.getString("email"));
                jt.append("\n");
            }
        } finally {
            close();
        }

    }

    /**
     *
     * @param id
     * <p>
     * The member's id</p>
     * @param posname
     * <p>
     * The position name. Type in any name and the function would try it's best
     * to find it in the database </p>
     * @param Action
     * <p>
     * Action code 1 -> New position (insert) </p>
     * <p>
     * Action code 2 or any -> Update existing member position </p>
     * @return
     * <p>
     * True if any of the actions was successful </p>
     * @throws SQLException
     */
    public boolean AlterMemeberPosition(long id, String posname, int Action) throws SQLException {
        String sql;
        long pid = getPositionId(posname);
        if (Action == 1) {
            sql = "INSERT INTO `members_positions` (`position_id`, `member_id`) VALUES (?, ?)";
        } else if (Action == 2) {
            sql = "UPDATE `members_positions` SET `position_id`=? WHERE  `member_id`=?";
        } else {
            sql = "UPDATE `members_positions` SET `position_id`=? WHERE  `member_id`=?";
        }
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, pid);
            stmt.setLong(2, id);
            int rows = stmt.executeUpdate();
            return rows == 1;

        } finally {
            // close resources
            close();
        }
    }

    /**
     *
     * @param id
     * <p>
     * The member's id</p>
     * @return
     * @throws SQLException
     * @throws AccountException
     */
    public boolean DeleteMemberLoan(long id) throws SQLException, AccountException {
        if (isAdmin()) {
            try {
                String sql = "DELETE FROM Loans WHERE member_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setLong(1, id);
                int rows = stmt.executeUpdate();
                return rows == 0;

            } finally {
                // close resources
                close();
            }
        } else {
            throw new AccountException("You are not allowed to perform this action");
        }
    }

    /**
     *
     * @return @throws SQLException
     */
    public double getLoanInterest() throws SQLException {
        try {
            String sql = "SELECT value FROM settings WHERE name = 'interest'";
            stmt = conn.prepareStatement(sql);
            result = stmt.executeQuery();
            if (result.next()) {
                return result.getDouble("value");
            }

        } finally {
            // close resources
            close();
        }
        return -1;
    }

    /**
     *
     * @param l
     * @param interest
     * @return
     * @throws SQLException
     */
    public boolean ChangeLoanInterest(Loan l, double interest) throws SQLException {
        if (getLoanInterest() == -1) {
            throw new SQLException("An error occured while trying to get the loan interest from db");
        }
        try {
//            int loanCount = l.GetLoanCount(3);
            String sql = "UPDATE settings SET value = ? WHERE name = 'interest'";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, interest);

            int rows = stmt.executeUpdate();
            // check if all rows of the table were updated. change should apply to all members
            return rows == 1;

        } finally {
            // close resources
            close();
        }
    }

    private void close() {
        if (result != null) {
            try {
                result.close();
            } catch (SQLException e) {
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
            }
        }
    }
}
