package com.wookjae.scheduler.comment.dto;

import com.wookjae.scheduler.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentUpdateResponse {

    private final Long id;
    private final String content;
    private final Long userId;
    private final Long scheduleId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentUpdateResponse(Long id, String content, Long userId, Long scheduleId,
        LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CommentUpdateResponse from(Comment comment) {
        return new CommentUpdateResponse(
            comment.getId(),
            comment.getContent(),
            comment.getUser().getId(),
            comment.getSchedule().getId(),
            comment.getCreatedAt(),
            comment.getModifiedAt()
        );
    }
}