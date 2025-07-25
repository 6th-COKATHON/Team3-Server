package com.example.hackathon.domain.room.controller.request;

import jakarta.validation.constraints.NotBlank;

public record RoomCreationRequest(
        @NotBlank String name
) {
}
