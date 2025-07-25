package com.example.hackathon.domain.user;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class User {

    private String id;

    private String nickname;

    private Role role;

    public static User of(String nickname) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .nickname(nickname)
                .build();
    }

    public void assign(Role role) {
        this.role = role;
    }

}
