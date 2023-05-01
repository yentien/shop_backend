package com.arthurtien.backend.rowmapper;

import com.arthurtien.backend.model.SysRole;
import com.arthurtien.backend.model.SysUser;
import com.arthurtien.backend.secuirty.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class UserRowMapper implements RowMapper<SysUser> {

    private Logger log = Logger.getLogger(UserRowMapper.class.getName());

    @Override
    public SysUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(rs.getInt("user_id"));
        sysUser.setEmail(rs.getString("email"));
        sysUser.setPassword(rs.getString("password"));
        sysUser.setCreatedDate(rs.getTimestamp("created_date"));
        sysUser.setLastModifiedDate(rs.getTimestamp("last_modified_date"));
        sysUser.setName(rs.getString("name"));
        sysUser.setCellphone(rs.getString("cellphone"));
        sysUser.setAddress(rs.getString("address"));
        sysUser.setGender(rs.getString("gender"));

        return sysUser;
    }
}
