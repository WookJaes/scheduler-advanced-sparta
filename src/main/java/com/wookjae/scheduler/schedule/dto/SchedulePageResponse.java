package com.wookjae.scheduler.schedule.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SchedulePageResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final Long commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String userName;

    public SchedulePageResponse(Long id, String title, String content, Long commentCount,
        LocalDateTime createdAt, LocalDateTime modifiedAt, String userName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.userName = userName;
    }
}