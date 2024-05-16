package org.example.backend.Service;

import org.example.backend.Events.RoomEvent;

import java.util.List;

public interface RoomEventService {

    public List<RoomEvent> getRoomEventsByRoomNo(int roomNo);
}
