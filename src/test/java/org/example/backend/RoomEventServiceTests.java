package org.example.backend;

import org.example.backend.Events.*;
import org.example.backend.Repository.RoomEventRepository;
import org.example.backend.Service.Impl.RoomEventServiceImpl;
import org.example.backend.Service.RoomEventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomEventServiceTests {
    @Mock
    private RoomEventRepository rmEventRepo;

    @InjectMocks
    private RoomEventServiceImpl sut;

//    public List<RoomEvent> getRoomEventsByRoomNo(int roomNo) {
//        return rmEventRepo.getRoomEventByRoomNo(roomNo);
//    }
    @Test
    void whenGetRoomEventsByRoomNoShouldMapCorrectly(){
        //Arrange
        RoomEvent ev1 = new RoomOpened(1L,101,LocalDateTime.parse("2024-05-16T12:57:29.101686955"));
        RoomEvent ev2 = new RoomClosed(2L,101,LocalDateTime.parse("2024-05-16T05:28:27.579059895"));
        RoomEvent ev3 = new RoomCleanStarted(3L,101,LocalDateTime.parse("2024-05-16T01:20:27.497480694"),"Cassandra Gutmann");
        RoomEvent ev4 = new RoomCleanDone(4L,101,LocalDateTime.parse("2024-05-16T18:32:27.540931906"),"Young Cartwright");
        List<RoomEvent> list = Arrays.asList(ev1,ev2,ev3,ev4);
        when(rmEventRepo.getRoomEventByRoomNo(101)).thenReturn(list);

        //Act
        List<RoomEvent> actual = sut.getRoomEventsByRoomNo(101);

        //Assert
        Assertions.assertEquals(4,actual.size());

        Assertions.assertTrue(actual.get(0).getId()==1L);
        Assertions.assertTrue(actual.get(0).getRoomno()==101);
        Assertions.assertTrue(String.valueOf(actual.get(0).getTimestamp()).equals("2024-05-16T12:57:29.101686955"));
        Assertions.assertTrue(actual.get(0).getCleaner()==null);

        Assertions.assertTrue(actual.get(1).getId()==2L);
        Assertions.assertTrue(actual.get(1).getRoomno()==101);
        Assertions.assertTrue(String.valueOf(actual.get(1).getTimestamp()).equals("2024-05-16T05:28:27.579059895"));
        Assertions.assertTrue(actual.get(1).getCleaner()==null);

        Assertions.assertTrue(actual.get(2).getId()==3L);
        Assertions.assertTrue(actual.get(2).getRoomno()==101);
        Assertions.assertTrue(String.valueOf(actual.get(2).getTimestamp()).equals("2024-05-16T01:20:27.497480694"));
        Assertions.assertTrue(actual.get(2).getCleaner().equals("Cassandra Gutmann"));

        Assertions.assertTrue(actual.get(3).getId()==4L);
        Assertions.assertTrue(actual.get(3).getRoomno()==101);
        Assertions.assertTrue(String.valueOf(actual.get(3).getTimestamp()).equals("2024-05-16T18:32:27.540931906"));
        Assertions.assertTrue(actual.get(3).getCleaner().equals("Young Cartwright"));
    }
}
