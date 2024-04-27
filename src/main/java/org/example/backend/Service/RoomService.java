package org.example.backend.Service;

import org.example.backend.DTO.RoomDto;
import org.example.backend.Model.Room;

import java.util.List;

public interface RoomService {

    public RoomDto roomToRoomDto(Room r);

    public List<RoomDto> getAllRooms();

    Room roomDtoToRoom(RoomDto room);
}