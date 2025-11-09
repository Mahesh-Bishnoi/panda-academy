package com.panda.academy.mapper;

import com.panda.academy.dto.CourseDto;
import com.panda.academy.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(source = "specialization.id", target = "specializationId")
    @Mapping(source = "prerequisite.id", target = "prerequisiteId")
    CourseDto courseToCourseDto(Course course);

}
