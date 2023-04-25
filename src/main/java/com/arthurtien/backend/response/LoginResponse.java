package com.arthurtien.backend.response;

import lombok.Data;

@Data
public class LoginResponse {

  private String email;
  private String jwt;
}
