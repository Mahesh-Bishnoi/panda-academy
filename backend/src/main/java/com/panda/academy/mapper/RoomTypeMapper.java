package com.panda.academy.mapper;

import com.panda.academy.dto.RoomTypeDto;
import com.panda.academy.entity.RoomType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomTypeMapper {

    RoomTypeMapper INSTANCE = Mappers.getMapper(RoomTypeMapper.class);

    RoomTypeDto roomTypeToRoomTypeDto(RoomType roomType);

    RoomType roomTypeDtoToRoomType(RoomTypeDto roomTypeDto);
}
