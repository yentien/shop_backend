package com.arthurtien.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysUser {

    private Integer userId;
    private String email;
    @JsonIgnore
    private String password;
    private String name;
    private String gender;
    private String cellphone;
    private String address;
    private Date createdDate;
    private Date lastModifiedDate;

    private String roleName;
//    private List<SysRole> role;
}
