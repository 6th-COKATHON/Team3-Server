package com.example.hackathon.domain.global.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final Key secretKey;
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간

    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        System.out.println("✅ Using JWT secret key with length: " + secret.getBytes().length * 8 + " bits");

        try {
            byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
            this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            System.err.println("Error creating JWT secret key: " + e.getMessage());
            throw new IllegalArgumentException("Invalid JWT secret key", e);
        }
    }

    // JWT 토큰 생성 로직
    public String createToken(String roomId, String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject("user")
                .claim("userId", userId)
                .claim("roomId", roomId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    // JWT 토큰 검증 로직
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token); // 예외 없으면 유효
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰에서 사용자 ID, Room ID 추출
    public String getUserId(String token) {
        return getClaims(token).get("userId", String.class);
    }

    public String getRoomId(String token) {
        return getClaims(token).get("roomId", String.class);
    }

    // Claims 꺼내는 내부 메서드
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
