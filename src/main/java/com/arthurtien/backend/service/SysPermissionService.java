package com.arthurtien.backend.service;

import com.arthurtien.backend.model.SysPermission;

import java.util.List;

public interface SysPermissionService {
  List<SysPermission> getPermissionByRoleId(Integer roleId);
}
