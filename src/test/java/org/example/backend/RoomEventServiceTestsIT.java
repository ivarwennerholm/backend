package org.example.backend;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.backend.Events.RoomEvent;
import org.example.backend.Repository.RoomEventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
public class RoomEventServiceTestsIT {

    @Autowired
    private RoomEventRepository eventRepo;

    private final ReadEventsApp eventApp = new ReadEventsApp(eventRepo);
    private JsonMapper jsonMapper;

    @BeforeEach
    public void setUp(){
        jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void whenGetQueueMessageToDatabaseShouldMapCorrectly() throws IOException {

        // Arrange
        InputStream in = new FileInputStream("src/test/resources/RoomEvents.json");
        Scanner s = new Scanner(in).useDelimiter("\\A");

        // Act
        String message = s.hasNext() ? s.next() : "";
        System.out.println(message);
        eventApp.getQueueMessageToDatabase(jsonMapper, eventRepo, message);

        // Assert
        List<RoomEvent> list = eventRepo.findAll();
        Assertions.assertEquals(1,list.size());
        Assertions.assertTrue(list.get(0).getId()==1);
        Assertions.assertTrue(list.get(0).getRoomno()==101);
        Assertions.assertTrue(list.get(0).getTimestamp().isEqual(LocalDateTime.parse("2024-05-16T18:32:27.540932")));
        Assertions.assertTrue(list.get(0).getCleaner().equals("Young Cartwright"));
    }

}
