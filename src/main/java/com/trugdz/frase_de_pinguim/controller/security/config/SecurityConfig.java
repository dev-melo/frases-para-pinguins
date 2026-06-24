package com.trugdz.frase_de_pinguim.controller.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/frases/**", "/users/**").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(form -> form.permitAll())
            .logout(LogoutConfigurer::permitAll);
        
        return http.build();
    }
}