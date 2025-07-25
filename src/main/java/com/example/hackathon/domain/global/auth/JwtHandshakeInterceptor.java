package com.example.hackathon.domain.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    //핸드셰이크 전 JWT 토큰 검증, 유저 정보 웹소켓 세션에 저장
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // 요청이 Servlet 기반인지 확인
        if (request instanceof ServletServerHttpRequest servletRequest) {
            // HttpServletRequest 추출
            HttpServletRequest httpRequest = servletRequest.getServletRequest();

            // Authorization 헤더에서 토큰 추출 (예: "Bearer abc.def.ghi")
            String authHeader = httpRequest.getHeader("Authorization");

            // 헤더가 존재하고 Bearer 타입인지 확인
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); // "Bearer " 제거하여 토큰만 추출

                // 토큰 유효성 검사
                if (jwtTokenProvider.validateToken(token)) {
                    // 토큰에서 필요한 정보 추출
                    String userId = jwtTokenProvider.getUserId(token);
                    String roomId = jwtTokenProvider.getRoomId(token);
                    System.out.println("유저 아이디" + userId);
                    // WebSocket 세션에 사용자 정보 저장
                    attributes.put("userId", userId);
                    attributes.put("roomId", roomId);

                    // 연결 허용
                    return true;
                }
            }
        }
        // 요청이 유효하지 않거나 인증 실패 시 연결 차단
        return false;
    }

    // 핸드셰이크 후 추가 작업이 필요할 경우 구현
    // 이 경우는 생략 가능
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // 생략 가능
    }
}
