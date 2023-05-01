package com.arthurtien.backend.model;

import lombok.Data;

@Data
public class SysPermission {
  private Integer id;
  private String code;
  private String name;
  private String resource;
}
