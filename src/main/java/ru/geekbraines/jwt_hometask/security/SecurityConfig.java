package ru.geekbraines.jwt_hometask.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer   webSecurityCustomizer(){

        return new WebSecurityCustomizer() {

            @Override
            public void customize(WebSecurity web) {
               
                 web.ignoring().requestMatchers("/auth/**");       

            }
            
        };

    } 

    @Bean
    public SecurityFilterChain securityFilterChainA(SecurityFilter filter, HttpSecurity httpSecurity) throws Exception{

            return httpSecurity.authorizeHttpRequests()
                .requestMatchers("/api/**")
                .authenticated()
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }


    @Bean
    public UserDetails userDetails(){

        UserDetails user = User.builder()   
            .username("user")
            .password("pass")
            .authorities("ADMIN", "MANAGER")
            .build();

            return (UserDetails) new InMemoryUserDetailsManager(user);
    }


    @Bean   
    public AuthenticationManager authenticationManager(AuthenticationProvider... providers){

        return new ProviderManager(providers);    

    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
            return new StandardAuthenticationProvider();

    }



}
