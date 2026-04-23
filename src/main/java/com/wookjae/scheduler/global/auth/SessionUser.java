package com.wookjae.scheduler.global.auth;

import lombok.Getter;

/**
 * 로그인한 유저 정보를 세션에 저장하는 DTO
 * 인증된 유저 식별에 사용된다.
 */
@Getter
public class SessionUser {

    private final Long id;
    private final String name;
    private final String email;

    public SessionUser(Long id, String name, String email) {
        if (id == null) {
            throw new IllegalArgumentException("유저 ID는 필수입니다.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }

        this.id = id;
        this.name = name;
        this.email = email;
    }
}