package com.sacco.classes;

import com.sacco.classes.vendor.Bcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.security.auth.login.AccountException;
import javax.security.auth.login.LoginException;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

public class Member implements AutoCloseable {

    protected static long id;
    private String firstname;
    private String gender;
    private String lastname;
    private Date dob;
    private long mobileno;
    private String address;
    private String email;
    private String password;
    private Timestamp RegisteredDate;
    private Timestamp LastModifiedDate;
    private boolean AccountStatus;
    protected long t_id = 0;

    // for account active/ not. by default, a created account is active
    public static final short ACCOUNT_ACTIVE = 1;
    public static final short ACCOUNT_INACTIVE = 0;
    private static boolean admin = false;

    // define member datatypes as potrayed in the db
    //static HashSet<Long> loggedInUsers = new HashSet<>();
    static Map<Long, String> loggedInUsers = new HashMap<>();
    protected List<Member> allMembers = new ArrayList<>();

    // for Database stuff
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet result = null;

    protected static void setAdmin(boolean status) {
        admin = status;
    }

    public static long getId() {
        return id;
    }

    public static boolean isAdmin() {
        return admin;
    }

    protected static void setId(long userid) {
        id = userid;
    }

    public static boolean CheckLoggedIn() {
        return loggedInUsers.containsKey(Member.getId());
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public long getMobileno() {
        return mobileno;
    }

    public void setMobileno(long mobileno) {
        this.mobileno = mobileno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // hash the password. never optional but in this case it is. ata bcrypt nkatoa bana
        this.password = BCrypt.Hash(password, BCrypt.generateSalt());
    }

    public Timestamp getRegisteredDate() {
        return RegisteredDate;
    }

    public Timestamp getLastModifiedDate() {
        return LastModifiedDate;
    }

    public boolean getAccountStatus() {
        return AccountStatus;
    }

    // login a member
    public boolean Login(long memberID, String password) throws SQLException, AccountException, LoginException {
        if (Member.CheckLoggedIn()) {
            throw new LoginException("You are already logged in");
        }
        this.conn = new Database().getConnection();
        try {
            String sql = "SELECT password, firstname, active FROM members WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, memberID);
            result = stmt.executeQuery();
            if (result.next()) {
                // check if the account is active or not
                if (!result.getBoolean("active")) {
                    throw new AccountException("Your account is not activated. Please contact the administrator to fix this");
                }
                // check thi password
                if (BCrypt.CheckPassword(password, result.getString("password"))) {
                    Member.setId(memberID);
                    // reset the login errors variable
                    // add member details into the hashmap
                    loggedInUsers.put(memberID, result.getString("firstname"));
                    return loggedInUsers.containsKey(memberID);
                } else {
                    return false;
                }
            }
        } finally {
            close();
        }
        return false;
    }

    public void getMyLoggedInInfo(JRootPane p) {
        for (Map.Entry<Long, String> entry : loggedInUsers.entrySet()) {
            if (loggedInUsers.containsKey(Member.id)) {
                Long m_id = entry.getKey();
                String _fname = entry.getValue();
                JOptionPane.showMessageDialog(p, "<html> <p><b>Your User Id is " + m_id + " </b> </p> <br> <p> <i> You are logged in as " + _fname + "</i> </p> </html>");
            }
            break;
        }
    }

    // logout the current member
    public static boolean Logout() {
        // incase the usr was an admin, unset the admin variable
        if (isAdmin()) {
            setAdmin(false);
        }
        loggedInUsers.remove(Member.id);
        return loggedInUsers.containsKey(Member.id) == false;
    }

    public long AddMember() throws SQLException, AccountException {
        this.conn = new Database().getConnection();
        try {
            String sql
                    = "INSERT INTO `members` "
                    + "(`firstname`, `lastname`, `gender`, `dob`,"
                    + " `mobileno`, `address`, `email`, `password`) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, getFirstname());
            stmt.setString(2, getLastname());
            stmt.setString(3, getGender());
            stmt.setDate(4, getDob());
            stmt.setLong(5, getMobileno());
            stmt.setString(6, getAddress());
            stmt.setString(7, getEmail());
            stmt.setString(8, getPassword());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("The specified user wasn't created");
            }
            // get the returned inserted id
            result = stmt.getGeneratedKeys();
            if (result.next()) {
                return result.getLong(1);
            } else {
                throw new SQLException("The specified user wasn't created. an ID wasn't obtained");
            }
        } finally {
            close();
        }
    }

