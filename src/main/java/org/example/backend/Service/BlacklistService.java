package org.example.backend.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.BlacklistPersonDto;
import org.example.backend.DTO.BlacklistStatusDto;
import org.example.backend.Utils.BlacklistCheckEmailURLProvider;
import org.example.backend.Utils.BlacklistURLProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BlacklistService {

    @Autowired
    private final BlacklistURLProvider blacklistURLProvider;

    @Autowired
    private final BlacklistCheckEmailURLProvider blacklistCheckEmailURLProvider;

    public List<BlacklistPersonDto> getAll() throws IOException {
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        BlacklistPersonDto[] theblacklist = jsonMapper.readValue(blacklistURLProvider.getBlacklistURL(), BlacklistPersonDto[].class);
        List<BlacklistPersonDto> temp = new ArrayList<>();
        for (BlacklistPersonDto b: theblacklist) {
            temp.add(b);
        }
        return temp;
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
                .uri(URI.create(blacklistURLProvider.getBlacklistUrl_String()))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        // receive http post response
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }

    public BlacklistPersonDto getBlacklistPerson(String email) throws IOException {
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
                .uri(URI.create(blacklistURLProvider.getBlacklistUrl_String()+"/"+email))
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
        blacklistCheckEmailURLProvider.setEmail(email);
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        BlacklistStatusDto status = jsonMapper.readValue(blacklistCheckEmailURLProvider.getBlacklistCheckEmailURL(), BlacklistStatusDto.class);
        return status.isOk();
    }

}
