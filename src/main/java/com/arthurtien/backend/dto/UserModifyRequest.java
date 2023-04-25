package com.arthurtien.backend.dto;

import lombok.Data;

@Data
public class UserModifyRequest {
  private String name;
  private String cellphone;
  private String address;
  private String gender;
}
