package com.wookjae.scheduler.comment.service;

import com.wookjae.scheduler.comment.dto.CommentCreateRequest;
import com.wookjae.scheduler.comment.dto.CommentCreateResponse;
import com.wookjae.scheduler.comment.dto.CommentGetResponse;
import com.wookjae.scheduler.comment.dto.CommentUpdateRequest;
import com.wookjae.scheduler.comment.dto.CommentUpdateResponse;
import com.wookjae.scheduler.comment.entity.Comment;
import com.wookjae.scheduler.comment.repository.CommentRepository;
import com.wookjae.scheduler.schedule.entity.Schedule;
import com.wookjae.scheduler.schedule.repository.ScheduleRepository;
import com.wookjae.scheduler.user.dto.SessionUser;
import com.wookjae.scheduler.user.entity.User;
import com.wookjae.scheduler.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CommentCreateResponse save(Long scheduleId, SessionUser sessionUser, CommentCreateRequest request) {

        validateLogin(sessionUser);

        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
            () -> new IllegalStateException("해당 유저가 없습니다.")
        );

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
            () -> new IllegalStateException("해당 일정이 없습니다.")
        );

        Comment comment = new Comment(
            request.getContent(),
            user,
            schedule
        );

        Comment savedComment = commentRepository.save(comment);
        return new CommentCreateResponse(
            savedComment.getId(),
            savedComment.getContent(),
            savedComment.getUser().getId(),
            savedComment.getSchedule().getId(),
            savedComment.getCreatedAt(),
            savedComment.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<CommentGetResponse> findAll(Long scheduleId) {
        List<Comment> comments = commentRepository.findAllByScheduleId(scheduleId);

        return comments.stream()
            .map(comment -> new CommentGetResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getSchedule().getId(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
            )).toList();
    }

    @Transactional
    public CommentUpdateResponse update(Long scheduleId, Long commentId, SessionUser sessionUser, CommentUpdateRequest request) {

        validateLogin(sessionUser);

        Comment comment = commentRepository.findById(commentId).orElseThrow(
            () -> new IllegalStateException("해당 댓글이 없습니다.")
        );

        if (!comment.getSchedule().getId().equals(scheduleId)) {
            throw new IllegalStateException("해당 일정에 속한 댓글이 아닙니다.");
        }

        if (!comment.getUser().getId().equals(sessionUser.getId())) {
            throw new IllegalStateException("댓글 수정 권한이 없습니다.");
        }

        comment.update(request.getContent());

        return new CommentUpdateResponse(
            comment.getId(),
            comment.getContent(),
            comment.getUser().getId(),
            comment.getSchedule().getId(),
            comment.getCreatedAt(),
            comment.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long scheduleId, Long commentId, SessionUser sessionUser) {

        validateLogin(sessionUser);

        Comment comment = commentRepository.findById(commentId).orElseThrow(
            () -> new IllegalStateException("해당 댓글이 없습니다.")
        );

        if (!comment.getSchedule().getId().equals(scheduleId)) {
            throw new IllegalStateException("해당 일정에 속한 댓글이 아닙니다.");
        }

        if (!comment.getUser().getId().equals(sessionUser.getId())) {
            throw new IllegalStateException("댓글 삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }

    private void validateLogin(SessionUser sessionUser) {
        if (sessionUser == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
    }
}