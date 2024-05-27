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
import org.example.backend.Repository.RoomEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@RequiredArgsConstructor
public class ReadEventsApp implements CommandLineRunner {

    @Autowired
    IntegrationsProperties integrations;

    private String queueName;

    private final RoomEventRepository eventRepo;


    @Override
    public void run(String... args) throws Exception {
        queueName = integrations.getEvents().getQueue();
        System.out.println("reading start");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(integrations.getEvents().getHost());
        factory.setUsername(integrations.getEvents().getUsername());
        factory.setPassword(integrations.getEvents().getPassword());
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
