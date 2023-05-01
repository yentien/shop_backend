package com.arthurtien.backend.service.impl;

import com.arthurtien.backend.dao.SysPermissionDao;
import com.arthurtien.backend.model.SysPermission;
import com.arthurtien.backend.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SysPermissioniServiceImpl implements SysPermissionService {

  @Autowired
  private SysPermissionDao sysPermissionDao;

  @Override
  public List<SysPermission> getPermissionByRoleId(Integer roleId) {
    return sysPermissionDao.getPermissionByRoleId(roleId);
  }
}
