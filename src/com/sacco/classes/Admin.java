/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacco.classes;

import static com.sacco.classes.Member.getId;
import static com.sacco.classes.Member.isAdmin;
import java.sql.SQLException;
import java.sql.Statement;
import javax.security.auth.login.AccountException;
import javax.swing.JTextArea;

/**
 *
 * @author Antony
 */
public class Admin extends Member {

    // handy, since it will be always checked on instance creation
    public Admin() throws AccountException {
        // check if the user is who they claim to be
        if (!Member.isAdmin()) {
            throw new AccountException("Are you really an admin?. Check again");
        }
    }

    // check login status. for administrative use since any id can be passed,
    // and a basic user shouldn't be able to see other user's status
    protected static boolean CheckIfLoggedIn(long id) {
        return hs.contains(id);
    }

    // logout a user by their id
    public static boolean Logout(long id) {
        // incase the usr was an Admin, unset the Admin variable
        if (isAdmin()) {
            setAdmin(false);
        }
        return hs.remove(id);
    }

    // an Admin task. of course
    public boolean DeleteMember(long id) throws SQLException, AccountException {
        if (isAdmin()) {
            try {
                //System.out.println(conn);
                String sql = "DELETE FROM sacco.members WHERE id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setLong(1, id);

                // PreparedStatement.setString(8, getPassword());
                int rows = stmt.executeUpdate();
                if (rows == 0) {
                    // a user wasn't removed
                    throw new SQLException("Deletion failed");
                } else {
                    return true;
                }

            } finally {
                // close resources
                close();
            }
        }
        throw new AccountException("You are not allowed to perform this action");
    }

    // the admin should be able to close another user's account
    public boolean CloseAccount(long id) throws SQLException {
        try {
            //System.out.println(conn);
            String sql = "UPDATE sacco.members SET active = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setShort(1, Member.INACTIVE);
            stmt.setLong(2, id);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                // a user wasn't removed
                throw new SQLException("Operation failed");
            } else {
                return true;
            }

        } finally {
            // close resources
            close();
        }
    }

    public void DisplayAllMembers(JTextArea jt) throws SQLException, AccountException {

        jt.setText("");
        try {
            String sql = "SELECT * FROM sacco.members";
            stmt = conn.prepareStatement(sql);
            result = stmt.executeQuery();

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

    public long makeMemberAdmin(long id) throws SQLException {
        if (isAdmin()) {
            try {
                //System.out.println(conn);
                String sql = "INSERT INTO sacco.admins (member_id) VALUES (?)";
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setLong(1, id);
                int rows = stmt.executeUpdate();
                if (rows == 0) {
                    // a user wasn't added
                    throw new SQLException("operation failed");
                }

                // get the returned inserted id
                result = stmt.getGeneratedKeys();
                if (result.next()) {
                    Member.setId(result.getLong(1));
                    return getId();
                } else {
                    throw new SQLException("Operation failed. an ID wasn't obtained");
                }
            } finally {
                // close resources
                close();
            }
        }
        return -1;

    }

    public boolean ClearMemberLoan(long id) throws SQLException, AccountException {
        if (isAdmin()) {
            try {
                //System.out.println(conn);
                String sql = "DELETE FROM sacco.Loans WHERE member_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setLong(1, id);

                // PreparedStatement.setString(8, getPassword());
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
