package com.sacco.classes;

import java.sql.SQLException;
import java.sql.Statement;

public class Secretary extends Member {

    public static int SEC_POSITION_ID = 2;

    public boolean ListAllQueries() {
        return false;
    }

    public int RecordMinutes(String minutes, String title) throws SQLException {
        this.conn = new Database().getConnection();
        try {
            String sql = "INSERT INTO `minutes` (`member_id`, `title`, `content`) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, Member.getId());
            stmt.setString(2, title);
            stmt.setString(3, minutes);

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("The minutes weren't created");
            }
            // get the returned inserted id
            result = stmt.getGeneratedKeys();
            if (result.next()) {
                return result.getInt(1);
            } else {
                throw new SQLException("The minutes weren't recorded. an ID wasn't obtained");
            }
        } finally {
            close();
        }
    }
}
