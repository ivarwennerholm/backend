package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.RoomDto;
import org.example.backend.Model.Room;
import org.example.backend.Repository.RoomRepository;
import org.example.backend.Service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    final private RoomRepository rr;

    @Override
    public List<RoomDto> getAllRooms() {
        return rr.findAll().stream().map(r -> roomToRoomDto(r)).toList();
    }

    @Override
    public Room roomDtoToRoom(RoomDto room) {
        return null;
    }

    @Override
    public RoomDto roomToRoomDto(Room r){
        return RoomDto.builder().Id(r.getId())
                .roomNumber(r.getRoomNumber())
                .pricePerNight(r.getPricePerNight())
                .build();
    }

}