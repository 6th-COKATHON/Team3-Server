package com.example.hackathon.domain.room;

import com.example.hackathon.domain.user.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class Room {

    private String id;

    private String name;

    private boolean isStarted;

    private final List<User> participants = new ArrayList<>();

    private final Map<User, String> submissions = new HashMap<>();

    public static Room of(String name) {
        return Room.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .build();
    }

    public void addParticipant(User user) {
        this.participants.add(user);
    }

    public void removeParticipant(String userId) {
        this.participants.removeIf(participant -> participant.getId().equals(userId));
    }

    public void start() {
        this.isStarted = true;
    }

    public void addSubmission(User user, String submission) {
        this.submissions.put(user, submission);
    }

}
