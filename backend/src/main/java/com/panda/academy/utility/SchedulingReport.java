package com.panda.academy.utility;

import com.panda.academy.entity.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Getter
@Component
public class SchedulingReport {
    private final Map<String, String> failedCourses = new HashMap<>();
    private final Map<String, Integer> remainingTeacherSlots = new HashMap<>();
    private final Map<String, Integer> remainingClassroomSlots = new HashMap<>();
    private final Map<String, Integer> teacherDailyHours = new HashMap<>();

    public void recordFailure(Course course, String reason) {
        failedCourses.put(course.getName() + " | " + course.getSpecialization().getRoomType().getName(), reason);
    }

    public void recordRemainingAvailability(Map<Teacher, Set<TimeSlot>> teacherAvailability
            , Map<Classroom, Set<TimeSlot>> classroomAvailability
            ,Map<Teacher, Map<DayOfWeek, Integer>> teacherDailyHoursMap
    ) {
        teacherAvailability.forEach((t, slots) -> remainingTeacherSlots.put(t.getFirstName() + " " + t.getLastName() + " | " + t.getSpecialization().getName() + " | " + t.getSpecialization().getRoomType().getName(), slots.size()));
        classroomAvailability.forEach((c, slots) -> remainingClassroomSlots.put(c.getName() + " | " + c.getRoomType().getName(), slots.size()));
        teacherDailyHoursMap.forEach((t,avm) -> avm.forEach((d,h) -> {
            if (!DayOfWeek.SATURDAY.equals(d) && !DayOfWeek.SUNDAY.equals(d)) {
                teacherDailyHours.put(t.getFirstName() + " " + t.getLastName() + " | " + d.name(), h);
            }
        }));
    }

    public void logSummary() {
        if (failedCourses.isEmpty()) {
            log.info("All courses successfully scheduled.");
        } else {
            log.warn("Scheduling failures detected for {} courses:", failedCourses.size());
            failedCourses.forEach((course, reason) -> log.warn(" - {}: {}", course, reason));
        }

        log.info("Remaining teacher availability:");
        remainingTeacherSlots.forEach((t, count) -> log.info(" - {} has {} free slots", t, count));

        log.info("Remaining classroom availability:");
        remainingClassroomSlots.forEach((c, count) -> log.info(" - Room {} has {} free slots", c, count));
    }
}