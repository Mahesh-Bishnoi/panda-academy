package com.panda.academy.mapper;

import com.panda.academy.dto.ClassroomDto;
import com.panda.academy.entity.Classroom;
import org.mapstruct.Mapper;

@Mapper(uses={RoomTypeMapper.class})
public interface ClassroomMapper {
    ClassroomDto classroomToClassroomDto(Classroom classroom);
    Classroom classroomDtoToClassroom(ClassroomDto classroomDto);
}
