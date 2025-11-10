package com.panda.academy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleIdDto {
    private Long courseId;
    private Long teacherId;
    private Long classroomId;
    private Long timeSlotId;
    private Long semesterId;
}
