package com.arthurtien.backend.secuirty;

import com.arthurtien.backend.response.ServerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ExceptionHandling extends Throwable implements AuthenticationEntryPoint, AccessDeniedHandler {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    response.setContentType("application/json;charset=UTF-8");
    ServerResponse serverResponse = new ServerResponse<>();
    serverResponse.setCode(0);
    serverResponse.setMessage("未登入或token錯誤");
    ObjectMapper objectMapper = new ObjectMapper();
    String s = objectMapper.writeValueAsString(serverResponse);
    response.getWriter().write(s);
  }

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setContentType("application/json;charset=UTF-8");
    ServerResponse serverResponse = new ServerResponse<>();
    serverResponse.setCode(2);
    serverResponse.setMessage("權限不足");
    ObjectMapper objectMapper = new ObjectMapper();
    String s = objectMapper.writeValueAsString(serverResponse);
    response.getWriter().write(s);
  }
}
