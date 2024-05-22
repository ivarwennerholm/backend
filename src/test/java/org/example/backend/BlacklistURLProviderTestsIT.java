package org.example.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.Utils.BlacklistURLProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BlacklistURLProviderTestsIT {

    @Autowired
    private BlacklistURLProvider blacklistURLProvider;
    static URL url;
//    @Test
//    void whenConnectMockUrlIfAvailableOrNot() throws IOException {
//        HttpURLConnection mockHttpURLConnection = mock(HttpURLConnection.class);
//        when(mockHttpURLConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND);
//
//        URL mockURL = mock(URL.class);
//        when(mockURL.openConnection()).thenReturn(mockHttpURLConnection);
//        blacklistURLProvider.setUrl(mockURL);
//
//        Assertions.assertFalse(blacklistURLProvider.isURLAvailable());
//    }
    @BeforeEach
    void setUp(){
        url = blacklistURLProvider.getBlacklistURL();
    }
    @Test
    void whenConnectUrlIfSuccessOrNot() throws Exception {
        Assertions.assertTrue(blacklistURLProvider.isURLAvailable());
    }

    @Test
    void fetchBlacklistPersonShouldContainCorrectTags() throws IOException {
        //Arrange
        Scanner s = new Scanner(url.openStream()).useDelimiter("\\A");
        ObjectMapper mapper = new ObjectMapper();

        //Act
        String result = s.hasNext() ? s.next() : "";

        //to check that number of fields of a json object has only 6 fields
        JsonNode arrayNode = mapper.readTree(result);
        int fieldCount = arrayNode.get(0).size();

        //Assert
        Assertions.assertEquals(6,fieldCount);
        assertTrue(  result.contains("id") );
        assertTrue(  result.contains("email") );
        assertTrue(  result.contains("name") );
        assertTrue(  result.contains("group") );
        assertTrue(  result.contains("created") );
        assertTrue(  result.contains("ok") );
    }
}