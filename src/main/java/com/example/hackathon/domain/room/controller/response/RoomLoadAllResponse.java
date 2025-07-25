package com.example.hackathon.domain.room.controller.response;

import com.example.hackathon.domain.room.controller.dto.RoomLoadDto;
import java.util.List;

public record RoomLoadAllResponse(
        List<RoomLoadDto> rooms
) {
    public static RoomLoadAllResponse of(List<RoomLoadDto> rooms) {
        return new RoomLoadAllResponse(rooms);
    }
}
