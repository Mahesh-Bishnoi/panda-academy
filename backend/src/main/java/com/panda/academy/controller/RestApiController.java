package com.panda.academy.controller;

import com.panda.academy.dto.TeacherDto;
import com.panda.academy.repository.StudentRepository;
import com.panda.academy.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestApiController {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherDto>> teachers(){
        return ResponseEntity.ok(teacherRepository.findAll().stream().map(TeacherDto::fromEntity).toList());
    }
}
