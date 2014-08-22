package com.sacco.classes;

import com.sacco.Hashing.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.login.AccountException;
import javax.security.auth.login.LoginException;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import org.eclipse.persistence.internal.helper.Helper;

public class Session implements AutoCloseable {

    // necessary variables
    static Map<Long, String> loggedInUsers = new HashMap<>();
    private static long sessionID;

    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet result = null;

    public long getSessionID() {
        return sessionID;
    }

    private void setSessionID(long sessionID) {
        Session.sessionID = sessionID;
    }

    public Session(long memberID, String password) throws SQLException, LoginException {
        boolean Login = Login(memberID, password, null, false, true);
        if (!Login) {
            throw new LoginException("Login failed");
        }
    }

    public Session() {

    }

    // logout the current member and thanks to eclipse for its helper functions
    public boolean Logout() {
        try {
            conn = new Database().getConnection();
            String sql = "UPDATE `login_sessions` SET `logoutTime`=? WHERE  `id`=?";
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Helper.timestampFromDate(new Date()));
            stmt.setLong(2, getSessionID());
            if (Member.isAdmin()) {
                Member.setAdmin(false);
            }
            Session.loggedInUsers.remove(getSessionID());
            return stmt.executeUpdate() == 1 && Session.loggedInUsers.containsKey(getSessionID()) == false;
        } catch (SQLException ex) {
            return false;
        }
    }

    // check if a member is logged in
    public boolean CheckLoggedIn() {
        return Session.loggedInUsers.containsKey(getSessionID());
    }

    // display user's logged in info. just the current user
    public void getMyLoggedInInfo(JRootPane p) {
        if (loggedInUsers.containsKey(this.getSessionID())) {
            Long sess_id = 0L;
            String _fname = null;
            for (Map.Entry<Long, String> entry : loggedInUsers.entrySet()) {
                sess_id = entry.getKey();
                _fname = entry.getValue();
                break;
            }
            JOptionPane.showMessageDialog(p,
                    "<html><p> <b>Your memberID is " + Member.getId() + "</b> </p> <br> "
                    + "<p><b>Your session Id is " + sess_id + " </b> </p> <br> "
                    + "<p> <i> You are logged in as " + _fname + "</i> </p> </html>");
        }
    }

    // login a member
    /**
     *
     * @param memberID
     * <p>
     * the member's ID</p>
     * @param password
     * <p>
     * the member's password</p>
     * @param emailAddress
     * <p>
     * the member's email Address</p>
     * @param useID
     * <p>
     * specifies if we should use the user's id to log them in</p>
     * @param useEmail
     * <p>
     * specifies if we should use the user's email to log them in</p>
     * @return
     * <p>
     * True if login succeeded </p>
     * @throws SQLException
     * @throws AccountException
     * <p>
     * Thrown if the user's account is not activated </p>
     * @throws LoginException
     * <p>
     * Thrown when a user tries to login and is already logged in </p>
     */
    public final boolean Login(long memberID, String password, String emailAddress, boolean useID, boolean useEmail) throws SQLException, AccountException, LoginException {
        String sql;
        if (CheckLoggedIn()) {
            throw new LoginException("You are already logged in");
        }
        conn = new Database().getConnection();
        if (useID) {
            sql = "SELECT id, password, firstname, active FROM members WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, memberID);
        } else if (useEmail) {
            sql = "SELECT id, password, firstname, active FROM members WHERE email=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, emailAddress);
        } else {
            sql = "SELECT id, password, firstname, active FROM members WHERE id=? AND email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, memberID);
            stmt.setString(2, emailAddress);
        }

        try {

            result = stmt.executeQuery();
            if (result.next()) {
                if (!result.getBoolean("active")) {
                    throw new AccountException("Your account is not activated. Please contact the administrator to fix this");
                }
                if (BCrypt.CheckPassword(password, result.getString("password"))) {
                    long m_id = result.getLong("id");
                    Member.setId(m_id);
                    // save the user's session
                    String uname = result.getString("firstname");
                    SaveUserSession(Member.getId());
                    Session.loggedInUsers.put(getSessionID(), uname);
                    return Session.loggedInUsers.containsKey(getSessionID());
                } else {
                    return false;
                }
            }
        } finally {
            close();
        }
        return false;
    }

    // record a user's session in the db
    private void SaveUserSession(long userID) throws SQLException {
        String sql = "INSERT INTO `login_sessions` (`member_id`) VALUES (?)";
        stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setLong(1, userID);
        int rows = stmt.executeUpdate();
        if (rows == 0) {
            throw new SQLException("The session ID could not be created");
        }
        // get the returned inserted id
        result = stmt.getGeneratedKeys();
        if (result.next()) {
            setSessionID(result.getLong(1));
        } else {
            throw new SQLException("The session ID could not be created. an ID wasn't obtained");
        }

    }

    @Override
    public void close() {
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
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
            }
        }
    }

}
