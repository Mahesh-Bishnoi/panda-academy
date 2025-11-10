package com.panda.academy.mapper;

import com.panda.academy.dto.TeacherDto;
import com.panda.academy.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {SpecializationMapper.class})
public interface TeacherMapper {
    @Mapping(source = "specialization.id", target = "specializationId")
    TeacherDto teacherToTeacherDto(Teacher teacher);
}
