package org.example.backend.Service.Impl;



import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.BlacklistCustomerDto;
import org.example.backend.DTO.BlacklistStatusDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlacklistService {

    public List<BlacklistCustomerDto> getAll() throws IOException {
        URL url = new URL("https://javabl.systementor.se/api/stefan/blacklist");
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());

        BlacklistCustomerDto[] theblacklist = jsonMapper.readValue(url, BlacklistCustomerDto[].class);

        return List.of(theblacklist);
    }

    public void addNewBlacklistCustomer(String name, String email, boolean isOk){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/stefan/blacklist"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"email\":"+email+", \"name\":"+name+", \"isOk\":"+isOk+" }"))
                .build();

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
