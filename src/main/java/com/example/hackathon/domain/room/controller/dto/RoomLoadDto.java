package com.example.hackathon.domain.room.controller.dto;

import com.example.hackathon.domain.room.Room;

public record RoomLoadDto(
        String id,
        String name
) {
    public static RoomLoadDto of(Room room) {
        return new RoomLoadDto(room.getId(), room.getName());
    }
}
