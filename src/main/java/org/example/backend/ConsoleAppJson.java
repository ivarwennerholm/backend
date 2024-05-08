package org.example.backend;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.ShipperDto;
import org.example.backend.Model.Shipper;
import org.example.backend.Repository.ShipperRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.net.URL;

@ComponentScan
@RequiredArgsConstructor
public class ConsoleAppJson implements CommandLineRunner{
    private final ShipperRepository shipperRepository;

//    @Autowired
//    public ConsoleAppJson(ShipperRepository shipperRepository) {
//        this.shipperRepository = shipperRepository;
//    }
    @Override
    public void run(String... args) throws Exception {

        URL url = new URL("https://javaintegration.systementor.se/shippers");
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());

        ShipperDto[]theShippers = jsonMapper.readValue(url, ShipperDto[].class);

        System.out.println("json hello");
        for (ShipperDto s : theShippers){
            shipperRepository.save(new Shipper(s));
        }
    }
}
