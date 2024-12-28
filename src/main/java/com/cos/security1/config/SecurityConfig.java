package com.cos.security1.config;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인에 등록됨
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // Secure 어노테이션 활성화, PreAuthorize, PostAuthorize 어노테이션 활성화
public class SecurityConfig {
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    // 순환 참조 이슈로 별도 클래스 생성
    // @Bean : 해당 메서드의 리턴되는 객체를 IoC로 등록
//    @Bean
//    public BCryptPasswordEncoder encodePwd() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").authenticated() // 인증만 되면 접근 가능
                        .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN") // 특정 권한 필요
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 권한 필요
                        .anyRequest().permitAll() // 그 외 권한 허용
                )

                .formLogin(form -> form
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // login 주소가 호출 -> 시큐리티가 낚아채서 로그인 대신 진행
                .defaultSuccessUrl("/")
                )

                .oauth2Login(oauth -> oauth
                        .loginPage("/loginForm") // oauth login 페이지
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(principalOauth2UserService)) // 사용자 정보 후처리
                );

        return http.build();
    }
}
