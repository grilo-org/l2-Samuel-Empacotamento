package com.samuel.l2challenge.Empacotamento.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtro para autenticação.
 * Valida se a senha passada na request é a senha esperada pela aplicação.
 * Se sim, considera que a request está autenticada.
 */
@Component
public class TokenAuthFilter extends OncePerRequestFilter {

  @Value("${spring.authentication.secret}")
  private String expectedToken;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authHeader != null && authHeader.equals("Bearer " + expectedToken)) {
      var auth = new UsernamePasswordAuthenticationToken("authorized-user", null,
          Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
      org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(auth);

    }

    filterChain.doFilter(request, response);
  }

}

