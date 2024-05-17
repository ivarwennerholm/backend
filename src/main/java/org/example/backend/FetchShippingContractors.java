package org.example.backend;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.ShipperDto;
import org.example.backend.Model.Shipper;
import org.example.backend.Repository.ShipperRepository;
import org.example.backend.Utils.ShipperJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

@ComponentScan
@RequiredArgsConstructor
public class FetchShippingContractors implements CommandLineRunner{

    @Autowired
    private final ShipperRepository shipperRepo;

    @Autowired
    private ShipperJsonProvider shipperJsonProvider;

    @Override
    public void run(String... args) throws Exception {

        HttpURLConnection httpConn = (HttpURLConnection)shipperJsonProvider.getShipperUrl().openConnection();
        httpConn.setInstanceFollowRedirects(false);
        httpConn.setRequestMethod("HEAD");
        try {
            httpConn.connect();
            System.out.println("\nJson server connection ok\n");
            shipperRepo.deleteAll();
            JsonMapper jsonMapper = new JsonMapper();
            jsonMapper.registerModule(new JavaTimeModule());

            getShippersToDatabase(shipperJsonProvider.getShipperUrl().openStream(),jsonMapper, shipperRepo);

//            ShipperDto[]theShippers = jsonMapper.readValue(shipperJsonProvider.getShipperUrl(), ShipperDto[].class);
//
//            for (ShipperDto s : theShippers){
//                shipperRepo.save(new Shipper(s));
//            }
        }catch (UnknownHostException | java.net.ConnectException ex){
            System.out.println("\nJson server down\n");
        }
    }

    void getShippersToDatabase(InputStream input, JsonMapper jsonMapper, ShipperRepository shipperRepo) throws IOException {
        ShipperDto[]theShippers = jsonMapper.readValue(input, ShipperDto[].class);

            for (ShipperDto s : theShippers){
                shipperRepo.save(new Shipper(s));
            }
    }
}
