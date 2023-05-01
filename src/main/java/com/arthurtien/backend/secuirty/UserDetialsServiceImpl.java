package com.arthurtien.backend.secuirty;

import com.arthurtien.backend.model.SysPermission;
import com.arthurtien.backend.model.SysRole;
import com.arthurtien.backend.model.SysUser;
import com.arthurtien.backend.service.SysPermissionService;
import com.arthurtien.backend.service.SysRoleService;
import com.arthurtien.backend.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Component
public class UserDetialsServiceImpl implements UserDetailsService {

  private Logger log = Logger.getLogger(SecurityConfig.class.getName());

  @Autowired
  @Lazy
  private SysUserService sysUserService;

  @Autowired
  private SysRoleService sysRoleService;

  @Autowired
  private SysPermissionService sysPermissionService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    //從資料庫中取出用戶訊息
    SysUser sysUser = sysUserService.getUserByEmail(email);
    // 判斷用戶是否存在
    if (sysUser == null) {
      throw new UsernameNotFoundException("用戶名不存在");
    }

    // 添加角色
    List<SysRole> roleList = sysRoleService.getRoleByUser(sysUser.getUserId());
    if (null == roleList || roleList.isEmpty() ) {
      throw new UsernameNotFoundException("role not found");
    }
    // 把角色放進authorities
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    for (SysRole sysRole: roleList) {
      authorities.add(new SimpleGrantedAuthority(sysRole.getCode()));

      // 放入角色擁有的權限級別
      List<SysPermission> permissionList = sysPermissionService.getPermissionByRoleId(sysRole.getId());
      if (null == permissionList || permissionList.isEmpty() ) {
        throw new UsernameNotFoundException("permission not found");
      }
      for (SysPermission sysPermission : permissionList) {
        authorities.add(new SimpleGrantedAuthority(sysPermission.getCode()));
      }
    }

    return new User(sysUser.getEmail(), sysUser.getPassword(), authorities);
  }

  // 轉換成自身業務的User
//  public SysUser toUser() {
//    User user = (User) SecurityContextHolder.getContext()
//        .getAuthentication().getPrincipal();
//    return sysUserService.getUserByEmail(user.getUsername());
//  }
}
