package com.arthurtien.backend.dao;

import com.arthurtien.backend.model.SysPermission;

import java.util.List;

public interface SysPermissionDao {
  List<SysPermission> getPermissionByRoleId(Integer roleId);
}
