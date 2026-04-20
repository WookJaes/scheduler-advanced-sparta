package com.wookjae.scheduler.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(min = 8, message = "비밀번호는 8글자 이상이어야 합니다.")
    private String password;
}