package com.panda.academy.mapper;

import com.panda.academy.dto.TimeSlotDto;
import com.panda.academy.entity.TimeSlot;
import org.mapstruct.Mapper;

@Mapper
public interface TimeSlotMapper {
    TimeSlotDto toDto(TimeSlot timeSlot);
    TimeSlot toEntity(TimeSlotDto timeSlotDto);
}
