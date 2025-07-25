package com.example.hackathon.domain.global.exception;

import com.example.hackathon.domain.global.common.CommonResponse;
import com.example.hackathon.domain.global.common.ErrorResponse;
import com.example.hackathon.domain.global.util.ApiResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse<?>> handleCustomException(CustomException e) {
        return ApiResponseUtils.error(e.getErrorResponse());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ApiResponseUtils.error(ErrorResponse.INVALID_INPUT);
    }

}