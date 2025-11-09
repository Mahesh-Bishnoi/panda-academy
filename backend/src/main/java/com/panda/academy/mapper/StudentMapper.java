package com.panda.academy.mapper;

import com.panda.academy.dto.StudentDto;
import com.panda.academy.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentDto studentToStudentDto(Student student);

    Student studentDtoToStudent(StudentDto studentDto);
}
