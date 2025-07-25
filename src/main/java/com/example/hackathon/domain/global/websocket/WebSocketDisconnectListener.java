package com.example.hackathon.domain.global.websocket;

import com.example.hackathon.domain.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketDisconnectListener implements ApplicationListener<SessionDisconnectEvent> {

    private final RoomService roomService;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String roomId = (String) accessor.getSessionAttributes().get("roomId");
        String userId = (String) accessor.getSessionAttributes().get("userId");

        roomService.removeParticipant(roomId, userId);
    }

}
