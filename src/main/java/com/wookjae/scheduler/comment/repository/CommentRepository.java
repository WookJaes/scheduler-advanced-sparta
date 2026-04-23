package com.wookjae.scheduler.comment.repository;

import com.wookjae.scheduler.comment.entity.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// soft delete 적용
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByScheduleIdAndDeletedFalse(Long scheduleId);
    Optional<Comment> findByIdAndDeletedFalse(Long id);
}