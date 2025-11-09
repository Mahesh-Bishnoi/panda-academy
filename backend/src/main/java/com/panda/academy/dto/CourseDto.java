package com.panda.academy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {

    private Long id;
    private String code;
    private String name;
    private String description;
    private BigDecimal credits;
    private Integer hoursPerWeek;
    private Long specializationId;
    private Long prerequisiteId;
    private CourseTypeDto courseType;
    private Integer gradeLevelMin;
    private Integer gradeLevelMax;
    private Integer semesterOrder;
    private LocalDateTime createdAt;
}
