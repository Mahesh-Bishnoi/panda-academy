package com.panda.academy.mapper;

import com.panda.academy.dto.SpecializationDto;
import com.panda.academy.entity.Specialization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SpecializationMapper {
    @Mapping(source = "roomType.id", target = "roomTypeId")
    SpecializationDto specializationToSpecializationDto(Specialization specialization);
}
