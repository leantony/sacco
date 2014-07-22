/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacco.classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import com.sacco.classes.vendor.Bcrypt.BCrypt;

/**
 *
 * @author Antony
 */
public class Member {

    // define member datatypes as potrayed in the db
    // store a logged in user. ive chosen a hashset it coz it won't allow duplicate keys. so a user can't login twice
    static Set<Long> hs = new HashSet<>();
    protected static long id;
    // for admin functions
    private static boolean admin = false;

    /**
     * @param aAdmin the admin to set
     */
    protected static void setAdmin(boolean aAdmin) {
        admin = aAdmin;
    }

    /**
     * @return the id
     */
    public static long getId() {
        return id;
    }

    /**
     * @return the admin
     */
    public static boolean isAdmin() {
        return admin;
    }

    /**
     * @param userid
     */
    protected static void setId(long userid) {
        id = userid;
    }

    public static boolean CheckLoggedIn() {
        return hs.contains(Member.getId());
    }

    // logout the current member
    public static boolean Logout() {
        // incase the usr was an admin, unset the admin variable
        if (isAdmin()) {
            setAdmin(false);
        }
        // JOptionPane.showMessageDialog(null, hs.contains(Member.getId()));
        return hs.remove(Member.getId());
    }

    private String firstname;
    private String gender;
    private String lastname;
    private Date dob;
    private int mobileno;
    private String address;
    private String email;
    private String password;

    // for database stuff
    PreparedStatement stmt = null;
    database d = new database();
    Connection conn = d.DBConnection;
    ResultSet result = null;

    // get a DBConnection to the database via constructor
    public void Member() {
        this.conn = null;
        d = new database();
        conn = d.getConnection();
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * @param dob the dob to set
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * @return the mobileno
     */
    public int getMobileno() {
        return mobileno;
    }

    /**
     * @param mobileno the mobileno to set
     */
    public void setMobileno(int mobileno) {
        this.mobileno = mobileno;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        // hash the password. optional
        this.password = BCrypt.Hash(password, BCrypt.generateSalt());
    }

    public boolean LoginMember(long id, String password) throws SQLException, IllegalArgumentException {
        try {
            String sql = "SELECT password, firstname FROM sacco.members WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            result = stmt.executeQuery();
            if (result.next()) {
                // we validate their hash
                if (BCrypt.CheckPassword(password, result.getString("password"))) {
                    // store logged in userID in mem
                    Member.setId(id);
                    // this will be used to get the user's name and display it in the logged in user's index page
                    setFirstname(result.getString("firstname"));
                    // a successful addition of the user id to the hashset will indicate a valid login
                    return hs.add(id);
                }

            }
        } finally {
            close();
        }
        return false;

    }

    // adds a member to the database
    public long AddMember() throws SQLException {
        try {
            //System.out.println(conn);
            //INSERT INTO `sacco`.`members` (`firstname`, `lastname`, `gender`, `dob`, `mobileno`, `address`, `email`, `password`) VALUES ('6666', '6666', 'Female', '2014-07-21', 666, '666', '666', '123456');
            String sql
                    = "INSERT INTO `sacco`.`members` (`firstname`, `lastname`, `gender`, `dob`, `mobileno`, `address`, `email`, `password`) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, getFirstname());
            stmt.setString(2, getLastname());
            stmt.setString(3, getGender());
            stmt.setDate(4, getDob());
            stmt.setInt(5, getMobileno());
            stmt.setString(6, getAddress());
            stmt.setString(7, getEmail());
            stmt.setString(8, getPassword());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                // a user wasn't added
                throw new SQLException("The specified user wasn't created");
            }

            // get the returned inserted id
            result = stmt.getGeneratedKeys();
            if (result.next()) {
                Member.setId(result.getLong(1));
                // login the member automatically
                LoginMember(id, password);
                return Member.getId();
            } else {
                throw new SQLException("The specified user wasn't created. an ID wasn't obtained");
            }
        } finally {
            // close resources
            close();
        }

    }

    // the current user should be able to delete their account
    public boolean CloseAccount() throws SQLException {
        try {
            //System.out.println(conn);
            String sql = "DELETE FROM sacco.members WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());

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
    public boolean checkIfUserIsAdmin() throws SQLException {
        try {
            // SELECT `id`, `member_id` FROM `sacco`.`admins` WHERE  `id`=2;
            String sql = "SELECT `id`, `member_id` FROM `sacco`.`admins` WHERE  `member_id`=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
            result = stmt.executeQuery();

            while (result.next()) {
                setAdmin(true);
                return isAdmin();
            }
        } finally {
            close();
        }
        return false;
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
