package com.wookjae.scheduler.schedule.entity;

import com.wookjae.scheduler.global.entity.BaseEntity;
import com.wookjae.scheduler.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // soft delete (true = 삭제된 일정, 실제 DB 삭제하지 않음)
    @Column(nullable = false)
    private boolean deleted = false;

    public Schedule(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 삭제 대신 상태값 변경 (데이터 보존)
    public void softDelete() {
        this.deleted = true;
    }
}