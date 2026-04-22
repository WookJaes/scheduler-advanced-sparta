package com.wookjae.scheduler.user.dto;

import com.wookjae.scheduler.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserGetResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UserGetResponse(Long id, String name, String email, LocalDateTime createdAt,
        LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UserGetResponse from(User user) {
        return new UserGetResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt(),
            user.getModifiedAt()
        );
    }
}