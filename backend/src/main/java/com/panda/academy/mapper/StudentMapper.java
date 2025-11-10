package com.panda.academy.mapper;

import com.panda.academy.dto.StudentDto;
import com.panda.academy.entity.Student;
import org.mapstruct.Mapper;

@Mapper
public interface StudentMapper {
    StudentDto studentToStudentDto(Student student);
    Student studentDtoToStudent(StudentDto studentDto);
}
