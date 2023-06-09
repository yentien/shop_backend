package com.arthurtien.backend.rowmapper;

import com.arthurtien.backend.model.SysRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class RoleRowMapper implements RowMapper<SysRole> {

  private Logger log = Logger.getLogger(RoleRowMapper.class.getName());

  @Override
  public SysRole mapRow(ResultSet rs, int rowNum) throws SQLException {
    SysRole sysRole = new SysRole();
    sysRole.setId(rs.getInt("role_id"));
    sysRole.setCode(rs.getString("role_code"));
    sysRole.setName(rs.getString("role_name"));
    return sysRole;
  }
}
