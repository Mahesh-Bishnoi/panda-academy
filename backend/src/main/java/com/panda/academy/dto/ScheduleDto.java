package com.panda.academy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDto {
    private ScheduleDto id;
    private CourseDto course;
    private TeacherDto teacher;
    private ClassroomDto classroom;
    private TimeSlotDto timeSlot;
    private SemesterDto semester;
}
