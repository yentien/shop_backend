package com.arthurtien.backend.secuirty;

import com.arthurtien.backend.dao.impl.SysUserDaoImpl;
import com.arthurtien.backend.response.ServerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.logging.Logger;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Component
public class JwtAthFilter extends OncePerRequestFilter {

  private Logger log = Logger.getLogger(JwtAthFilter.class.getName());

  private final SysUserDaoImpl userDao;
  private final JwtUtils jwtUtils;
  private final UserDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {
    try {
      // 取得Authorization標頭欄位的值
      final String authHeader = request.getHeader(AUTHORIZATION);
      // 如果Authorization標頭欄位的值不存在或不是以"Bearer "字串開頭
      // ，則直接繼續下一個Filter處理
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
      }
      final String userEmail;
      final String jwtToken;
      // 從Authorization標頭欄位的值中取出JWT Token
      jwtToken = authHeader.substring(7);
      // 從JWT Token中解析出使用者的Email
      userEmail = jwtUtils.extractUsername(jwtToken);

      // 如果使用者Email存在且當前的安全上下文中沒有驗證過的身份驗證，則進行身份驗證
      if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

        // 驗證JWT Token是否合法，如果合法則建立一個身份驗證物件，並存入SecurityContextHolder
        if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
      }
    } catch (ExpiredJwtException ex) {
      response.setContentType("application/json;charset=UTF-8");
      ServerResponse serverResponse = new ServerResponse<>();
      serverResponse.setCode(2);
      serverResponse.setMessage("JWT token 過期");
      ObjectMapper objectMapper = new ObjectMapper();
      String s = objectMapper.writeValueAsString(serverResponse);
      response.getWriter().write(s);
    }
  }
}
