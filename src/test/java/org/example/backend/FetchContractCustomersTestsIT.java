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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(args = "fetchcontractcustomers")
public class FetchContractCustomersTestsIT {

    @Autowired
    IntegrationsProperties integrations;

    @Autowired
    private ContractCustomerRepository repo;

    private FetchContractCustomers sut;

    URL url;
    InputStream xmlStream;
    private static final Logger logger = LogManager.getLogger(FetchContractCustomersTestsIT.class);

    // ANSI colors for readability
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    @BeforeEach
    public void setup() throws IOException {
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
        // ARRANGE
        String testFilePath = Paths.get("src", "test", "resources", "contractcustomers.xml").toString();
        File testFile = new File(testFilePath);
        assertThat(testFile).exists();
        sut.setFilePath(testFilePath);

        // ACT
        sut.run();
        List<ContractCustomer> result = repo.findAll();

        // ASSERT
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(3);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).customerId);
        assertEquals("Persson Kommanditbolag", result.get(0).companyName);
        assertEquals("Maria Åslund", result.get(0).contactName);
        assertEquals("gardener", result.get(0).contactTitle);
        assertEquals("Anderssons Gata 259", result.get(0).streetAddress);
        assertEquals("Kramland", result.get(0).city);
        assertEquals(60843, result.get(0).postalCode);
        assertEquals("Sverige", result.get(0).country);
        assertEquals("076-340-7143", result.get(0).phone);
        assertEquals("1500-16026", result.get(0).fax);
    }

}
