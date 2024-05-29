package org.example.backend;

import org.example.backend.DTO.RoomTypeDto;
import org.example.backend.Model.RoomType;
import org.example.backend.Repository.RoomTypeRepository;
import org.example.backend.Service.Impl.RoomTypeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomTypeServiceTests {

    @Mock
    private RoomTypeRepository rtRepo;

    @InjectMocks
    private RoomTypeServiceImpl rtService;

    @Test
    public void roomTypeToRoomTypeDtoTest(){
        RoomType rt = RoomType.builder().
                id(1L).
                type("Single").
                maxExtraBed(0).
                maxPerson(1).
                pricePerNight(500).
                build();
        RoomTypeDto actual = rtService.roomTypeToRoomTypeDto(rt);
        Assertions.assertTrue(actual.getId()==1L);
        Assertions.assertTrue(actual.getType().equals("Single"));
        Assertions.assertTrue(actual.getMaxExtraBed()==0);
        Assertions.assertTrue(actual.getMaxPerson()==1);
        Assertions.assertTrue(actual.getPricePerNight()==500);
    }

    @Test
    public void roomTypeDtoToRoomTypeTest(){
        RoomTypeDto rtDto = RoomTypeDto.builder().
                id(1L).
                type("Single").
                maxExtraBed(0).
                maxPerson(1).
                pricePerNight(500).
                build();
        RoomType actual = rtService.roomTypeDtoToRoomType(rtDto);
        Assertions.assertTrue(actual.getId()==1L);
        Assertions.assertTrue(actual.getType().equals("Single"));
        Assertions.assertTrue(actual.getMaxExtraBed()==0);
        Assertions.assertTrue(actual.getMaxPerson()==1);
        Assertions.assertTrue(actual.getPricePerNight()==500);
    }

    @Test
    public void getAllTest(){
        RoomType rt1 = RoomType.builder().
                id(1L).
                type("Single").
                maxExtraBed(0).
                maxPerson(1).
                pricePerNight(500).
                build();
        RoomType rt2 = RoomType.builder().
                id(2L).
                type("Double").
                maxExtraBed(1).
                maxPerson(2).
                pricePerNight(1000).
                build();
        when(rtRepo.findAll()).thenReturn(Arrays.asList(rt1,rt2));
        List<RoomTypeDto> rtList = rtService.getAll();
        Assertions.assertEquals(rtList.size(),2);
        Assertions.assertTrue(rtList.get(0).getId()==1L);
        Assertions.assertTrue(rtList.get(0).getType().equals("Single"));
        Assertions.assertTrue(rtList.get(0).getMaxExtraBed()==0);
        Assertions.assertTrue(rtList.get(0).getMaxPerson()==1);
        Assertions.assertTrue(rtList.get(0).getPricePerNight()==500);
        Assertions.assertTrue(rtList.get(1).getId()==2L);
        Assertions.assertTrue(rtList.get(1).getType().equals("Double"));
        Assertions.assertTrue(rtList.get(1).getMaxExtraBed()==1);
        Assertions.assertTrue(rtList.get(1).getMaxPerson()==2);
        Assertions.assertTrue(rtList.get(1).getPricePerNight()==1000);
    }

    @Test
    public void addRoomTypeTest(){
        RoomTypeDto rtDto = RoomTypeDto.builder().
                id(1L).
                type("Single").
                maxExtraBed(0).
                maxPerson(1).
                pricePerNight(500).
                build();
        Assertions.assertTrue(rtService.addRoomType(rtDto).equals("new room type is added"));
    }

}
