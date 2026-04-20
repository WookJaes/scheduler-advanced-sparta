package com.wookjae.scheduler.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    @NotNull
    private Long userId;

    @NotBlank
    @Size(max = 30)
    private String title;

    @NotBlank
    @Size(max = 200)
    private String content;
}