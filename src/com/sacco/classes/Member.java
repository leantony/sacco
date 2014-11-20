package com.sacco.classes;

import com.sacco.Hashing.BCrypt;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Member implements DatabaseInterface<Member> {

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

    protected List<Member> allMembers = new ArrayList<>();


    // for Database stuff
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet result = null;

    // <editor-fold defaultstate="collapsed" desc="the obvious getters and setters">
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
    //</editor-fold>

    public boolean checkMobileExists(long mno) {
        try {
            this.conn = new Database().getConnection();
            String sql = "SELECT mobileno FROM members WHERE mobileno = ?";
            stmt = conn.prepareStatement(sql);
            result = stmt.executeQuery();
        } catch (SQLException ex) {
            //
        }
        return result == null;
    }

    public boolean checkEmailExists(String email) {
        try {
            this.conn = new Database().getConnection();
            String sql = "SELECT email FROM members WHERE email = ?";
            stmt = conn.prepareStatement(sql);
            result = stmt.executeQuery();
        } catch (SQLException ex) {
            //
        }
        return result == null;
    }

    @Override
    public long Add(Member value) throws SQLException {
        this.conn = new Database().getConnection();
        try {
            String sql
                    = "INSERT INTO `members` "
                    + "(`firstname`, `lastname`, `gender`, `dob`,"
                    + " `mobileno`, `address`, `email`, `password`) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, value.getFirstname());
            stmt.setString(2, value.getLastname());
            stmt.setString(3, value.getGender());
            stmt.setDate(4, value.getDob());
            stmt.setLong(5, value.getMobileno());
            stmt.setString(6, value.getAddress());
            stmt.setString(7, value.getEmail());
            stmt.setString(8, value.getPassword());

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

    @Override
    public boolean Update(Member value) throws SQLException {
        this.conn = new Database().getConnection();
        try {
            String sql = "UPDATE `members` SET "
                    + "`firstname`=?, `lastname`=?, `gender`=?, `dob`=?, "
                    + "`mobileno`=?, `address`=?, `email`=? WHERE  `id`=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, value.getFirstname());
            stmt.setString(2, value.getLastname());
            stmt.setString(3, value.getGender());
            stmt.setDate(4, value.getDob());
            stmt.setLong(5, value.getMobileno());
            stmt.setString(6, value.getAddress());
            stmt.setString(7, value.getEmail());
            stmt.setLong(8, Member.getId());
            return stmt.executeUpdate() == 1;
        } finally {
            close();
        }
    }

    @Override
    public boolean Delete(long ID) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Member> select() throws SQLException {
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
                m.setMobileno(result.getLong("mobileno"));
                allMembers.add(m);
            }
            return allMembers;
        } finally {
            close();
        }
    }

    @Override
    public List<Member> select(long ID) throws SQLException {
        this.conn = new Database().getConnection();
        String sql = "SELECT * FROM members WHERE `id` = ?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, ID);
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
                m.setMobileno(result.getLong("mobileno"));
                allMembers.add(m);
            }
            return allMembers;
        } finally {
            close();
        }
    }

    public boolean EditPassword(String password, long memberID) throws SQLException {
        if (!isAdmin()) {
            memberID = Member.getId();
        }
        this.conn = new Database().getConnection();
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
