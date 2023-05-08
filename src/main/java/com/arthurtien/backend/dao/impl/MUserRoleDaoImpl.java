package com.arthurtien.backend.dao.impl;

import com.arthurtien.backend.dao.MUserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MUserRoleDaoImpl implements MUserRoleDao {

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public void createMUserRole(Integer userId) {
    String sql = "INSERT INTO m_user_roles (u_id, r_id) VALUES (:userId, :roleId)";
    Map<String, Object> map = new HashMap<>();
    map.put("userId", userId);
    map.put("roleId", "2"); // 一般使用者

    namedParameterJdbcTemplate.update(sql, map);
  }
}
