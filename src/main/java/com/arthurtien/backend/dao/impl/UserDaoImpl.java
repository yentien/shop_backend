package com.arthurtien.backend.dao.impl;

import com.arthurtien.backend.controller.UserController;
import com.arthurtien.backend.dao.UserDao;
import com.arthurtien.backend.dto.UserModifyRequest;
import com.arthurtien.backend.dto.UserRegisterRequest;
import com.arthurtien.backend.model.User;
import com.arthurtien.backend.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.logging.Logger;

@Repository
@Component
public class UserDaoImpl implements UserDao {
    private Logger log = Logger.getLogger(UserController.class.getName());

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // 修改user
    @Override
    public void modifyUser(Integer userId, UserModifyRequest userModifyRequest) {
        String sql = "UPDATE user SET user.name = :name," +
            " user.cellphone = :cellphone, user.address = :address," +
            " user.gender = :gender , last_modified_date = :lastModifiedDate" +
            " WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("name", userModifyRequest.getName());
        map.put("cellphone", userModifyRequest.getCellphone());
        map.put("address", userModifyRequest.getAddress());
        map.put("gender", userModifyRequest.getGender());
        map.put("userId", userId);
        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    public UserDetails findUserByEmail(String email) {
        log.info("Run findUserByEmail");
        String sql = "SELECT user_id, email, password, created_date," +
            " last_modified_date, name, role, cellphone, address, gender" +
            " FROM `user`" +
            " WHERE email = :email";

        Map<String, Object> map = new HashMap<>();
        map.put("email",email);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (userList.size() > 0) {
//            log.info(userList.get(0).toString());
            log.info(userList.get(0).getEmail() + "  "+ userList.get(0).getPassword()  + "  " +
                userList.get(0).getRole());
            return new org.springframework.security.core.userdetails.User(
                userList.get(0).getEmail(),
                userList.get(0).getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(userList.get(0).getRole()))
            );
        } else {
            log.info("找不到email");
            throw new UsernameNotFoundException("User with email: " +email +" not found !");
        }
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT user_id, email, password, created_date," +
                " last_modified_date, name, role, cellphone, address, gender" +
                " FROM `user`" +
                " WHERE email = :email";

        Map<String, Object> map = new HashMap<>();
        map.put("email",email);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    // 新增帳號
    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO `user`(email, password, created_date," +
                " last_modified_date, name, role)" +
                " VALUES (:email, :password, :createdDate," +
                " :lastModifiedDate, :name, :role)";

        Map<String, Object> map = new HashMap<>();
        map.put("email", userRegisterRequest.getEmail());
        map.put("name", userRegisterRequest.getUsername());
        map.put("password", userRegisterRequest.getPassword());

        // 權限 - 先都設定為normal
        String role = "USER";
        map.put("role", role);

        // 開通 - 先都設定為enable
        String status = "enable";
        map.put("status", status);

        Date date = new Date();
        map.put("createdDate", date);
        map.put("lastModifiedDate", date);

        // 用來接住新增帳號的userId
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate
                .update(sql, new MapSqlParameterSource(map), keyHolder);

        Integer userId = keyHolder.getKey().intValue();

        return userId;
    }


    @Override
    public User getUserById(Integer userId) {
//        String sql = "SELECT user_id, email, password, created_date," +
//                " last_modified_date, name, role" +
//                " FROM `user`" +
//                " WHERE user_id = :userId";

        String sql = "SELECT user_id, email, password, created_date," +
            " last_modified_date, name, role, status, gender," +
            " cellphone, address " +
            "FROM `user` WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (userList.size() < 0) return null;

        return userList.get(0);
    }
}
