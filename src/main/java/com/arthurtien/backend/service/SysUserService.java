package com.arthurtien.backend.service;

import com.arthurtien.backend.dto.UserLoginRequest;
import com.arthurtien.backend.dto.UserModifyRequest;
import com.arthurtien.backend.dto.UserRegisterRequest;
import com.arthurtien.backend.model.SysUser;

import java.util.List;


public interface SysUserService {

    List<SysUser> getUser();

    SysUser getUserByEmail(String email);

    // 新增帳號
    Integer register(UserRegisterRequest userRegisterRequest);

    // 查詢帳號 - By Id
    SysUser getUserById(Integer userId);

    SysUser login(UserLoginRequest userLoginRequest);

    void modifyUser(Integer userId, UserModifyRequest userModifyRequest);

//    User findUserByEmail(String email);
}
