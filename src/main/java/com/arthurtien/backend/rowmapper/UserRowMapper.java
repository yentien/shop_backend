package com.arthurtien.backend.rowmapper;

import com.arthurtien.backend.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreatedDate(rs.getTimestamp("created_date"));
        user.setLastModifiedDate(rs.getTimestamp("last_modified_date"));
        user.setName(rs.getString("name"));
        user.setRole(rs.getString("role"));
        user.setCellphone(rs.getString("cellphone"));
        user.setAddress(rs.getString("address"));
        user.setGender(rs.getString("gender"));

        return user;
    }
}
