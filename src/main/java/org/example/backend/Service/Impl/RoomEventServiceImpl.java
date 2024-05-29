package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.Events.RoomEvent;
import org.example.backend.Repository.RoomEventRepository;
import org.example.backend.Service.RoomEventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomEventServiceImpl implements RoomEventService {

    private final RoomEventRepository rmEventRepo;

    @Override
    public List<RoomEvent> getRoomEventsByRoomNo(int roomNo) {
        return rmEventRepo.getRoomEventByRoomNo(roomNo);
    }
}
