package com.example.hackathon.domain.game.controller;

import com.example.hackathon.domain.game.controller.request.AnswerRequest;
import com.example.hackathon.domain.game.controller.request.DescriptionRequest;
import com.example.hackathon.domain.game.controller.request.ExplanationRequest;
import com.example.hackathon.domain.game.controller.request.JoinRequest;
import com.example.hackathon.domain.room.service.RoomService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GameWebSocket {

    private final RoomService roomService;

    @MessageMapping("/enter")
    public void enterRoom(@Payload JoinRequest request, Principal principal) {
        String userId = principal.getName();
        roomService.addParticipant(request.roomId(), userId);
        roomService.startGame(request.roomId());
    }

    @MessageMapping("/description")
    public void submitDescription(@Payload DescriptionRequest request, Principal principal) {
        String userId = principal.getName();
        roomService.submitDescription(request, userId);
    }

    @MessageMapping("/answer")
    public void submitAnswer(@Payload AnswerRequest request, Principal principal) {
        String userId = principal.getName();
        roomService.submitAnswer(request, userId);
    }

    @MessageMapping("/explanation")
    public void submitAnswer(@Payload ExplanationRequest request) {
        roomService.loadExplanation(request);
    }

}
