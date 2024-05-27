package org.example.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.*;
import lombok.RequiredArgsConstructor;
import org.example.backend.Configurations.IntegrationsProperties;
import org.example.backend.Events.RoomEvent;
//import org.example.backend.Events.RoomEvent2;
import org.example.backend.Repository.RoomEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.UnsupportedEncodingException;

@ComponentScan
@RequiredArgsConstructor
public class ReadEventsApp implements CommandLineRunner {

    @Autowired
    IntegrationsProperties integrations;

    private String queueName;
    // TODO: Delete
    // private String queueName = "8984dd98-efa9-44fc-8f27-6701b88e9bca";

    private final RoomEventRepository eventRepo;


    @Override
    public void run(String... args) throws Exception {
        queueName = integrations.getEvents().getQueue();
        System.out.println("reading start");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(integrations.getEvents().getHost());
        // TODO: Delete
        // factory.setHost("128.140.81.47");
        factory.setUsername(integrations.getEvents().getUsername());
        // TODO: Delete
        // factory.setUsername("djk47589hjkew789489hjf894");
        factory.setPassword(integrations.getEvents().getPassword());
        // TODO: Delete
        // factory.setPassword("sfdjkl54278frhj7");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");

            getQueueMessageToDatabase(jsonMapper,eventRepo,message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    void getQueueMessageToDatabase(JsonMapper jsonMapper, RoomEventRepository eventRepo, String message) throws JsonProcessingException {
        RoomEvent r = jsonMapper.readValue(message, RoomEvent.class);
        eventRepo.save(r);
        System.out.println(r.toString());
    }
}
