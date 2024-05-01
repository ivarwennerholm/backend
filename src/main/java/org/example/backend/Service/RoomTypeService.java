package org.example.backend.Service;

import org.example.backend.DTO.RoomTypeDto;
import org.example.backend.Model.RoomType;

import java.util.List;

public interface RoomTypeService {
    public RoomTypeDto roomTypeToRoomTypeDto(RoomType rt);

    public RoomType roomTypeDtoToRoomType(RoomTypeDto rtd);

    public List<RoomTypeDto> getAll();

    public void addRoomType(RoomTypeDto rtd);
}
