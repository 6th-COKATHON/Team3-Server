package com.example.hackathon.domain.game.controller.dto;

public record ExplanationDto(
        String nickname,
        String description
) {
    public static ExplanationDto of(String nickname, String description) {
        return new ExplanationDto(nickname, description);
    }
}
