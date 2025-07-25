package com.example.hackathon.domain.game.controller.response;

import com.example.hackathon.domain.room.EventType;

public record NotificationResponse(
        EventType eventType,
        String nickname
) {
    public static NotificationResponse of(EventType eventType, String nickname) {
        return new NotificationResponse(eventType, nickname);
    }
}
