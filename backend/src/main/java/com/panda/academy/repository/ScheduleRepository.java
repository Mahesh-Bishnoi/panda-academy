package com.panda.academy.repository;

import com.panda.academy.entity.Schedule;
import com.panda.academy.entity.ScheduleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, ScheduleId> {
    // Check if a schedule with the same combination exists
    boolean existsById(@NonNull ScheduleId id);

    // Find all schedules for a specific teacher
    List<Schedule> findByTeacherId(Long teacherId);

    // Find all schedules for a specific classroom
    List<Schedule> findByClassroomId(Long classroomId);

    // Find all schedules for a specific time slot
    List<Schedule> findByTimeSlotId(Long timeSlotId);

    // Find all schedules for a specific semester
    List<Schedule> findBySemesterId(Long semesterId);

    // Find all schedules for a specific course
    List<Schedule> findByCourseId(Long courseId);

    // Find schedules by teacher and time slot (useful for conflict checking)
    List<Schedule> findByTeacherIdAndTimeSlotId(Long teacherId, Long timeSlotId);

    // Find schedules by classroom and time slot (useful for conflict checking)
    List<Schedule> findByClassroomIdAndTimeSlotId(Long classroomId, Long timeSlotId);

    // Find schedules by teacher, classroom, time slot, and semester (full conflict check)
    List<Schedule> findByTeacherIdAndClassroomIdAndTimeSlotIdAndSemesterId(
            Long teacherId,
            Long classroomId,
            Long timeSlotId,
            Long semesterId
    );
}
