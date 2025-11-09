package com.panda.academy.mapper;

import com.panda.academy.dto.SemesterDto;
import com.panda.academy.entity.Semester;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SemesterMapper {

    SemesterMapper INSTANCE = Mappers.getMapper(SemesterMapper.class);

    SemesterDto semesterToSemesterDto(Semester semester);

    Semester semesterDtoToSemester(SemesterDto semesterDto);
}
