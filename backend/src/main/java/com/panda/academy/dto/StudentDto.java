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
public class StudentDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer gradeLevel;
    private Integer enrollmentYear;
    private Integer expectedGraduationYear;
    private StudentStatusDto status;
    private LocalDateTime createdAt;
}
