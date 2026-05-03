package com.wookjae.scheduler.schedule.dto;

import com.wookjae.scheduler.comment.dto.CommentGetResponse;
import com.wookjae.scheduler.schedule.entity.Schedule;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;

@Getter
public class ScheduleGetResponse {

    private final Long id;
    private final Long userId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<CommentGetResponse> comments;

    public ScheduleGetResponse(Long id, Long userId, String title, String content,
        LocalDateTime createdAt, LocalDateTime modifiedAt, List<CommentGetResponse> comments) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
    }

    public static ScheduleGetResponse from(Schedule schedule, List<CommentGetResponse> comments) {
        return new ScheduleGetResponse(
            schedule.getId(),
            schedule.getUser().getId(),
            schedule.getTitle(),
            schedule.getContent(),
            schedule.getCreatedAt(),
            schedule.getModifiedAt(),
            comments
        );
    }
}