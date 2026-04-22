package com.wookjae.scheduler.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleUpdateRequest {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 30, message = "제목은 30자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 200, message = "200자 이하여야 합니다.")
    private String content;
}