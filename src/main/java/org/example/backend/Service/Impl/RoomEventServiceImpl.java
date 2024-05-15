package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.Events.Log;
import org.example.backend.Repository.RoomEventRepository;
import org.example.backend.Service.RoomEventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomEventServiceImpl implements RoomEventService {

    private final RoomEventRepository rmEventRepo;
    @Override
    public List<Log> getRoomEventsByRoomNo(int roomNo) {
        return rmEventRepo.getRoomEventByRoomNo(roomNo);
    }
}
