package com.panda.academy.mapper;

import com.panda.academy.dto.StudentCourseHistoryDto;
import com.panda.academy.entity.StudentCourseHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StudentCourseHistoryMapper {
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "semester.id", target = "semesterId")
    StudentCourseHistoryDto studentCourseHistoryToStudentCourseHistoryDto(StudentCourseHistory studentCourseHistory);
}
