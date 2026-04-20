package com.wookjae.scheduler.schedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeleteScheduleRequest {

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;
}