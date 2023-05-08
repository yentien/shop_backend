package com.arthurtien.backend.service.impl;

import com.arthurtien.backend.dao.MUserRoleDao;
import com.arthurtien.backend.dao.SysRoleDao;
import com.arthurtien.backend.dao.SysUserDao;
import com.arthurtien.backend.dto.UserLoginRequest;
import com.arthurtien.backend.dto.UserModifyRequest;
import com.arthurtien.backend.dto.UserRegisterRequest;
import com.arthurtien.backend.model.SysRole;
import com.arthurtien.backend.model.SysUser;
import com.arthurtien.backend.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class SysUserServiceImpl implements SysUserService {

  private final static Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

  @Autowired
  private SysUserDao sysUserDao;

  @Autowired
  private SysRoleDao sysRoleDao;

  @Autowired
  private MUserRoleDao mUserRoleDao;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public List<SysUser> getUser() {
    List<SysUser> userList = sysUserDao.getUser();
    for (SysUser user : userList) {
      user.setRoleName(sysRoleDao.getRoleByUserId(user.getUserId()));
    }
    return userList;
  }

  @Override
  public void modifyUser(Integer userId, UserModifyRequest userModifyRequest) {
    sysUserDao.modifyUser(userId, userModifyRequest);
  }

  @Override
  public SysUser getUserByEmail(String email) {
    SysUser user = sysUserDao.getUserByEmail(email);
    if (user != null) {
      String roleName = sysRoleDao.getRoleByUserId(user.getUserId());
      if (roleName != null) {
        user.setRoleName(roleName);
      }
      return user;
    }
    return null;
  }

  @Override
  public SysUser login(UserLoginRequest userLoginRequest) {
    SysUser sysUser = sysUserDao.getUserByEmail(userLoginRequest.getEmail());

    if (sysUser == null) {
      log.warn("無此帳號或 {} 密碼不正確", userLoginRequest.getEmail());
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    if(passwordEncoder.matches(userLoginRequest.getPassword(), sysUser.getPassword())){
      return sysUser;
    } else {
      log.warn("無此帳號或 {} 密碼不正確", userLoginRequest.getEmail());
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public Integer register(UserRegisterRequest userRegisterRequest) {
    // 密碼加密，替換到password裡
    String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
    userRegisterRequest.setPassword(encodedPassword);

    Integer userId = sysUserDao.createUser(userRegisterRequest);
    mUserRoleDao.createMUserRole(userId);
    return userId;
  }

  @Override
  public SysUser getUserById(Integer userId) {
    return sysUserDao.getUserById(userId);
  }
}
