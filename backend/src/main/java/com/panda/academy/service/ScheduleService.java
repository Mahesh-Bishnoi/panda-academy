package com.panda.academy.service;

import com.panda.academy.dto.ScheduleGenerationResultDto;

import java.util.List;

public interface ScheduleService {
    ScheduleGenerationResultDto generateSchedule(Long semesterId);
}
