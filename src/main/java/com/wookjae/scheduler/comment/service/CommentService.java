package com.wookjae.scheduler.comment.service;

import com.wookjae.scheduler.comment.dto.CommentCreateRequest;
import com.wookjae.scheduler.comment.dto.CommentCreateResponse;
import com.wookjae.scheduler.comment.dto.CommentGetResponse;
import com.wookjae.scheduler.comment.dto.CommentUpdateRequest;
import com.wookjae.scheduler.comment.dto.CommentUpdateResponse;
import com.wookjae.scheduler.comment.entity.Comment;
import com.wookjae.scheduler.comment.repository.CommentRepository;
import com.wookjae.scheduler.global.auth.AuthValidator;
import com.wookjae.scheduler.global.auth.SessionUser;
import com.wookjae.scheduler.global.exception.*;
import com.wookjae.scheduler.schedule.entity.Schedule;
import com.wookjae.scheduler.schedule.repository.ScheduleRepository;
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
        AuthValidator.validateLogin(sessionUser);
        User user = findUserById(sessionUser.getId());
        Schedule schedule = findScheduleById(scheduleId);

        Comment comment = new Comment(request.getContent(), user, schedule);
        Comment savedComment = commentRepository.save(comment);
        return CommentCreateResponse.from(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentGetResponse> findAll(Long scheduleId) {
        findScheduleById(scheduleId);

        return commentRepository.findAllByScheduleIdAndDeletedFalse(scheduleId).stream()
            .map(CommentGetResponse::from)
            .toList();
    }

    @Transactional
    public CommentUpdateResponse update(Long scheduleId, Long commentId, SessionUser sessionUser, CommentUpdateRequest request) {
        AuthValidator.validateLogin(sessionUser);
        Comment comment = findCommentById(commentId);
        validateCommentSchedule(scheduleId, comment);
        validateCommentOwner(sessionUser, comment, "댓글을 수정할 권한이 없습니다.");

        comment.update(request.getContent());
        return CommentUpdateResponse.from(comment);
    }

    @Transactional
    public void delete(Long scheduleId, Long commentId, SessionUser sessionUser) {
        AuthValidator.validateLogin(sessionUser);
        Comment comment = findCommentById(commentId);
        validateCommentSchedule(scheduleId, comment);
        validateCommentOwner(sessionUser, comment, "댓글을 삭제할 권한이 없습니다.");

        comment.softDelete();
    }

    private User findUserById(Long userId) {
        return userRepository.findByIdAndDeletedFalse(userId).orElseThrow(
            () -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private Schedule findScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdAndDeletedFalse(scheduleId)
            .orElseThrow(() -> new ScheduleNotFoundException("일정을 찾을 수 없습니다."));

        if (schedule.getUser().isDeleted()) {
            throw new ScheduleNotFoundException("일정을 찾을 수 없습니다.");
        }

        return schedule;
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findByIdAndDeletedFalse(commentId).orElseThrow(
            () -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
    }

    private void validateCommentSchedule(Long scheduleId, Comment comment) {
        if (!comment.getSchedule().getId().equals(scheduleId)) {
            throw new BadRequestException("해당 일정의 댓글이 아닙니다.");
        }
    }

    private void validateCommentOwner(SessionUser sessionUser, Comment comment, String message) {
        if (!comment.getUser().getId().equals(sessionUser.getId())) {
            throw new ForbiddenException(message);
        }
    }
}