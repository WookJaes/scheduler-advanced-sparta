package com.wookjae.scheduler.schedule.service;

import com.wookjae.scheduler.schedule.dto.CreateScheduleRequest;
import com.wookjae.scheduler.schedule.dto.CreateScheduleResponse;
import com.wookjae.scheduler.schedule.dto.DeleteScheduleRequest;
import com.wookjae.scheduler.schedule.dto.GetScheduleResponse;
import com.wookjae.scheduler.schedule.dto.UpdateScheduleRequest;
import com.wookjae.scheduler.schedule.dto.UpdateScheduleResponse;
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
public class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(
            () -> new IllegalStateException("존재하지 않는 유저입니다."));

        Schedule schedule = new Schedule(
            request.getTitle(),
            request.getContent(),
            user
        );

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
            savedSchedule.getId(),
            savedSchedule.getTitle(),
            savedSchedule.getContent(),
            savedSchedule.getCreatedAt(),
            savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetScheduleResponse> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
            .map(schedule -> new GetScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
            )).toList();
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
            () -> new IllegalStateException("해당 일정이 없습니다."));

        return new GetScheduleResponse(
            schedule.getId(),
            schedule.getUser().getId(),
            schedule.getTitle(),
            schedule.getContent(),
            schedule.getCreatedAt(),
            schedule.getModifiedAt()
        );
    }

    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
            () -> new IllegalStateException("해당 일정이 없습니다."));

        if (request.getUserId() == null || !request.getUserId().equals(schedule.getUser().getId())) {
            throw new IllegalStateException("대상 유저가 아닙니다.");
        }

        schedule.update(
            request.getTitle(),
            request.getContent()
        );
        return new UpdateScheduleResponse(
            schedule.getId(),
            schedule.getTitle(),
            schedule.getContent(),
            schedule.getCreatedAt(),
            schedule.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long scheduleId, DeleteScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
            () -> new IllegalStateException("해당 일정이 없습니다."));

        if (request.getUserId() == null || !request.getUserId().equals(schedule.getUser().getId())) {
            throw new IllegalStateException("대상 유저가 아닙니다.");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}