/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTextArea;

/**
 *
 * @author Antony
 */
public class Member {

    // define member datatypes as potrayed in the db
    private static long id;
    private String firstname;
    private String gender;
    private String lastname;
    private Date dob;
    private int mobileno;
    private String address;
    private String email;
    private String password;

    // for admin functions
    private static boolean admin = false;

    PreparedStatement stmt = null;
    database d = new database();
    Connection conn = d.DBConnection;
    ResultSet result = null;
    // store a logged in user. ive chosen a hashset it coz it won't allow duplicate keys. so a user can't login twice
    static Set<Long> hs = new HashSet<>();

    // get a DBConnection to the database via constructor
    public void Member() {
        this.conn = null;
        d = new database();
        conn = d.getConnection();
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
     * @param id the id to set
     */
    private void setId(long userid) {
        id = userid;
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
        this.password = password;
    }

    public boolean LoginMember(long id, String password) throws SQLException {
        try {
            String sql = "SELECT * FROM sacco.members WHERE id=? AND password=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.setString(2, password);
            result = stmt.executeQuery();
            while (result.next()) {
                // store logged in userID in mem
                setId(id);
                setFirstname(result.getString("firstname"));
                return hs.add(id);
            }
        } finally {
            close();
        }
        return false;

    }

    // check login status. for administrative use since any id can be passed,
    // and a basic user shouldn't be able to see other user's status
    protected static boolean CheckIfLoggedIn(long id) {
        return hs.contains(id);
    }

    public static boolean CheckLoggedIn() {
        return hs.contains(Member.getId());
    }

    // logout a user
    public static boolean Logout(long id) {
        // incase the usr was an admin, unset the admin variable
        if (admin) {
            admin = false;
        }
        return hs.remove(id);
    }

    // adds a member to the database
    public long AddMember() throws SQLException {
        try {
            //System.out.println(conn);
            //INSERT INTO `sacco`.`members` (`firstname`, `lastname`, `gender`, `dob`, `mobileno`, `address`, `email`, `password`) VALUES ('6666', '6666', 'Female', '2014-07-21', 666, '666', '666', '123456');
            String sql = "INSERT INTO `sacco`.`members` (`firstname`, `lastname`, `gender`, `dob`, `mobileno`, `address`, `email`, `password`) "
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
                this.setId(result.getLong(1));
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

    // an admin task. of course
    protected boolean DeleteMember(long id) throws SQLException {
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
        return false;
    }

    public void DisplayAllMembers(JTextArea jt) throws SQLException {
        if (isAdmin()) {
            try {
                String sql = "SELECT * FROM sacco.members";
                stmt = conn.prepareStatement(sql);
                result = stmt.executeQuery();

                jt.append("HERE are all the members in the sacco \n");

                while (result.next()) {
                    // store logged in userID in mem
                    setId(id);
                    jt.append(result.getString("firstname"));
                    jt.append("\n");
                    jt.append(result.getString("firstname"));
                    jt.append("\n");
                    jt.append(result.getString("gender"));
                    jt.append("\n");
                    jt.append(result.getDate("dob").toString());
                    jt.append("\n");
                    jt.append(result.getString("address"));
                    jt.append("\n");
                    jt.append(result.getString("email"));
                    jt.append("\n");
                }
            } finally {
                close();
            }
        }
    }

    protected long makeMemberAdmin(long id) throws SQLException {
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
                    setId(result.getLong(1));
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

    public boolean checkIfUserIsAdmin() throws SQLException {
        try {
            // SELECT `id`, `member_id` FROM `sacco`.`admins` WHERE  `id`=2;
            String sql = "SELECT `id`, `member_id` FROM `sacco`.`admins` WHERE  `member_id`=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
            result = stmt.executeQuery();

            while (result.next()) {
                admin = true;
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
