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

    public static boolean Logout(long id) throws AccountException {
        // incase the usr was an admin, unset the admin variable
        if (isAdmin()) {
            //setAdmin(false);
            return loggedInUsers.remove(id);
        } else {
            throw new AccountException("You are not authorized to perform this action");
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
            close();
        }
    }

    private void getAllMemberInfo() throws SQLException {
        String sql = "SELECT * FROM members";
        stmt = conn.prepareStatement(sql);
        result = stmt.executeQuery();
        while (result.next()) {
            Member m = new Member();
            m.t_id = result.getLong("id");
            m.setFirstname(result.getString("firstname"));
            m.setLastname(result.getString("lastname"));
            m.setAddress(result.getString("address"));
            m.setDob(result.getDate("dob"));
            m.setEmail(result.getString("email"));
            m.setGender(result.getString("gender"));
            m.setMobileno(result.getInt("mobileno"));
            allMembers.add(m);
        }
    }

    private ResultSet getAllContributions() throws SQLException {
        String sql = "SELECT * FROM contributions";
        stmt = conn.prepareStatement(sql);
        result = stmt.executeQuery();
        return result;
    }

    public void DisplayAllContributions(JComboBox combobox) throws SQLException {
        DefaultComboBoxModel Model = new DefaultComboBoxModel();
        try {
            result = getAllContributions();
            while (result.next()) {
                long ID = result.getLong("id");
                Model.addElement(ID);
            }
            combobox.setModel(Model);
        } finally {
            close();
        }
    }

    public void DisplayAllMembers(JComboBox combobox) throws SQLException {
        DefaultComboBoxModel Model = new DefaultComboBoxModel();
        // no need to re-execute the query while we already have a loaded list
        if (allMembers.isEmpty()) {
            getAllMemberInfo();
        }
        try {
            allMembers.stream().map((_member) -> _member.t_id).forEach((memberID) -> {
                Model.addElement(memberID);
            });
            combobox.setModel(Model);
        } finally {
            close();
        }
    }

    public void DisplayAllMembers(JTextArea jt) throws SQLException {
        jt.setText("");
        // no need to re-execute the query while we already have a loaded list
        if (allMembers.isEmpty()) {
            getAllMemberInfo();
        }
        try {
            jt.append("Here are all the members in the sacco \n\n");
            jt.append("MEMBER_ID\t\tFIRSTNAME\t\tLASTNAME\t\tGENDER\t\tDATE_OF_BIRTH\tADDRESS\t\tEMAIL\n\n");
            for (Member _member : allMembers) {
                jt.append(_member.t_id + "\t\t");
                jt.append(_member.getFirstname());
                jt.append("\t\t");
                jt.append(_member.getLastname());
                jt.append("\t\t");
                jt.append(_member.getGender());
                jt.append("\t\t");
                jt.append(_member.getDob().toString());
                jt.append("\t\t");
                jt.append(_member.getAddress());
                jt.append("\t\t");
                jt.append(_member.getEmail());
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
            close();
        }
    }

    public double getLoanInterest() throws SQLException {
        try {
            String sql = "SELECT value FROM settings WHERE name = 'interest'";
            stmt = conn.prepareStatement(sql);
            result = stmt.executeQuery();
            if (result.next()) {
                return result.getDouble("value");
            }

        } finally {
            close();
        }
        return -1;
    }

    public boolean ApproveORdissaproveContribution(long id, int Action) throws SQLException {
        String sql;
        if (Action == 1) {
            sql = "UPDATE `contributions` SET `Approved`=1 WHERE  `id`=?";
        } else if (Action == 0) {
            sql = "UPDATE `contributions` SET `Approved`=0 WHERE  `id`=?";
        } else {
            sql = "UPDATE `sacco`.`contributions` SET `Approved`=1 WHERE  `id`=?";
        }
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            int rows = stmt.executeUpdate();
            return rows == 1;

        } finally {
            close();
        }
    }

    public boolean ChangeLoanInterest(Loan l, double interest) throws SQLException {
        if (getLoanInterest() == -1) {
            throw new SQLException("An error occured while trying to get the loan interest from db");
        }
        try {
            String sql = "UPDATE settings SET value = ? WHERE name = 'interest'";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, interest);
            int rows = stmt.executeUpdate();
            return rows == 1;
        } finally {
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
