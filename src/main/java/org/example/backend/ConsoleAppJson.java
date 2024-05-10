package org.example.backend;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.ShipperDto;
import org.example.backend.Model.Shipper;
import org.example.backend.Repository.ShipperRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

@ComponentScan
@RequiredArgsConstructor
public class ConsoleAppJson implements CommandLineRunner{
    private final ShipperRepository shipperRepo;

    @Override
    public void run(String... args) throws Exception {

        URL url = new URL("https://javaintegration.systementor.se/shippers");
        HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
        httpConn.setInstanceFollowRedirects(false);
        httpConn.setRequestMethod("HEAD");
        try {
            httpConn.connect();
            System.out.println("\nJson server connection ok\n");
            shipperRepo.deleteAll();
            JsonMapper jsonMapper = new JsonMapper();
            jsonMapper.registerModule(new JavaTimeModule());

            ShipperDto[]theShippers = jsonMapper.readValue(url, ShipperDto[].class);

            for (ShipperDto s : theShippers){
                shipperRepo.save(new Shipper(s));
            }
        }catch (UnknownHostException | java.net.ConnectException ex){
            System.out.println("\nJson server down\n");
        }
    }
}
