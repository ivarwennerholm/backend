package org.example.backend.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Events.RoomEvent;
import org.example.backend.Repository.RoomEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomEventService {

    private final RoomEventRepository rmEventRepo;

    public List<RoomEvent> getRoomEventsByRoomNo(int roomNo) {
        return rmEventRepo.getRoomEventByRoomNo(roomNo);
    }
}
