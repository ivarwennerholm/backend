package org.example.backend;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.example.backend.Configurations.IntegrationsProperties;
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

@ComponentScan
@RequiredArgsConstructor
public class FetchShippingContractors implements CommandLineRunner{

    @Autowired
    private IntegrationsProperties integrations;

    @Autowired
    private final ShipperRepository shipperRepo;

    //@Autowired
    private ShipperJsonProvider shipperJsonProvider;


    @Override
    public void run(String... args) throws Exception {
        shipperJsonProvider = new ShipperJsonProvider(integrations);
        HttpURLConnection httpConn = (HttpURLConnection)shipperJsonProvider.getShipperUrl().openConnection();
        httpConn.setInstanceFollowRedirects(false);
        httpConn.setRequestMethod("HEAD");
            httpConn.connect();
            shipperRepo.deleteAll();
            JsonMapper jsonMapper = new JsonMapper();
            jsonMapper.registerModule(new JavaTimeModule());

            getShippersToDatabase(shipperJsonProvider.getShipperUrl().openStream(),jsonMapper, shipperRepo);
    }

    void getShippersToDatabase(InputStream input, JsonMapper jsonMapper, ShipperRepository shipperRepo) throws IOException {
        ShipperDto[]theShippers = jsonMapper.readValue(input, ShipperDto[].class);

            for (ShipperDto s : theShippers){
                shipperRepo.save(new Shipper(s));
            }
    }
}
