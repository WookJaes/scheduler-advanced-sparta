package com.wookjae.scheduler.schedule.service;

import com.wookjae.scheduler.global.exception.*;
import com.wookjae.scheduler.schedule.dto.ScheduleCreateRequest;
import com.wookjae.scheduler.schedule.dto.ScheduleCreateResponse;
import com.wookjae.scheduler.schedule.dto.ScheduleGetResponse;
import com.wookjae.scheduler.schedule.dto.SchedulePageResponse;
import com.wookjae.scheduler.schedule.dto.ScheduleUpdateRequest;
import com.wookjae.scheduler.schedule.dto.ScheduleUpdateResponse;
import com.wookjae.scheduler.schedule.entity.Schedule;
import com.wookjae.scheduler.schedule.repository.ScheduleRepository;
import com.wookjae.scheduler.user.dto.SessionUser;
import com.wookjae.scheduler.user.entity.User;
import com.wookjae.scheduler.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleCreateResponse save(SessionUser sessionUser, ScheduleCreateRequest request) {
        validateLogin(sessionUser);

        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
            () -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        Schedule schedule = new Schedule(
            request.getTitle(),
            request.getContent(),
            user
        );

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleCreateResponse(
            savedSchedule.getId(),
            savedSchedule.getTitle(),
            savedSchedule.getContent(),
            savedSchedule.getCreatedAt(),
            savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<SchedulePageResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size,
            Sort.by(Sort.Direction.DESC, "modifiedAt"));
        return scheduleRepository.findAllSchedules(pageable);
    }

    @Transactional(readOnly = true)
    public ScheduleGetResponse findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
            () -> new ScheduleNotFoundException("일정을 찾을 수 없습니다."));

        return new ScheduleGetResponse(
            schedule.getId(),
            schedule.getUser().getId(),
            schedule.getTitle(),
            schedule.getContent(),
            schedule.getCreatedAt(),
            schedule.getModifiedAt()
        );
    }

    @Transactional
    public ScheduleUpdateResponse update(Long scheduleId, SessionUser sessionUser, ScheduleUpdateRequest request) {
        validateLogin(sessionUser);

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
            () -> new ScheduleNotFoundException("일정을 찾을 수 없습니다."));

        if (!sessionUser.getId().equals(schedule.getUser().getId())) {
            throw new ForbiddenException("일정을 수정할 권한이 없습니다.");
        }

        schedule.update(
            request.getTitle(),
            request.getContent()
        );
        return new ScheduleUpdateResponse(
            schedule.getId(),
            schedule.getTitle(),
            schedule.getContent(),
            schedule.getCreatedAt(),
            schedule.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long scheduleId, SessionUser sessionUser) {
        validateLogin(sessionUser);

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
            () -> new ScheduleNotFoundException("일정을 찾을 수 없습니다."));

        if (!sessionUser.getId().equals(schedule.getUser().getId())) {
            throw new ForbiddenException("일정을 삭제할 권한이 없습니다.");
        }
        scheduleRepository.delete(schedule);
    }

    private void validateLogin(SessionUser sessionUser) {
        if (sessionUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
    }
}