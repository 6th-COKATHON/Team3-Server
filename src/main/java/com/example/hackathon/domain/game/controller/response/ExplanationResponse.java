package com.example.hackathon.domain.game.controller.response;

import com.example.hackathon.domain.game.controller.dto.ExplanationDto;
import java.util.List;

public record ExplanationResponse(
        List<ExplanationDto> explanations
) {
    public static ExplanationResponse of(List<ExplanationDto> explanations) {
        return new ExplanationResponse(explanations);
    }
}
