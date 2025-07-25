package com.example.hackathon.domain.global.util;

import com.example.hackathon.domain.global.common.CommonResponse;
import com.example.hackathon.domain.global.common.ErrorResponse;
import com.example.hackathon.domain.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;

public class ApiResponseUtils {

    public static ResponseEntity<CommonResponse<?>> success(SuccessResponse successResponse) {
        return ResponseEntity.status(successResponse.getStatus()).body(CommonResponse.success(successResponse));
    }

    public static <T> ResponseEntity<CommonResponse<?>> success(SuccessResponse successResponse, T data) {
        return ResponseEntity.status(successResponse.getStatus()).body(CommonResponse.success(successResponse, data));
    }

    public static ResponseEntity<CommonResponse<?>> error(ErrorResponse errorResponse) {
        return ResponseEntity.status(errorResponse.getStatus()).body(CommonResponse.error(errorResponse));

    }
}

