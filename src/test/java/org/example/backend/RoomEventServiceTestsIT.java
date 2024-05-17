package org.example.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.*;
import org.example.backend.Events.RoomCleanDone;
import org.example.backend.Events.RoomEvent;
import org.example.backend.Repository.RoomEventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Scanner;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RoomEventServiceTestsIT {

    // test connection to rabbitMQ ***
    // test receiving message in json format from rabbitMQ ***
    // test json format file has all correct fields ***
    /*
     ***cannot dp integration test because we cannot control if rabbitMQ has awaiting messages in queue.
     To know if rabbitMQ is running, there are other management systems doing this work.
     */


    // data is mapped correctly to entity object -> UNIT TEST - DONE
    // connection to H2 database is working
    // data is successfully saved to database
    // correct json type is mapped to correct java class

    @Mock
    private RoomEventRepository eventRepo;

    @InjectMocks
    private ReadEventsApp eventApp;

    @Test
    void whenGetQueueMessageToDatabaseShouldMapCorrectly() throws IOException {
        //Arrange
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/RoomEvents.json"));
        String message = reader.readLine();
        System.out.println(message);
//        RoomEvent savedEvent = new RoomCleanDone(1L, 101, LocalDateTime.parse("2024-05-16T18:32:27.540931906"), "Young Cartwright");
//        when(eventRepo.save(any(RoomEvent.class))).thenReturn(savedEvent);

        //Act
        eventApp.getQueueMessageToDatabase(jsonMapper, eventRepo, message);

        //Assert
        verify(eventRepo,times(1)).save(argThat(RoomEvent -> RoomEvent.getId()==1L));
        verify(eventRepo,times(1)).save(argThat(RoomEvent -> RoomEvent.getRoomno()==101));
        verify(eventRepo,times(1)).save(argThat(RoomEvent -> String.valueOf(RoomEvent.getTimestamp()).equals("2024-05-16T18:32:27.540931906")));
        verify(eventRepo,times(1)).save(argThat(RoomEvent -> RoomEvent.getCleaner().equals("Young Cartwright")));
    }

}
