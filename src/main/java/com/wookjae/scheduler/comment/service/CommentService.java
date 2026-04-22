package com.wookjae.scheduler.comment.service;

import com.wookjae.scheduler.comment.dto.CommentCreateRequest;
import com.wookjae.scheduler.comment.dto.CommentCreateResponse;
import com.wookjae.scheduler.comment.dto.CommentGetResponse;
import com.wookjae.scheduler.comment.dto.CommentUpdateRequest;
import com.wookjae.scheduler.comment.dto.CommentUpdateResponse;
import com.wookjae.scheduler.comment.entity.Comment;
import com.wookjae.scheduler.comment.repository.CommentRepository;
import com.wookjae.scheduler.global.exception.*;
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
            () -> new UserNotFoundException("사용자를 찾을 수 없습니다.")
        );

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
            () -> new ScheduleNotFoundException("일정을 찾을 수 없습니다.")
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
            () -> new CommentNotFoundException("댓글을 찾을 수 없습니다.")
        );

        if (!comment.getSchedule().getId().equals(scheduleId)) {
            throw new BadRequestException("해당 일정의 댓글이 아닙니다.");
        }

        if (!comment.getUser().getId().equals(sessionUser.getId())) {
            throw new ForbiddenException("댓글을 수정할 권한이 없습니다.");
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
            () -> new CommentNotFoundException("댓글을 찾을 수 없습니다.")
        );

        if (!comment.getSchedule().getId().equals(scheduleId)) {
            throw new BadRequestException("해당 일정의 댓글이 아닙니다.");
        }

        if (!comment.getUser().getId().equals(sessionUser.getId())) {
            throw new ForbiddenException("댓글을 삭제할 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }

    private void validateLogin(SessionUser sessionUser) {
        if (sessionUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
    }
}