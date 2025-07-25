package com.example.hackathon.domain.global.exception;

import com.example.hackathon.domain.global.common.ErrorResponse;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public CustomException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }
}
