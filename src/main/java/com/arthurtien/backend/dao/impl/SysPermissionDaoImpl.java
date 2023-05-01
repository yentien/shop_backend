package com.arthurtien.backend.dao.impl;

import com.arthurtien.backend.dao.SysPermissionDao;
import com.arthurtien.backend.model.SysPermission;
import com.arthurtien.backend.rowmapper.PermissionRowMapper;
import com.arthurtien.backend.secuirty.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class SysPermissionDaoImpl implements SysPermissionDao {
  private Logger log = Logger.getLogger(SysPermissionDaoImpl.class.getName());

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public List<SysPermission> getPermissionByRoleId(Integer roleId) {
    String sql = "SELECT p.id, p.code, p.name, p.resource" +
        " FROM m_role_permission m" +
        " LEFT JOIN permission p on p.id = m.p_id" +
        " WHERE m.r_id = :roleId;";

    Map<String, Object> map = new HashMap<>();
    map.put("roleId", roleId);

    List<SysPermission> permissionList = namedParameterJdbcTemplate.query(sql, map, new PermissionRowMapper());

    return permissionList;
  }
}
