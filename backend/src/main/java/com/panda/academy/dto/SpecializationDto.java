package com.panda.academy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecializationDto {

    private Long id;
    private String name;
    private Long roomTypeId;
    private String description;
}
