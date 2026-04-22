package com.wookjae.scheduler.comment.controller;

import com.wookjae.scheduler.comment.dto.CommentCreateRequest;
import com.wookjae.scheduler.comment.dto.CommentCreateResponse;
import com.wookjae.scheduler.comment.dto.CommentGetResponse;
import com.wookjae.scheduler.comment.dto.CommentUpdateRequest;
import com.wookjae.scheduler.comment.dto.CommentUpdateResponse;
import com.wookjae.scheduler.comment.service.CommentService;
import com.wookjae.scheduler.user.dto.SessionUser;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentCreateResponse> create(
        @PathVariable Long scheduleId,
        @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
        @Valid @RequestBody CommentCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(scheduleId, sessionUser, request));
    }

    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<CommentGetResponse>> getAll(
        @PathVariable Long scheduleId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll(scheduleId));
    }

    @PatchMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<CommentUpdateResponse> update(
        @PathVariable Long scheduleId,
        @PathVariable Long commentId,
        @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
        @Valid @RequestBody CommentUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(scheduleId, commentId, sessionUser, request));
    }

    @DeleteMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<Void> delete(
        @PathVariable Long scheduleId,
        @PathVariable Long commentId,
        @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ) {
        commentService.delete(scheduleId, commentId, sessionUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}