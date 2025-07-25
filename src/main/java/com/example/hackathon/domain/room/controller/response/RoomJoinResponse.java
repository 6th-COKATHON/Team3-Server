package com.example.hackathon.domain.room.controller.response;

public record RoomJoinResponse(
        String accessToken
) {
    public static RoomJoinResponse of(String accessToken) {
        return new RoomJoinResponse(accessToken);
    }
}
