package com.panda.academy.service;

import com.panda.academy.dto.ScheduleDto;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDto> generateSchedule(Long semesterId);
}
