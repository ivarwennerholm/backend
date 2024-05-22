package org.example.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.backend.Utils.BlacklistCheckEmailURLProvider;
import org.example.backend.Utils.BlacklistURLProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BlacklistCheckEmailURLProviderTestsIT {
    @Autowired
    private BlacklistURLProvider blacklistURLProvider;

    @Autowired
    private BlacklistCheckEmailURLProvider blacklistCheckEmailURLProvider;
    static URL url;
//    @Test
//    void whenConnectMockUrlIfAvailableOrNot() throws IOException {
//        HttpURLConnection mockHttpURLConnection = mock(HttpURLConnection.class);
//        when(mockHttpURLConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND);
//
//        URL mockURL = mock(URL.class);
//        when(mockURL.openConnection()).thenReturn(mockHttpURLConnection);
//        blacklistCheckEmailURLProvider.setUrl(mockURL);
//
//        Assertions.assertFalse(blacklistCheckEmailURLProvider.isCheckEmailURLAvailable());
//    }
    @BeforeEach
    void setUp(){
        blacklistCheckEmailURLProvider = new BlacklistCheckEmailURLProvider("stefan6@aaa.com");
        url = blacklistCheckEmailURLProvider.getBlacklistCheckEmailURL();
    }
    @Test
    void whenConnectCheckEmailUrlIfSuccessOrNot() throws Exception {
        Assertions.assertTrue(blacklistCheckEmailURLProvider.isCheckEmailURLAvailable());
    }

    @Test
    void fetchBlacklistCheckEmailShouldContainCorrectTags() throws IOException {
        //Arrange
        Scanner s = new Scanner(url.openStream()).useDelimiter("\\A");
        ObjectMapper mapper = new ObjectMapper();

        //Act
        String result = s.hasNext() ? s.next() : "";

        //to check that number of fields of a json object has only 2 fields
        JsonNode arrayNode = mapper.readTree(result);
        int fieldCount = arrayNode.size();

        //Assert
        Assertions.assertEquals(2,fieldCount);
        assertTrue(  result.contains("statusText") );
        assertTrue(  result.contains("ok") );
    }

}
