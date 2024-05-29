package org.example.backend.Service;

import org.example.backend.DTO.RoomDto;
import org.example.backend.Model.Room;

import java.util.List;

public interface RoomService {

    RoomDto roomToRoomDto(Room r);

    Room roomDtoToRoom(RoomDto room);

    List<RoomDto> getAll();

    RoomDto getRoomById(long id);

}
