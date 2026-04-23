package com.wookjae.scheduler.schedule.repository;

import com.wookjae.scheduler.schedule.dto.SchedulePageResponse;
import com.wookjae.scheduler.schedule.entity.Schedule;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// soft delete 적용
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 일정 목록 조회 (댓글 수 집계 + 작성자 이름 포함, Soft Delete 적용)
    @Query(
        value = """
            select new com.wookjae.scheduler.schedule.dto.SchedulePageResponse(
            s.id, s.title, s.content, count(c.id), s.createdAt, s.modifiedAt, u.name
        )
            from Schedule s
            join s.user u
            left join Comment c on c.schedule = s and c.deleted = false
            where s.deleted = false
            and u.deleted = false
            group by s.id, s.title, s.content, s.createdAt, s.modifiedAt, u.name
            """,
        countQuery = """
            select count(s)
            from Schedule s
            join s.user u
            where s.deleted = false
            and u.deleted = false
        """
    )
    Page<SchedulePageResponse> findAllSchedules(Pageable pageable);
    Optional<Schedule> findByIdAndDeletedFalse(Long id);
}