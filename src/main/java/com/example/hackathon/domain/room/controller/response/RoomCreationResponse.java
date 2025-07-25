package com.example.hackathon.domain.room.controller.response;

public record RoomCreationResponse(
        String roomId
) {
    public static RoomCreationResponse of(String roomId) {
        return new RoomCreationResponse(roomId);
    }
}
