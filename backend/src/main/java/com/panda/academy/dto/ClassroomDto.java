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
public class ClassroomDto {

    private Long id;
    private String name;
    private Integer roomTypeId;
    private Integer capacity;
    private String equipment;
    private Integer floor;
    private LocalDateTime createdAt;
}
