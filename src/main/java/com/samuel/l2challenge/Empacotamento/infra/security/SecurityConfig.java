package com.samuel.l2challenge.Empacotamento.infra.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuração de autenticação.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final TokenAuthFilter tokenAuthFilter;

  /**
   * Desabilita csrf e seta a política como stateless, já que a aplicação é REST e não trabalho com sessões.
   * Valida a autenticação das requests, exceto se for um acesso ao swagger.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/actuator/health"
            ).permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(tokenAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /**
   * Isso é para fazer o Spring não gerar a senha automática e imprimir log no console.
   */
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      throw new UsernameNotFoundException("Usuário não suportado.");
    };
  }

}
