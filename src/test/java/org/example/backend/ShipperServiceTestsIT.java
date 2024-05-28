package org.example.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.backend.Model.Shipper;
import org.example.backend.Repository.ShipperRepository;
import org.example.backend.Utils.ShipperJsonProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ShipperServiceTestsIT {

    @Autowired
    private ShipperRepository repo;

    @Autowired
    private ShipperJsonProvider shipperJsonProvider;

    private FetchShippingContractors fetchShippingContractors;

    static URL url;

    @BeforeEach
    void setUp(){
        fetchShippingContractors = new FetchShippingContractors(repo);
        url = shipperJsonProvider.getShipperUrl();
    }

    @Test
    void whenConnectMockUrlIfAvailableOrNot() throws Exception {
        Assertions.assertTrue(shipperJsonProvider.isURLAvailable());
    }

    @Test
    void fetchShippingContractorsShouldContainCorrectTags() throws IOException {
        //Arrange
        Scanner s = new Scanner(url.openStream()).useDelimiter("\\A");
        ObjectMapper mapper = new ObjectMapper();

        //Act
        String result = s.hasNext() ? s.next() : "";

        //to check that number of fields of a json object has only 11 fields
        JsonNode arrayNode = mapper.readTree(result);
        int fieldCount = arrayNode.get(0).size();

        //Assert
        Assertions.assertEquals(11,fieldCount);
        assertTrue(  result.contains("id") );
        assertTrue(  result.contains("email") );
        assertTrue(  result.contains("companyName") );
        assertTrue(  result.contains("contactName") );
        assertTrue(  result.contains("contactTitle") );
        assertTrue(  result.contains("streetAddress") );
        assertTrue(  result.contains("city") );
        assertTrue(  result.contains("postalCode") );
        assertTrue(  result.contains("country") );
        assertTrue(  result.contains("phone") );
        assertTrue(  result.contains("fax") );
    }
    @Test
    public void getShippersToDatabaseShouldMapCorrectly() throws IOException {
        //Arrange
        InputStream in = new FileInputStream(new File("src/test/resources/ShippingContractors.json"));
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());

        //Act
        fetchShippingContractors.getShippersToDatabase(in, jsonMapper, repo);
        List<Shipper> list = repo.findAll();

        //Assert
        Assertions.assertEquals(3,list.size());

        Assertions.assertTrue(list.get(0).getId()==1);
        Assertions.assertTrue(list.get(0).getEmail().equals("birgitta.olsson@hotmail.com"));

        Assertions.assertTrue(list.get(1).getId()==2);
        Assertions.assertTrue(list.get(1).getEmail().equals("lars.aslund@hotmail.com"));

        Assertions.assertTrue(list.get(2).getId()==3);
        Assertions.assertTrue(list.get(2).getEmail().equals("karin.ostlund@yahoo.com"));
    }
}
