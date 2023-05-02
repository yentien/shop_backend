package com.arthurtien.backend.secuirty;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private Logger log = Logger.getLogger(SecurityConfig.class.getName());

  private final CorsConfig corsConfig;
  private final JwtAthFilter jwtAuthFilter;
  private final UserDetailsServiceImpl userDetailsService;
  private final ExceptionHandling exceptionHandling;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws ExceptionHandling, Exception {

    http
        .csrf(csrf -> csrf.disable())
        .addFilterBefore(corsConfig.corsFilter(), ChannelProcessingFilter.class)
        .authorizeHttpRequests( auth -> auth
            .requestMatchers("/users/register","/users/login","/products/**").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/users/{userId}/**").hasAnyRole("ADMIN","USER")
            .anyRequest().authenticated()
        )
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling().authenticationEntryPoint(exceptionHandling)
        .accessDeniedHandler(exceptionHandling)
        ;

    return http.build();
  }

  // 設定自訂義的userDetailService
  @Bean
  public AuthenticationProvider authenticationProvider() {
    final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws ExceptionHandling, Exception {
      return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
