package org.example.backend.Service;

import org.example.backend.DTO.RoomTypeDto;
import org.example.backend.Model.RoomType;

import java.util.List;

public interface RoomTypeService {
    RoomTypeDto roomTypeToRoomTypeDto(RoomType rt);

    RoomType roomTypeDtoToRoomType(RoomTypeDto rtd);

    List<RoomTypeDto> getAll();

    String addRoomType(RoomTypeDto rtd);

}
