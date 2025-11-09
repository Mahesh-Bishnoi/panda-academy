package com.panda.academy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCourseHistoryDto {

    private Long id;
    private Long studentId;
    private Long courseId;
    private Long semesterId;
    private CourseCompletionStatusDto status;
    private LocalDateTime createdAt;
}
