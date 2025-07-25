package com.example.hackathon.domain.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorResponse {
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "COMMON003", "올바르지 않은 입력"),

    INVALID_USER(HttpStatus.UNAUTHORIZED, "AUTH001", "존재하지 않는 유저"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH002", "유효하지 않은 토큰"),

    ACCESS_DENIED(HttpStatus.FORBIDDEN, "AUTH004", "접근 권한 없음"),

    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON001", "요청 자원 없음");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
