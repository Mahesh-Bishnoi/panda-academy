package com.panda.academy.mapper;

import com.panda.academy.dto.ClassroomDto;
import com.panda.academy.entity.Classroom;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClassroomMapper {

    ClassroomMapper INSTANCE = Mappers.getMapper(ClassroomMapper.class);

    ClassroomDto classroomToClassroomDto(Classroom classroom);

    Classroom classroomDtoToClassroom(ClassroomDto classroomDto);
}
