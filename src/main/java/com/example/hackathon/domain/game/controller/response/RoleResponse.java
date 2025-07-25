package com.example.hackathon.domain.game.controller.response;

import com.example.hackathon.domain.user.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoleResponse(
        Role role,
        String imageUrl
) {
    public static RoleResponse of(Role role, String imageUrl) {
        return RoleResponse.of(role, imageUrl);
    }
}
