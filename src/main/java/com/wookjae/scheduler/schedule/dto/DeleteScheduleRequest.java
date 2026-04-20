package com.wookjae.scheduler.schedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeleteScheduleRequest {

    @NotNull
    private Long userId;
}