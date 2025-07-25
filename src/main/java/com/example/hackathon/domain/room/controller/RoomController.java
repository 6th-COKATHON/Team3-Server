package com.example.hackathon.domain.room.controller;

import com.example.hackathon.domain.global.common.SuccessResponse;
import com.example.hackathon.domain.global.util.ApiResponseUtils;
import com.example.hackathon.domain.room.controller.request.RoomCreationRequest;
import com.example.hackathon.domain.room.controller.request.RoomJoinRequest;
import com.example.hackathon.domain.room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping(value = "/{roomId}/nickname")
    public ResponseEntity<?> validateNickname(@PathVariable String roomId,
                                              @RequestParam String nickname) {
        return ApiResponseUtils.success(SuccessResponse.OK, roomService.validateNickname(roomId, nickname));
    }

    @GetMapping
    public ResponseEntity<?> loadRoomAll() {
        return ApiResponseUtils.success(SuccessResponse.OK, roomService.loadRoomAll());
    }

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody @Valid RoomCreationRequest request) {
        return ApiResponseUtils.success(SuccessResponse.CREATED, roomService.createRoom(request));
    }

    @PostMapping(value = "/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId,
                                      @RequestBody @Valid RoomJoinRequest request) {
        return ApiResponseUtils.success(SuccessResponse.OK, roomService.joinRoom(roomId, request));
    }

}
