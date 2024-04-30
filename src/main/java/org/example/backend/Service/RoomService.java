package org.example.backend.Service;

import org.example.backend.DTO.RoomDto;
import org.example.backend.Model.Room;

import java.util.List;

public interface RoomService {

    public RoomDto roomToRoomDto(Room r);

    public Room roomDtoToRoom(RoomDto room);

    public List<RoomDto> getAll();

    public RoomDto getRoomById(long id);
}