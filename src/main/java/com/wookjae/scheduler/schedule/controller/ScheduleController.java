package com.wookjae.scheduler.schedule.controller;

import com.wookjae.scheduler.schedule.dto.ScheduleCreateRequest;
import com.wookjae.scheduler.schedule.dto.ScheduleCreateResponse;
import com.wookjae.scheduler.schedule.dto.ScheduleDeleteRequest;
import com.wookjae.scheduler.schedule.dto.ScheduleGetResponse;
import com.wookjae.scheduler.schedule.dto.ScheduleUpdateRequest;
import com.wookjae.scheduler.schedule.dto.ScheduleUpdateResponse;
import com.wookjae.scheduler.schedule.service.ScheduleService;
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

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleCreateResponse> create(
        @Valid @RequestBody ScheduleCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request));
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
        @Valid @RequestBody ScheduleUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId, request));
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> delete(
        @PathVariable Long scheduleId,
        @Valid @RequestBody ScheduleDeleteRequest request
    ) {
        scheduleService.delete(scheduleId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}