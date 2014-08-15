package com.sacco.classes;

import static com.sacco.classes.Member.isAdmin;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.security.auth.login.AccountException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class Admin extends Member {

    public static int ADMIN_POSITION_ID = 1;

    // anyone instantiating this class will have to be an admin
    public Admin() throws AccountException {
        if (!Member.isAdmin()) {
            throw new AccountException("You are not authorized to perform this action");
        }
    }

    public static boolean Logout(long memberID) throws AccountException {
        // incase the usr was an admin, unset the admin variable
        if (isAdmin()) {
            //setAdmin(false);
            loggedInUsers.remove(memberID);
            return loggedInUsers.containsKey(memberID) == false;
        } else {
            throw new AccountException("You are not authorized to perform this action");
        }
    }

    public boolean AlterMemberAccount(long memberID, int status) throws SQLException, AccountException {
        String sql;
        this.conn = new Database().getConnection();
        if (status == 0) {
            // deactivate account
            sql = "UPDATE members SET active = 0 WHERE id = ?";
        } else if (status == 1) {
            // delete only an acitvated account
            sql = "DELETE FROM members WHERE id = ? AND active = 1";
        } else if (status == 2) {
            sql = "UPDATE members SET active = 1 WHERE id = ?";
        } else {
            // delete di akaunt
            sql = "DELETE FROM members WHERE id = ?";
        }
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, memberID);
            return stmt.executeUpdate() == 1;
        } finally {
            close();
        }
    }

    // stores member info in a list to be used later
    public void getAllMemberInfo() throws SQLException {
        this.conn = new Database().getConnection();
        String sql = "SELECT * FROM members";
        try {
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
        } finally {
            close();
        }
    }

    private ResultSet getAllContributions() throws SQLException {
        this.conn = new Database().getConnection();
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
        this.conn = new Database().getConnection();
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
    }

    public boolean AlterMemeberPosition(long memberID, String posname, int Action) throws SQLException {
        String sql;
        long pid = getPositionId(posname);
        this.conn = new Database().getConnection();
        // insert position
        if (Action == 1) {
            sql = "INSERT INTO `members_positions` (`position_id`, `member_id`) VALUES (?, ?)";
        } else {
            // update an existing members position
            sql = "UPDATE `members_positions` SET `position_id`=? WHERE  `member_id`=?";
        }
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, pid);
            stmt.setLong(2, memberID);
            return stmt.executeUpdate() == 1;
        } finally {
            close();
        }
    }

    public boolean AlterMemberContribution(long contributionID, int Action) throws SQLException {
        this.conn = new Database().getConnection();
        String sql;
        if (Action == 0) {
            // dissaprove contribution
            sql = "UPDATE `contributions` SET `Approved`=0 WHERE  `id`=?";
        } else if (Action == 1) {
            // approve contribution
            sql = "UPDATE `contributions` SET `Approved`=1 WHERE  `id`=?";
        } else {
            // delete contribution
            sql = "DELETE FROM `contributions` WHERE `id`=?";
        }
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, contributionID);
            return stmt.executeUpdate() == 1;
        } finally {
            close();
        }
    }

    public boolean ChangeLoanInterest(double interest) throws SQLException {
        this.conn = new Database().getConnection();
        try {
            String sql = "UPDATE settings SET value = ? WHERE name = 'interest'";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, interest);
            return stmt.executeUpdate() == 1;
        } finally {
            close();
        }
    }
}
