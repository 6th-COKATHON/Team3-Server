package com.example.hackathon.domain.room.controller.response;

public record ValidateNicknameResponse(
        boolean isAvailable
) {
    public static ValidateNicknameResponse of(boolean isAvailable) {
        return new ValidateNicknameResponse(isAvailable);
    }
}
