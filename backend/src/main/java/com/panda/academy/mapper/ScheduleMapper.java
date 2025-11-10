package com.panda.academy.mapper;

import com.panda.academy.dto.ScheduleDto;
import com.panda.academy.entity.Schedule;
import org.mapstruct.Mapper;

@Mapper(uses = {CourseMapper.class, TeacherMapper.class, ClassroomMapper.class, TimeSlotMapper.class, SemesterMapper.class, SpecializationMapper.class})
public interface ScheduleMapper {
    ScheduleDto toDto(Schedule schedule);
    Schedule toEntity(ScheduleDto scheduleDto);
}
