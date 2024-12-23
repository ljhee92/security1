package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인에 등록됨
public class SecurityConfig {
    // @Bean : 해당 메서드의 리턴되는 객체를 IoC로 등록
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").authenticated() // 인증만 되면 접근 가능
                        .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN") // 특정 권한 필요
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 권한 필요
                        .anyRequest().permitAll() // 그 외 권한 허용
                );

        http.formLogin(form -> form
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // login 주소가 호출 -> 시큐리티가 낚아채서 로그인 대신 진행
                .defaultSuccessUrl("/"));
        return http.build();
    }
}
