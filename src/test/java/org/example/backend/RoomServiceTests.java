package org.example.backend;

import org.example.backend.DTO.RoomDto;
import org.example.backend.DTO.RoomTypeDto;
import org.example.backend.Model.Room;
import org.example.backend.Model.RoomType;
import org.example.backend.Repository.RoomRepository;
import org.example.backend.Repository.RoomTypeRepository;
import org.example.backend.Service.Impl.RoomServiceImpl;
import org.example.backend.Service.Impl.RoomTypeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTests {

    @Mock
    private RoomRepository rmRepo;

    @Mock
    private RoomTypeRepository rtRepo;

    @InjectMocks
    private RoomTypeServiceImpl rtService;

    @InjectMocks
    private RoomServiceImpl rmService;

    @Test
    public void roomToRoomDtoTest(){
        RoomType rt = RoomType.builder().
                id(1L).
                type("single").
                maxExtraBed(0).
                maxPerson(1).
                pricePerNight(500).
                build();

        Room rm = Room.builder().
                id(1L).
                roomNumber(101).
                roomType(rt).
                build();

        when(rtRepo.findById(1L)).thenReturn(Optional.of(rt));
        RoomServiceImpl rmService2 = new RoomServiceImpl(rmRepo,rtRepo,rtService);

        RoomDto rmDto = rmService2.roomToRoomDto(rm);

        assertEquals(1L, rmDto.getId());
        assertEquals(101, rmDto.getRoomNumber());
        assertEquals(1L, rmDto.getRoomType().getId());
        assertEquals("single", rmDto.getRoomType().getType());
        assertEquals(0, rmDto.getRoomType().getMaxExtraBed());
        assertEquals(1, rmDto.getRoomType().getMaxPerson());
        assertEquals(500, rmDto.getRoomType().getPricePerNight());
    }
    @Test
    void roomDtoToRoomTest() {
        RoomType rt = RoomType.builder().
                id(1L).
                type("single").
                maxExtraBed(0).
                maxPerson(1).
                pricePerNight(500).
                build();

        RoomTypeDto rtDto = rtService.roomTypeToRoomTypeDto(rt);
        RoomDto roomDto = new RoomDto(1L, 101, rtDto);

        RoomServiceImpl rmService2 = new RoomServiceImpl(rmRepo,rtRepo,rtService);

        Room room = rmService2.roomDtoToRoom(roomDto);

        assertEquals(1L, room.getId());
        assertEquals(101, room.getRoomNumber());
        assertEquals(1L, room.getRoomType().getId());
        assertEquals("single", room.getRoomType().getType());
        assertEquals(0, room.getRoomType().getMaxExtraBed());
        assertEquals(1, room.getRoomType().getMaxPerson());
        assertEquals(500, room.getRoomType().getPricePerNight());
    }
    @Test
    public void getroomByIdTest(){
        RoomType rt = RoomType.builder().
                id(1L).
                type("single").
                maxExtraBed(0).
                maxPerson(1).
                pricePerNight(500).
                build();

        when(rtRepo.findById(1L)).thenReturn(Optional.of(rt));

        Room rm1 = Room.builder().
                id(1L).
                roomNumber(101).
                roomType(rt).
                build();

        when(rmRepo.findById(1L)).thenReturn(Optional.of(rm1));
        RoomServiceImpl rmService2 = new RoomServiceImpl(rmRepo,rtRepo,rtService);

        RoomDto rmDto = rmService2.getRoomById(1L);

        assertEquals(1L, rmDto.getId());
        assertEquals(101, rmDto.getRoomNumber());
        assertEquals(1L, rmDto.getRoomType().getId());
        assertEquals("single", rmDto.getRoomType().getType());
        assertEquals(0, rmDto.getRoomType().getMaxExtraBed());
        assertEquals(1, rmDto.getRoomType().getMaxPerson());
        assertEquals(500, rmDto.getRoomType().getPricePerNight());
    }

    @Test
    public void getAllTest(){
        RoomType rt1 = RoomType.builder().
                id(1L).
                type("single").
                maxExtraBed(0).
                maxPerson(1).
                pricePerNight(500).
                build();

        RoomType rt2 = RoomType.builder().
                id(2L).
                type("double").
                maxExtraBed(1).
                maxPerson(2).
                pricePerNight(1000).
                build();

        when(rtRepo.findById(1L)).thenReturn(Optional.of(rt1));

        when(rtRepo.findById(2L)).thenReturn(Optional.of(rt2));
        Room rm1 = Room.builder().
                id(1L).
                roomNumber(101).
                roomType(rt1).
                build();

        Room rm2 = Room.builder().
                id(2L).
                roomNumber(202).
                roomType(rt2).
                build();

        when(rmRepo.findAll()).thenReturn(Arrays.asList(rm1,rm2));

        RoomServiceImpl rmService2 = new RoomServiceImpl(rmRepo,rtRepo,rtService);

        List<RoomDto> rmDtoList = rmService2.getAll();

        Assertions.assertEquals(2,rmDtoList.size());
    }
}
