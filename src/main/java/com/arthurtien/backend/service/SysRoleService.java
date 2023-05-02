package com.arthurtien.backend.service;

import com.arthurtien.backend.model.SysRole;

import java.util.List;

public interface SysRoleService {
  List<SysRole> getRoleByUser(Integer userId);
}
