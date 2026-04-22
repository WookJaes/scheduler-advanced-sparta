package com.wookjae.scheduler.schedule.repository;

import com.wookjae.scheduler.schedule.dto.SchedulePageResponse;
import com.wookjae.scheduler.schedule.entity.Schedule;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

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