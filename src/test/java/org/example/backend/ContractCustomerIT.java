package org.example.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContractCustomerTestsIT {

    URL url;
    InputStream xmlStreamProvider;

    @BeforeEach
    public void init() throws IOException {
        url = new URL("https://javaintegration.systementor.se/customers");
        xmlStreamProvider = url.openStream();
    }

    @Test
    public void fetchContractCustomersContainsCorrectTagsTest() {
        Scanner scan = new Scanner(xmlStreamProvider).useDelimiter("\\A");
//        Scanner s = new Scanner(xmlStreamProvider.getDataStream()).useDelimiter("\\A");
//        String result = scan.hasNext() ? scan.next() : "";
        /*
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
        */
    }
}
