package com.arthurtien.backend.dao;

import com.arthurtien.backend.model.SysRole;

import java.util.List;

public interface SysRoleDao {
  String getRoleByUserId(Integer userId);
  List<SysRole> getRoleByUser(Integer userId);
}
