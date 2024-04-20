package ru.geekbraines.jwt_hometask.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ru.geekbraines.jwt_hometask.security.JwtProperties;

@Service
public class JwtService {


    @Autowired
   private JwtProperties properties;


    public String generateToken(UserDetails userDetails){

        String userName = userDetails.getUsername();        

        List<String> authorities = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList();


            Map<String,Object> claims = new HashMap<>(Map.of("authority",authorities));
                return Jwt.builder()
                                .setClaims(claims)
                                .setSubject(userName)
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(System.currentTimeMillis()+properties.getExpire_Time().toMillis()))
                                .signWith(SignatureAlgorithm.HS256, properties.getSecret())         
                                .compact();   

    }

        public String getUsername(String value){

            return parse(value).getSubject();
        }


          private Claims parse(String value){

            return Jwts.parser()
                .setSigningKey(properties.getSecret())
                .parseClaimsJws(value)
                .getBody(); 

          }     

          public List<GrantedAuthority> getAuthorities(String value){

                List<String> authorities = parse(value).get("authority");

                return authorities.stream()
                .map(SimpleGrantedAuthority:: new)
                .map(it->(GrantedAuthority) it)
                .toList();

          }





}
