package com.wookjae.scheduler.schedule.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ScheduleCreateResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public ScheduleCreateResponse(Long id, String title, String content, LocalDateTime createdAt,
        LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}