package com.panda.academy.mapper;

import com.panda.academy.dto.TeacherDto;
import com.panda.academy.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherMapper {

    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    @Mapping(source = "specialization.id", target = "specializationId")
    TeacherDto teacherToTeacherDto(Teacher teacher);

}
