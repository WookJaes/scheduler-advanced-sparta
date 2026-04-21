package com.wookjae.scheduler.comment.repository;

import com.wookjae.scheduler.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByScheduleId(Long scheduleId);
}