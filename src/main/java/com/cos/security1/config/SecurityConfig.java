package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인에 등록됨
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").authenticated() // /user로 들어오면 인증 필요
                        .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN") // 특정 권한 필요
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 권한 필요
                        .anyRequest().permitAll() // 그 외 권한 허용
                );

        http.formLogin(form -> form
                .loginPage("/login"));
        return http.build();
    }
}
