package com.sacco.classes;

import com.sacco.classes.vendor.Bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.login.AccountException;

public class Member {

    // define member datatypes as potrayed in the db
    // store a logged in user. ive chosen a hashset it coz it won't allow duplicate keys. so a user can't login twice
    static Set<Long> hs = new HashSet<>();
    // for account active/ not. by default, a created account is active
    public static final short ACTIVE = 1;
    public static final short INACTIVE = 0;
    // the member id
    protected static long id;
    // for admin functions
    private static boolean admin = false;

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

    // the variables
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
        // hash the password. optional
        this.password = BCrypt.Hash(password, BCrypt.generateSalt());
    }

    public boolean LoginMember(long id, String password) throws SQLException, AccountException {
        // makes sense to prevent double logins, even if the hashset won't allow that
        if (Member.CheckLoggedIn()) {
            throw new AccountException("You are already logged in");
        }
        try {
            String sql = "SELECT password, firstname, active FROM sacco.members WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            result = stmt.executeQuery();
            while (result.next()) {
                // check if the account is active
                if (result.getShort("active") == 0) {
                    throw new AccountException("Your account is not activated. Please contact the administrator to fix this");
                }
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
    public long AddMember() throws SQLException, AccountException {
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
                // LoginMember(id, password);
                return Member.getId();
            } else {
                throw new SQLException("The specified user wasn't created. an ID wasn't obtained");
            }
        } finally {
            // close resources
            close();
        }

    }

    // popultes all variables with member data to be used afterwards
    public void getMemberInfo(Member m) throws SQLException {
        try {
            // SELECT `id`, `member_id` FROM `sacco`.`admins` WHERE  `id`=2;
            String sql = "SELECT * FROM `sacco`.`members` WHERE  `id`=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Member.getId());
            result = stmt.executeQuery();

            while (result.next()) {
                m.lastname = result.getString("lastname");
                m.address = result.getString("address");
                m.firstname = result.getString("firstname");
                m.gender = result.getString("gender");
                m.email = result.getString("email");
                m.dob = result.getDate("dob");
                m.mobileno = result.getInt("mobileno");
                m.password = result.getString("password");
            }
        } finally {
            close();
        }

    }

    // edit member password
    public boolean EditPassword(Member m, String password) throws AccountException, SQLException {
        if (CheckLoggedIn()) {
            try {
                getMemberInfo(m);
                // retrieve hash from the database.
                // if bcrypt realizes that the hashes match, then alert the user
                // boolean force will allow the user to override the exception thrown
                if (BCrypt.CheckPassword(password, m.password)) {
                    throw new AccountException("Please try another password?. Your password is similar to the one in the database");
                }
                // bcrypt the password
                password = BCrypt.Hash(password, BCrypt.generateSalt());

                // now comes the sql part of the activity
                String sql = "UPDATE `sacco`.`members` SET `password`=? WHERE  `id`= ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, password);
                stmt.setLong(2, Member.getId());

                int rows = stmt.executeUpdate();
                // we need exactly one row updated
                return rows == 1;

            } finally {
                // close resources
                close();
            }
        } else {
            throw new AccountException("You aren't allowed to perform this action");
        }
    }

    // edit member data
    public boolean EditMemberInfo(Member m) throws AccountException, SQLException {
        if (CheckLoggedIn()) {
            try {
                //getMemberInfo(m);
                // the sql
                // UPDATE `sacco`.`members` SET `firstname`='rgrgs', `lastname`='rgrgs', `gender`='Females', `dob`='2015-07-25', `mobileno`=343443, `address`='343434', `email`='a@b.c.no\'\'\'', `password`='556465' WHERE  `id`=11;
                String sql = "UPDATE `sacco`.`members` SET `firstname`=?, `lastname`=?, `gender`=?, `dob`=?, `mobileno`=?, `address`=?, `email`=? WHERE  `id`=?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, m.firstname);
                stmt.setString(2, m.lastname);
                stmt.setString(3, m.gender);
                stmt.setDate(4, m.dob);
                stmt.setInt(5, m.mobileno);
                stmt.setString(6, m.address);
                stmt.setString(7, m.email);
                stmt.setLong(8, Member.getId());

                int rows = stmt.executeUpdate();
                // we need exactly one row updated
                return rows == 1;

            } finally {
                // close resources
                close();
            }
        } else {
            throw new AccountException("You aren't allowed to perform this action");
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
