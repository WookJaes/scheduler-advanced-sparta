package com.wookjae.scheduler.schedule.controller;

import com.wookjae.scheduler.schedule.dto.ScheduleCreateRequest;
import com.wookjae.scheduler.schedule.dto.ScheduleCreateResponse;
import com.wookjae.scheduler.schedule.dto.ScheduleGetResponse;
import com.wookjae.scheduler.schedule.dto.ScheduleUpdateRequest;
import com.wookjae.scheduler.schedule.dto.ScheduleUpdateResponse;
import com.wookjae.scheduler.schedule.service.ScheduleService;
import com.wookjae.scheduler.user.dto.SessionUser;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleCreateResponse> create(
        @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
        @Valid @RequestBody ScheduleCreateRequest request
    ) {
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(sessionUser, request));
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleGetResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll());
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleGetResponse> getOne(
        @PathVariable Long scheduleId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }

    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResponse> update(
        @PathVariable Long scheduleId,
        @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
        @Valid @RequestBody ScheduleUpdateRequest request
    ) {
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId, sessionUser, request));
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> delete(
        @PathVariable Long scheduleId,
        @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ) {
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        scheduleService.delete(scheduleId, sessionUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}