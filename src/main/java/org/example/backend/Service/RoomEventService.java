package org.example.backend.Service;

import org.example.backend.Events.Log;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoomEventService {

    public List<Log> getRoomEventsByRoomNo(int roomNo);
}
