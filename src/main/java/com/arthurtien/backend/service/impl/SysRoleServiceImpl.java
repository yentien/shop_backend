package com.arthurtien.backend.service.impl;

import com.arthurtien.backend.dao.SysRoleDao;
import com.arthurtien.backend.model.SysRole;
import com.arthurtien.backend.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SysRoleServiceImpl implements SysRoleService {

  @Autowired
  private SysRoleDao sysRoleDao;

  @Override
  public List<SysRole> getRoleByUser(Integer userId) {
    return sysRoleDao.getRoleByUser(userId);
  }
}
