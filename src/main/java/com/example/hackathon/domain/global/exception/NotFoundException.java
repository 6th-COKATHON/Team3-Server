package com.example.hackathon.domain.global.exception;

import com.example.hackathon.domain.global.common.ErrorResponse;

public class NotFoundException extends CustomException {

    public NotFoundException(ErrorResponse errorResponse) {
        super(errorResponse);
    }

    public static NotFoundException wrong() {
        return new NotFoundException(ErrorResponse.NOT_FOUND);
    }

}
