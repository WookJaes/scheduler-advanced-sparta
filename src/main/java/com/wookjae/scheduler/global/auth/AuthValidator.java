package com.wookjae.scheduler.global.auth;

import com.wookjae.scheduler.global.exception.UnauthorizedException;

public class AuthValidator {

    private AuthValidator() {}

    public static void validateLogin(SessionUser sessionUser) {
        if (sessionUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
    }
}