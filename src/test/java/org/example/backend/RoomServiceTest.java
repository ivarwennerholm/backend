package org.example.backend;

import org.example.backend.DTO.RoomDto;
import org.example.backend.DTO.RoomTypeDto;
import org.example.backend.Model.Room;
import org.example.backend.Model.RoomType;
import org.example.backend.Repository.RoomRepository;
import org.example.backend.Repository.RoomTypeRepository;
import org.example.backend.Service.Impl.RoomServiceImpl;
import org.example.backend.Service.Impl.RoomTypeServiceImpl;
import org.example.backend.Service.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository rmRepo;

    @Mock
    private RoomTypeRepository rtRepo;

    @InjectMocks
    private RoomServiceImpl rmService;

    @InjectMocks
    private RoomTypeServiceImpl rtService;

//    @Override
//    public List<RoomDto> getAll() {
//        return roomRepository.findAll().stream().map(r -> roomToRoomDto(r)).toList();
//    }
//
//    @Override
//    public RoomDto getRoomById(long id) {
//        return roomToRoomDto(roomRepository.findById(id).orElse(null));
//
//    }
//
//    @Override
//    public Room roomDtoToRoom(RoomDto rd) {
//        RoomType roomType = roomTypeService.roomTypeDtoToRoomType(rd.getRoomType());
//        return Room.builder().
//                id(rd.getId()).
//                roomNumber(rd.getRoomNumber()).
//                roomType(roomType).
//                build();
//    }

//    @Test
//    public void roomDtoToRoomTest(){
//        RoomTypeDto rtDto = RoomTypeDto.builder().
//                id(1L).
//                type("single").
//                maxExtraBed(0).
//                maxPerson(1).
//                pricePerNight(500).
//                build();
//
//        RoomType rt = RoomType.builder().
//                id(1L).
//                type("single").
//                maxExtraBed(0).
//                maxPerson(1).
//                pricePerNight(500).
//                build();
//
//        RoomDto rmDto = RoomDto.builder().
//                id(1L).
//                roomNumber(2099).
//                roomType(rtDto).
//                build();
//
//        when(rtService.roomTypeDtoToRoomType(rmDto.getRoomType())).thenReturn(rt);
//
//
//        Room rm = rmService.roomDtoToRoom(rmDto);
//
//        Assertions.assertTrue(rm.getId()==1L);
//        Assertions.assertTrue(rm.getRoomNumber()==2099);
//        Assertions.assertTrue(rm.getRoomType().getId()==1L);
//        Assertions.assertTrue(rm.getRoomType().getType().equals("single"));
//        Assertions.assertTrue(rm.getRoomType().getMaxExtraBed()==0);
//        Assertions.assertTrue(rm.getRoomType().getMaxPerson()==1);
//        Assertions.assertTrue(rm.getRoomType().getPricePerNight()==500);
//    }

//
//    @Override
//    public RoomDto roomToRoomDto(Room r){
//        RoomTypeDto roomTypeDto = roomTypeService.roomTypeToRoomTypeDto(roomTypeRepository.findById(r.getId()).orElse(null));
//        return RoomDto.builder().
//                id(r.getId()).
//                roomNumber(r.getRoomNumber()).
//                roomType(roomTypeDto).
//                build();
//    }

    @Test
    void roomDtoToRoom_ConvertsRoomDtoToRoom() {
        // Given
        RoomType roomType = new RoomType(1L, "Single", 1, 1, 100.0);
        RoomDto roomDto = new RoomDto(1L, 101, new RoomTypeDto(1L, "Single", 1, 1, 100.0));
        when(rtService.roomTypeDtoToRoomType(Mockito.any(RoomTypeDto.class))).thenReturn(roomType);

        // When
        Room room = rmService.roomDtoToRoom(roomDto);

        // Then
        assertEquals(roomDto.getId(), room.getId());
        assertEquals(roomDto.getRoomNumber(), room.getRoomNumber());
        assertEquals(roomType, room.getRoomType());
    }
}
