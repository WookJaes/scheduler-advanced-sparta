package com.wookjae.scheduler.comment.entity;

import com.wookjae.scheduler.global.entity.BaseEntity;
import com.wookjae.scheduler.schedule.entity.Schedule;
import com.wookjae.scheduler.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    // soft delete (true = 삭제된 댓글, 실제 DB 삭제하지 않음)
    @Column(nullable = false)
    private boolean deleted = false;

    public Comment(String content, User user, Schedule schedule) {
        this.content = content;
        this.user = user;
        this.schedule = schedule;
    }

    public void update(String content) {
        this.content = content;
    }

    // 삭제 대신 상태값 변경 (데이터 보존 및 FK 제약 회피)
    public void softDelete() {
        this.deleted = true;
    }
}