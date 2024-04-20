package ru.geekbraines.jwt_hometask.security;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.geekbraines.jwt_hometask.service.JwtService;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    @Autowired  
    private JwtService  jwtService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
       
                String authorizationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);
                if (authorizationHeaderValue!=null && authorizationHeaderValue.startsWith("Bearer ")) {
                    
                    String bearerTokenValue = authorizationHeaderValue.substring(7);

                    String username = jwtService.getUsername(bearerTokenValue);
                    List<GrantedAuthority> authorities = jwtService.getAuthorities(bearerTokenValue);


                    if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                        SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(username,null, authorities)
                        );
                    }

                }
                filterChain.doFilter(request, response);


    }

    
}
