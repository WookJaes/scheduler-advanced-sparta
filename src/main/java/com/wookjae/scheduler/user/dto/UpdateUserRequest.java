package com.wookjae.scheduler.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    @NotNull
    @Size(max = 50)
    private String name;
}