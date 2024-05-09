package org.example.backend.Service.Impl;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.BlacklistPersonDto;
import org.example.backend.DTO.BlacklistStatusDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BlacklistService {

    public static List<BlacklistPersonDto> getAll() throws IOException {
        URL url = new URL("https://javabl.systementor.se/api/stefan/blacklist");
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());

        BlacklistPersonDto[] theblacklist = jsonMapper.readValue(url, BlacklistPersonDto[].class);

        return List.of(theblacklist);
    }

    public void addNewBlacklistPerson(String name, String email, boolean isOk) throws JsonProcessingException {

        // Create ObjectMapper instance
        ObjectMapper mapper = new ObjectMapper();

        // Create JSON payload using Java objects
        String jsonPayload = mapper.writeValueAsString(Map.of(
                "email", email,
                "name", name,
                "ok", isOk
        ));

        // send http post request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/stefan/blacklist"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        // receive http post response
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }

    public static BlacklistPersonDto getBlacklistPerson(String email) throws IOException {
        return getAll().stream().filter(k -> k.getEmail().equals(email)).findFirst().orElse(null);
    }

    public void updateBlacklistedPerson(String email, String name, boolean isOk) throws JsonProcessingException {

        // Create ObjectMapper instance
        ObjectMapper mapper = new ObjectMapper();

        // Create JSON payload using Java objects
        String jsonPayload = mapper.writeValueAsString(Map.of(
                "name", name,
                "isOk", isOk
        ));

        // send http put request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/stefan/blacklist/"+email))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        // receive http put response
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }

    public boolean isEmailValid(String email) throws Exception {
        URL url = new URL("https://javabl.systementor.se/api/stefan/blacklistcheck/"+email);
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());

        BlacklistStatusDto status = jsonMapper.readValue(url, BlacklistStatusDto.class);

        return status.isOk();
    }
}
