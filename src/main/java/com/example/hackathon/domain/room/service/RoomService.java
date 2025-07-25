package com.example.hackathon.domain.room.service;

import com.example.hackathon.domain.game.controller.dto.ExplanationDto;
import com.example.hackathon.domain.game.controller.request.AnswerRequest;
import com.example.hackathon.domain.game.controller.request.DescriptionRequest;
import com.example.hackathon.domain.game.controller.request.ExplanationRequest;
import com.example.hackathon.domain.game.controller.response.ExplanationResponse;
import com.example.hackathon.domain.game.controller.response.NotificationResponse;
import com.example.hackathon.domain.game.controller.response.RoleResponse;
import com.example.hackathon.domain.global.auth.JwtTokenProvider;
import com.example.hackathon.domain.global.exception.ForbiddenException;
import com.example.hackathon.domain.global.exception.NotFoundException;
import com.example.hackathon.domain.room.EventType;
import com.example.hackathon.domain.room.Room;
import com.example.hackathon.domain.room.controller.dto.RoomLoadDto;
import com.example.hackathon.domain.room.controller.request.RoomCreationRequest;
import com.example.hackathon.domain.room.controller.request.RoomJoinRequest;
import com.example.hackathon.domain.room.controller.response.RoomCreationResponse;
import com.example.hackathon.domain.room.controller.response.RoomJoinResponse;
import com.example.hackathon.domain.room.controller.response.RoomLoadAllResponse;
import com.example.hackathon.domain.room.controller.response.ValidateNicknameResponse;
import com.example.hackathon.domain.user.Role;
import com.example.hackathon.domain.user.User;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private static final int ROOM_MAXIMUM_CAPACITY = 1;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final Map<String, Room> roomRepository = new LinkedHashMap<>();
    private final Map<String, User> userRepository = new HashMap<>();
    private final JwtTokenProvider jwtTokenProvider;

    public ValidateNicknameResponse validateNickname(String roomId, String nickname) {
        return ValidateNicknameResponse.of(roomRepository.get(roomId).getParticipants().stream()
                .anyMatch(participant -> participant.getNickname().equals(nickname)));
    }

    public RoomJoinResponse joinRoom(String roomId, RoomJoinRequest request) {
        if (isFull(roomId)) {
            throw ForbiddenException.wrong();
        }
        User user = User.of(request.nickname());
        userRepository.put(user.getId(), user);
        return RoomJoinResponse.of(jwtTokenProvider.createToken(roomId, user.getId()));
    }

    public RoomLoadAllResponse loadRoomAll() {
        return RoomLoadAllResponse.of(
                roomRepository.values().stream()
                        .map(RoomLoadDto::of).toList()
        );
    }

    public RoomCreationResponse createRoom(RoomCreationRequest request) {
        Room room = Room.of(request.name());
        String roomId = room.getId();
        roomRepository.put(roomId, room);
        return RoomCreationResponse.of(roomId);
    }

    public void addParticipant(String roomId, String userId) {
        Room room = roomRepository.get(roomId);
        User user = userRepository.get(userId);
        room.addParticipant(user);
        simpMessagingTemplate.convertAndSend("/topic/room/" + roomId,
                NotificationResponse.of(EventType.ENTER, user.getNickname()));
    }

    public void removeParticipant(String roomId, String userId) {
        Room room = roomRepository.get(roomId);
        room.removeParticipant(userId);
        User user = userRepository.get(userId);
        simpMessagingTemplate.convertAndSend("/topic/room/" + roomId,
                NotificationResponse.of(EventType.LEFT, user.getNickname()));
        userRepository.remove(userId);
    }

    public void startGame(String roomId) {
        if (!isFull(roomId)) {
            return;
        }
        Room room = roomRepository.get(roomId);
        List<User> participants = room.getParticipants();
        User guesser = pickRandomUser(participants);
        guesser.assign(Role.GUESSER);
        participants.stream()
                .filter(u -> !u.equals(guesser))
                .toList()
                .forEach(describer -> describer.assign(Role.DESCRIBER));

        for (User user : participants) {
            boolean isGuesser = user.equals(guesser);
            Role role = isGuesser ? Role.GUESSER : Role.DESCRIBER;
            String imageUrl = isGuesser ? null : "이미지 URL";

            simpMessagingTemplate.convertAndSendToUser(
                    user.getId(),
                    "/queue/private",
                    RoleResponse.of(role, imageUrl)
            );
        }
    }

    public void submitDescription(DescriptionRequest request, String userId) {
        Room room = roomRepository.get(request.roomId());
        User user = room.getParticipants().stream()
                .filter(participant -> participant.getId().equals(userId))
                .findFirst()
                .orElseThrow(NotFoundException::wrong);
        if (!user.getRole().equals(Role.DESCRIBER)) {
            throw ForbiddenException.wrong();
        }
        room.addSubmission(user, request.description());
    }

    public void submitAnswer(AnswerRequest request, String userId) {
        Room room = roomRepository.get(request.roomId());
        User user = room.getParticipants().stream()
                .filter(participant -> participant.getId().equals(userId))
                .findFirst()
                .orElseThrow(NotFoundException::wrong);
        if (!user.getRole().equals(Role.GUESSER)) {
            throw ForbiddenException.wrong();
        }
        room.addSubmission(user, request.answer());
    }

    public boolean isFull(String roomId) {
        Room room = roomRepository.get(roomId);
        log.info(roomId);
        return room.getParticipants().size() >= ROOM_MAXIMUM_CAPACITY;
    }

    private User pickRandomUser(List<User> users) {
        int idx = new Random().nextInt(users.size());
        return users.get(idx);
    }

    public void loadExplanation(ExplanationRequest request) {
        String roomId = request.roomId();
        Room room = roomRepository.get(roomId);
        simpMessagingTemplate.convertAndSend("/topic/room/" + request.roomId(), ExplanationResponse.of(
                room.getSubmissions().entrySet().stream().filter(m -> m.getKey().getRole().equals(Role.DESCRIBER))
                        .toList()
                        .stream().map(
                                a -> ExplanationDto.of(a.getKey().getNickname(), a.getValue())).toList()
        ));
    }

}
