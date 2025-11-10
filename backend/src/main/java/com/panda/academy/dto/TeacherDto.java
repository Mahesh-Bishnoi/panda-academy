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
public class TeacherDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Long specializationId;
    private String email;
    private Integer maxDailyHours;
    private LocalDateTime createdAt;
}
