package com.example.hackathon.domain.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    // Spring Security 설정 클래스
    // 이 클래스는 Spring Security의 설정을 정의합니다.

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // JWT 인증 필터를 등록하는 메소드
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/rooms/**/nickname",   // 닉네임 등록 허용
                                "/public/**",           // 기타 공개 API
                                "/static/**"            // 정적 리소스
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .securityContext(context -> context.requireExplicitSave(false));

        return http.build();
    }
}
