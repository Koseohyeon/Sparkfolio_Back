package com.sparkfolio.sparkfolio_back.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.formLogin(form -> form
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .failureUrl("/member/login/error")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
        );
        http.logout(Customizer.withDefaults());


        return http.build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
