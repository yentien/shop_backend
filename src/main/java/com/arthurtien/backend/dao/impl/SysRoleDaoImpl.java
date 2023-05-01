package com.arthurtien.backend.dao.impl;

import com.arthurtien.backend.dao.SysRoleDao;
import com.arthurtien.backend.model.SysRole;
import com.arthurtien.backend.rowmapper.RoleRowMapper;
import com.arthurtien.backend.secuirty.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class SysRoleDaoImpl implements SysRoleDao {

  private Logger log = Logger.getLogger(SecurityConfig.class.getName());

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public List<SysRole> getRoleByUser(Integer userId) {
    String sql = "SELECT r.id,  r.code, r.name" +
        " FROM m_user_roles m" +
        " LEFT JOIN role r on r.id = m.r_id" +
        " WHERE u_id = :userId";

    Map<String, Object> map = new HashMap<>();
    map.put("userId", userId);

    List<SysRole> roleList = namedParameterJdbcTemplate.query(sql,map, new RoleRowMapper());
    return roleList;
  }
}