    public long getPositionId(String posname) throws SQLException {
        this.conn = new Database().getConnection();
        try {
            String sql = "SELECT `id` FROM positions WHERE `name` LIKE ? LIMIT 1";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + posname + "%");
            result = stmt.executeQuery();
            if (result.next()) {
                return result.getLong("id");
            }
        } finally {
            close();
        }
        return -1;
    }

    public void getMemberInfo(Member m, long memberID) throws SQLException {
        // if a user invokes this as a normal user, then we don't need them passing any memberID in. just default to theirs
        if (!isAdmin()) {
            memberID = Member.getId();
        }
        this.conn = new Database().getConnection();
        try {
            String sql = "SELECT * FROM `members` WHERE  `id`=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, memberID);
            result = stmt.executeQuery();
            while (result.next()) {
                m.lastname = result.getString("lastname");
                m.address = result.getString("address");
                m.firstname = result.getString("firstname");
                m.gender = result.getString("gender");
                m.email = result.getString("email");
                m.dob = result.getDate("dob");
                m.mobileno = result.getLong("mobileno");
                m.password = result.getString("password");
                m.RegisteredDate = result.getTimestamp("DateRegistered");
                m.LastModifiedDate = result.getTimestamp("DateModified");
                m.AccountStatus = result.getBoolean("active");
            }
        } finally {
            close();
        }
    }

    public boolean EditPassword(String password, long memberID) throws AccountException, SQLException {
        if (!isAdmin()) {
            memberID = Member.getId();
        }
        this.conn = new Database().getConnection();
        if (CheckLoggedIn()) {
            try {
                password = BCrypt.Hash(password, BCrypt.generateSalt());
                String sql = "UPDATE `members` SET `password`=? WHERE `id`= ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, password);
                stmt.setLong(2, memberID);
                return stmt.executeUpdate() == 1;
            } finally {
                close();
            }
        } else {
            throw new AccountException("You aren't allowed to perform this action");
        }
    }

    public boolean EditMemberInfo(Member m) throws AccountException, SQLException {
        this.conn = new Database().getConnection();
        if (CheckLoggedIn()) {
            try {
                String sql = "UPDATE `members` SET "
                        + "`firstname`=?, `lastname`=?, `gender`=?, `dob`=?, "
                        + "`mobileno`=?, `address`=?, `email`=? WHERE  `id`=?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, m.firstname);
                stmt.setString(2, m.lastname);
                stmt.setString(3, m.gender);
                stmt.setDate(4, m.dob);
                stmt.setLong(5, m.mobileno);
                stmt.setString(6, m.address);
                stmt.setString(7, m.email);
                stmt.setLong(8, Member.getId());
                return stmt.executeUpdate() == 1;
            } finally {
                close();
            }
        } else {
            throw new AccountException("You aren't allowed to perform this action");
        }
    }

    public int checkUserPosition() throws SQLException {
        this.conn = new Database().getConnection();
        if (CheckLoggedIn()) {
            try {
                String sql = "SELECT id FROM positions JOIN members_positions ON positions.id = members_positions.position_id AND member_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setLong(1, Member.getId());
                result = stmt.executeQuery();
                while (result.next()) {
                    if (result.getInt("id") == Admin.ADMIN_POSITION_ID) {
                        setAdmin(true);
                        return 1;
                    }
                    if (result.getInt("id") == Secretary.SEC_POSITION_ID) {
                        return 2;
                    } else {
                        return result.getInt("id");
                    }
                }
            } finally {
                close();
            }
        }
        return -1;
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
