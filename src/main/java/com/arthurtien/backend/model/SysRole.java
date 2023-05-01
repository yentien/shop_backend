package com.arthurtien.backend.model;

import lombok.Data;

import java.util.List;

@Data
public class SysRole {
  private Integer id;
  private String code;
  private String name;

  private List<SysPermission> permissionList;
}
