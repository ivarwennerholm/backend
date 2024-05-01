package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.RoomDto;
import org.example.backend.DTO.RoomTypeDto;
import org.example.backend.Model.Room;
import org.example.backend.Model.RoomType;
import org.example.backend.Repository.RoomRepository;
import org.example.backend.Repository.RoomTypeRepository;
import org.example.backend.Service.RoomService;
import org.example.backend.Service.RoomTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    final private RoomRepository roomRepository;
    final private RoomTypeRepository roomTypeRepository;
    final private RoomTypeService roomTypeService;

    @Override
    public List<RoomDto> getAll() {
        return roomRepository.findAll().stream().map(r -> roomToRoomDto(r)).toList();
    }

    @Override
    public RoomDto getRoomById(long id) {
        return roomToRoomDto(roomRepository.findById(id).orElse(null));

    }

    @Override
    public Room roomDtoToRoom(RoomDto rd) {
        RoomType roomType = roomTypeService.roomTypeDtoToRoomType(rd.getRoomType());
        return Room.builder().
                id(rd.getId()).
                roomNumber(rd.getRoomNumber()).
                roomType(roomType).
                build();
    }

    @Override
    public RoomDto roomToRoomDto(Room r){
        RoomTypeDto roomTypeDto = roomTypeService.roomTypeToRoomTypeDto(roomTypeRepository.findById(r.getId()).orElse(null));
        return RoomDto.builder().
                id(r.getId()).
                roomNumber(r.getRoomNumber()).
                roomType(roomTypeDto).
                build();
    }

}