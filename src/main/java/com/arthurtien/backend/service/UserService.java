package com.arthurtien.backend.service;

import com.arthurtien.backend.dto.UserLoginRequest;
import com.arthurtien.backend.dto.UserModifyRequest;
import com.arthurtien.backend.dto.UserRegisterRequest;
import com.arthurtien.backend.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService {

    User getUserByEmail(String email);

    // 新增帳號
    Integer register(UserRegisterRequest userRegisterRequest);

    // 查詢帳號 - By Id
    User getUserById(Integer userId);

    User login(UserLoginRequest userLoginRequest);

    void modifyUser(Integer userId, UserModifyRequest userModifyRequest);
}
