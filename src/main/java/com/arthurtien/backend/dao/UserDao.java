package com.arthurtien.backend.dao;

import com.arthurtien.backend.dto.UserModifyRequest;
import com.arthurtien.backend.dto.UserRegisterRequest;
import com.arthurtien.backend.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDao {

    User getUserByEmail(String email);

    // 新增帳號
    Integer createUser(UserRegisterRequest userRegisterRequest);

    // 查詢帳號 - By Id
    User getUserById(Integer userId);

    // 修改user
    void modifyUser(Integer userId, UserModifyRequest userModifyRequest);

//    UserDetails findUserByEmail(String email);
}
