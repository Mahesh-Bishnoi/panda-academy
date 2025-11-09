package com.panda.academy.mapper;

import com.panda.academy.dto.SpecializationDto;
import com.panda.academy.entity.Specialization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SpecializationMapper {

    SpecializationMapper INSTANCE = Mappers.getMapper(SpecializationMapper.class);

    @Mapping(source = "roomType.id", target = "roomTypeId")
    SpecializationDto specializationToSpecializationDto(Specialization specialization);

}
