package com.wookjae.scheduler.user.dto;

import lombok.Getter;

@Getter
public class SessionUser {

    private final Long id;
    private final String name;
    private final String email;

    public SessionUser(Long id, String name, String email) {
        if (id == null) {
            throw new IllegalArgumentException("사용자 ID는 필수입니다.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 비어 있을 수 없습니다.");
        }

        this.id = id;
        this.name = name;
        this.email = email;
    }
}