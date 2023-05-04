package com.arthurtien.backend.dao;

import com.arthurtien.backend.dto.UserModifyRequest;
import com.arthurtien.backend.dto.UserRegisterRequest;
import com.arthurtien.backend.model.SysUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface SysUserDao {

    List<SysUser> getUser();
    SysUser getUserByEmail(String email);
    // 新增帳號
    Integer createUser(UserRegisterRequest userRegisterRequest);
    // 查詢帳號 - By Id
    SysUser getUserById(Integer userId);
    // 修改user
    void modifyUser(Integer userId, UserModifyRequest userModifyRequest);
}
