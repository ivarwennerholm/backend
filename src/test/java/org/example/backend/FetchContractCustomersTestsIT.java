package org.example.backend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.backend.Configurations.IntegrationsProperties;
import org.example.backend.Model.ContractCustomer;
import org.example.backend.Repository.ContractCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.backend.BackendApplication.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(args = "fetchcontractcustomers")
public class FetchContractCustomersTestsIT {

    @Autowired
    private IntegrationsProperties integrations;

    @Autowired
    private ContractCustomerRepository repo;

    private FetchContractCustomers sut;
    private URL url;
    private InputStream xmlStream;
    private final Logger logger = LogManager.getLogger(FetchContractCustomersTestsIT.class);

    @BeforeEach
    public void setup() {
        sut = new FetchContractCustomers(repo, integrations);
        repo.deleteAll();
        try {
            url = new URL(integrations.getContractCustomersUrl());
            xmlStream = url.openStream();
        } catch (MalformedURLException e) {
            logger.error(ANSI_RED + "The URL provided is not valid: " + ANSI_RESET + e.getMessage());
        } catch (IOException e) {
            logger.error(ANSI_RED + "Failed to open URL stream: " + ANSI_RESET + e.getMessage());
        }
    }

    @Test
    @DisplayName("XML content should contain correct tags")
    @Tag("integration")
    public void fetchContractCustomersContainsCorrectTagsTest() {
        Scanner scan = new Scanner(xmlStream).useDelimiter("\\A");
        String result = scan.hasNext() ? scan.next() : "";
        assertTrue(result.contains("<allcustomers>"));
        assertTrue(result.contains("</allcustomers>"));
        assertTrue(result.contains("<customers>"));
        assertTrue(result.contains("</customers>"));
        assertTrue(result.contains("<id>"));
        assertTrue(result.contains("</id>"));
        assertTrue(result.contains("<companyName>"));
        assertTrue(result.contains("</companyName>"));
        assertTrue(result.contains("<contactName>"));
        assertTrue(result.contains("</contactName>"));
        assertTrue(result.contains("<contactTitle>"));
        assertTrue(result.contains("</contactTitle>"));
        assertTrue(result.contains("<streetAddress>"));
        assertTrue(result.contains("</streetAddress>"));
        assertTrue(result.contains("<city>"));
        assertTrue(result.contains("</city>"));
        assertTrue(result.contains("<postalCode>"));
        assertTrue(result.contains("</postalCode>"));
        assertTrue(result.contains("<country>"));
        assertTrue(result.contains("</country>"));
        assertTrue(result.contains("<phone>"));
        assertTrue(result.contains("</phone>"));
        assertTrue(result.contains("<fax>"));
        assertTrue(result.contains("</fax>"));
    }

    @Test
    @DisplayName("Mapping of Contract Customers from xml should be done correctly")
    @Tag("integration")
    public void testFetchContractCustomersFromFile() throws Exception {
        String testFilePath = Paths.get("src", "test", "resources", "ContractCustomers.xml").toString();
        File testFile = new File(testFilePath);
        assertThat(testFile).exists();
        sut.setFilePath(testFilePath);

        sut.run();
        List<ContractCustomer> result = repo.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(3);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getCustomerId());
        assertEquals("Persson Kommanditbolag", result.get(0).getCompanyName());
        assertEquals("Maria Åslund", result.get(0).getContactName());
        assertEquals("gardener", result.get(0).getContactTitle());
        assertEquals("Anderssons Gata 259", result.get(0).getStreetAddress());
        assertEquals("Kramland", result.get(0).getCity());
        assertEquals(60843, result.get(0).getPostalCode());
        assertEquals("Sverige", result.get(0).getCountry());
        assertEquals("076-340-7143", result.get(0).getPhone());
        assertEquals("1500-16026", result.get(0).getFax());
    }

}
