package com.wookjae.scheduler.schedule.controller;

import com.wookjae.scheduler.schedule.dto.CreateScheduleRequest;
import com.wookjae.scheduler.schedule.dto.CreateScheduleResponse;
import com.wookjae.scheduler.schedule.dto.GetScheduleResponse;
import com.wookjae.scheduler.schedule.dto.UpdateScheduleRequest;
import com.wookjae.scheduler.schedule.dto.UpdateScheduleResponse;
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
    public ResponseEntity<CreateScheduleResponse> create(
        @Valid @RequestBody CreateScheduleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request));
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll());
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getOne(
        @PathVariable Long scheduleId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }

    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> update(
        @PathVariable Long scheduleId,
        @Valid @RequestBody UpdateScheduleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId, request));
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
        @PathVariable Long scheduleId
    ) {
        scheduleService.delete(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}