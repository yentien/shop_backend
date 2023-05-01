package com.arthurtien.backend.controller;

import com.arthurtien.backend.model.SysUser;
import com.arthurtien.backend.secuirty.JwtUtils;
import com.arthurtien.backend.dto.UserLoginRequest;
import com.arthurtien.backend.dto.UserModifyRequest;
import com.arthurtien.backend.dto.UserRegisterRequest;
import com.arthurtien.backend.service.SysUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
public class UserController {

  private Logger log = Logger.getLogger(UserController.class.getName());

  private final SysUserService sysUserService;
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;

  // 新增帳號
  @PostMapping("/users/register")
  public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
    String password = userRegisterRequest.getPassword();
    // 檢查email是否已經存在
    if (sysUserService.getUserByEmail(userRegisterRequest.getEmail()) != null) {
      // 409
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
    }


    try {
      // 新增帳號
      Integer userId = sysUserService.register(userRegisterRequest);

      // 查詢新增的帳號
      SysUser sysUser = sysUserService.getUserById(userId);

      // security 帳密驗證並獲取jwtToken
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(userRegisterRequest.getEmail(), password)
      );

      SecurityContextHolder.getContext().setAuthentication(authentication);
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();

      Map<String, Object> response = new HashMap<>();
      response.put("jwt", jwtUtils.generateToken(userDetails));
      response.put("user", sysUser);

      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (Exception e) {
      return ResponseEntity.status(400).body(e.toString());
    }
  }

  // 登入
  @PostMapping("/users/login")
  public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword())
      );

      SecurityContextHolder.getContext().setAuthentication(authentication);
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();

      if (userDetails != null) {

        SysUser sysUser = sysUserService.getUserByEmail(userLoginRequest.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("jwt", jwtUtils.generateToken(userDetails));
        response.put("user", sysUser);

        return ResponseEntity.ok(response);
      } else {
        return ResponseEntity.status(400).body("找不到此用戶資訊");
      }
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(401).body("驗證失敗");
    }
  }

  // 修改user
  @PostMapping("/users/{userId}")
  public ResponseEntity<?> modifyUser(
      @PathVariable Integer userId,
      @RequestBody UserModifyRequest userModifyRequest) {

    // 檢查是否有此user
    SysUser sysUser = sysUserService.getUserById(userId);
    if (sysUser == null) {
      return ResponseEntity.status(404).body("找不到此用戶");
    }

    // 修改user
    sysUserService.modifyUser(userId, userModifyRequest);
    // 修改完的user資訊
    SysUser modifiedSysUser = sysUserService.getUserById(userId);

    return ResponseEntity.status(200).body(modifiedSysUser);
  }
}
