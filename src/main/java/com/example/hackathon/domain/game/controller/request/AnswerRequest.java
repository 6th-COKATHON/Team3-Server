package com.example.hackathon.domain.game.controller.request;

public record AnswerRequest(
        String roomId,
        String answer
) {
}
