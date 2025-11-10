package com.panda.academy.controller;

import com.panda.academy.dto.ScheduleDto;
import com.panda.academy.dto.SemesterDto;
import com.panda.academy.mapper.SemesterMapper;
import com.panda.academy.repository.SemesterRepository;
import com.panda.academy.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestApiController {
    private final SemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;
    private final ScheduleService scheduleService;

    @GetMapping("/semesters")
    public ResponseEntity<List<SemesterDto>> semesters(){
        return ResponseEntity.ok(semesterRepository.findAll().stream().map(semesterMapper::semesterToSemesterDto).toList());
    }

    @PostMapping("/schedules/generate/{semesterId}")
    public ResponseEntity<List<ScheduleDto>> generateSchedule(@PathVariable Long semesterId) {
        return ResponseEntity.ok(scheduleService.generateSchedule(semesterId));
    }
}
