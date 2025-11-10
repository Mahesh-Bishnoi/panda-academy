package com.panda.academy.mapper;

import com.panda.academy.dto.SemesterDto;
import com.panda.academy.entity.Semester;
import org.mapstruct.Mapper;

@Mapper
public interface SemesterMapper {
    SemesterDto semesterToSemesterDto(Semester semester);
    Semester semesterDtoToSemester(SemesterDto semesterDto);
}
