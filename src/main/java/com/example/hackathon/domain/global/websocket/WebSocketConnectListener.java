package com.example.hackathon.domain.global.websocket;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
@RequiredArgsConstructor
public class WebSocketConnectListener implements ApplicationListener<SessionConnectedEvent> {

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        Principal user = event.getUser();
        if (user != null) {
            System.out.println("Connected user ID: " + user.getName());
        } else {
            System.out.println("No Principal found");
        }
    }

}
