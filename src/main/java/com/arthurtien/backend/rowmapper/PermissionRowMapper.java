package com.arthurtien.backend.rowmapper;

import com.arthurtien.backend.model.SysPermission;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PermissionRowMapper implements RowMapper<SysPermission>{
  @Override
  public SysPermission mapRow(ResultSet rs, int rowNum) throws SQLException {
    SysPermission sysPermission = new SysPermission();
    sysPermission.setId(rs.getInt("id"));
    sysPermission.setCode(rs.getString("code"));
    sysPermission.setName(rs.getString("name"));
    sysPermission.setResource(rs.getString("resource"));
    return sysPermission;
  }
}
