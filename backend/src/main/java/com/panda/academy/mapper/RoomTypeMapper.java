package com.panda.academy.mapper;

import com.panda.academy.dto.RoomTypeDto;
import com.panda.academy.entity.RoomType;
import org.mapstruct.Mapper;

@Mapper
public interface RoomTypeMapper {
    RoomTypeDto roomTypeToRoomTypeDto(RoomType roomType);
    RoomType roomTypeDtoToRoomType(RoomTypeDto roomTypeDto);
}
