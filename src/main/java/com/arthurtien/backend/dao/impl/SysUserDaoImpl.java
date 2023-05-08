package com.arthurtien.backend.dao.impl;

import com.arthurtien.backend.dao.SysUserDao;
import com.arthurtien.backend.dto.UserModifyRequest;
import com.arthurtien.backend.dto.UserRegisterRequest;
import com.arthurtien.backend.model.SysUser;
import com.arthurtien.backend.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Repository
@Component
public class SysUserDaoImpl implements SysUserDao {
    private Logger log = Logger.getLogger(SysUserDaoImpl.class.getName());

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // 查詢user(所有)
    @Override
    public List<SysUser> getUser() {
        String sql = "SELECT user_id, email, password, created_date, last_modified_date," +
            " user.name, user.status, user.gender, user.cellphone, user.address, role_name" +
            " FROM user" +
            " LEFT JOIN m_user_roles mur on user.user_id = mur.u_id" +
            " LEFT JOIN role r on mur.r_id = r.role_id";

        List<SysUser> userList = namedParameterJdbcTemplate.query(sql, new UserRowMapper());
        return userList;
    }

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

    @Override
    public SysUser getUserByEmail(String email) {
        String sql = "SELECT user_id, email, password, created_date," +
                " last_modified_date, name, cellphone, address, gender" +
                " FROM `user`" +
                " WHERE email = :email";

        Map<String, Object> map = new HashMap<>();
        map.put("email",email);

        List<SysUser> sysUserList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (sysUserList.size() > 0) {
            return sysUserList.get(0);
        } else {
            return null;
        }
    }

    // 新增帳號
    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO `user`(email, password, created_date," +
                " last_modified_date, name, status)" +
                " VALUES (:email, :password, :createdDate," +
                " :lastModifiedDate, :name, :status)";

        Map<String, Object> map = new HashMap<>();
        map.put("email", userRegisterRequest.getEmail());
        map.put("name", userRegisterRequest.getUsername());
        map.put("password", userRegisterRequest.getPassword());

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
    public SysUser getUserById(Integer userId) {
        String sql = "SELECT user_id, email, password, created_date," +
            " last_modified_date, name, status, gender," +
            " cellphone, address " +
            "FROM `user` WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<SysUser> sysUserList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (sysUserList.size() == 0) return null;

        return sysUserList.get(0);
    }
}
