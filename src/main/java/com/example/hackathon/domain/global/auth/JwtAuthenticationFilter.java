package com.example.hackathon.domain.global.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // JWT 인증 필터 구현
    // 이 클래스는 HTTP 요청에서 JWT 토큰을 추출하고 검증하는 역할을 합니다.

    private final JwtTokenProvider jwtTokenProvider;

    //실제 HTTP 요청 가로채는 메소드
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Authorization 헤더에서 JWT 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");
        String token = extractToken(authorizationHeader);
        // 토큰이 존재하고 유효한지 검증
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효한 경우, 인증된 사용자 정보 설정
            String userId = jwtTokenProvider.getUserId(token);

            // 인증 객체 생성
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, null); // 권한 없음

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            // Spring Security Context에 등록
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }



    // JWT 토큰 추출 메소드
    public String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

}
