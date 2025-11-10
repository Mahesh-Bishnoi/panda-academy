package com.panda.academy.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ScheduleGenerationResultDto {
    private List<ScheduleDto> schedules;
    private Map<String, String> failedCourses;
    private Map<String, Integer> remainingTeacherAvailability;
    private Map<String, Integer> remainingClassroomAvailability;
    private Map<String, Integer> teacherDailyHours;
}
