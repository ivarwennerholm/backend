package org.example.backend;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.backend.DTO.BlacklistPersonDto;
import org.example.backend.Service.BlacklistService;
import org.example.backend.Utils.BlacklistCheckEmailURLProvider;
import org.example.backend.Utils.BlacklistURLProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.net.URL;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlacklistServiceTests {

    @Mock
    private BlacklistURLProvider blacklistURLProvider;

    @Mock
    private BlacklistCheckEmailURLProvider blacklistCheckEmailURLProvider;

    @InjectMocks
    private BlacklistService blacklistService;

    public void setUp() throws IOException {
        //Arrange
        InputStream in = new FileInputStream("src/test/resources/BlacklistPerson.json");
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        URL mockURL = mock(URL.class);
        when(blacklistURLProvider.getBlacklistURL()).thenReturn(mockURL);
        when(blacklistURLProvider.getBlacklistURL().openStream()).thenReturn(in);
    }

    @Test
    @Tag("unit")
    public void whenGetAllShouldReturnCorrectly() throws IOException {
        //Arrange
        setUp();

        //Act
        List<BlacklistPersonDto> list = blacklistService.getAll();

        //Assert
        Assertions.assertEquals(3,list.size());

        Assertions.assertTrue(list.get(0).getId()==1L);
        Assertions.assertTrue(list.get(0).getEmail().equals("stefan6@aaa.com"));
        Assertions.assertTrue(list.get(0).getName().equals("Stefan Holmberg"));
        Assertions.assertTrue(list.get(0).getGroup().equals("stefan"));
        Assertions.assertTrue(list.get(0).getCreated().equals("2024-05-06T11:18:24.441769"));
        Assertions.assertTrue(list.get(0).isOk()==false);

        Assertions.assertTrue(list.get(1).getId()==2L);
        Assertions.assertTrue(list.get(1).getEmail().equals("peter@aaa.com"));
        Assertions.assertTrue(list.get(1).getName().equals("Peter Pan"));
        Assertions.assertTrue(list.get(1).getGroup().equals("peter"));
        Assertions.assertTrue(list.get(1).getCreated().equals("2024-05-07T11:04:42.641854"));
        Assertions.assertTrue(list.get(1).isOk()==false);

        Assertions.assertTrue(list.get(2).getId()==3L);
        Assertions.assertTrue(list.get(2).getEmail().equals("eric@aaa.com"));
        Assertions.assertTrue(list.get(2).getName().equals("Eric Ericsson"));
        Assertions.assertTrue(list.get(2).getGroup().equals("eric"));
        Assertions.assertTrue(list.get(2).getCreated().equals("2024-05-08T12:40:52.627185"));
        Assertions.assertTrue(list.get(2).isOk()==true);
    }
    @Test
    @Tag("unit")
    public void whenGetBlacklistPersonShouldMatchCorrectly() throws IOException {
        //Arrange
        setUp();

        //Act
        BlacklistPersonDto bDto = blacklistService.getBlacklistPerson("stefan6@aaa.com");

        //Assert
        Assertions.assertEquals(1L, bDto.getId());
        Assertions.assertEquals("stefan6@aaa.com", bDto.getEmail());
        Assertions.assertEquals("Stefan Holmberg", bDto.getName());
        Assertions.assertEquals("stefan", bDto.getGroup());
        Assertions.assertEquals("2024-05-06T11:18:24.441769", bDto.getCreated());
        Assertions.assertFalse(bDto.isOk());
    }

    // TODO: Fix bug
    /*
    @Test
    void whenCheckEmailValidityShouldReturnTrueFalse() throws Exception {

        //Arrange
        InputStream in = new FileInputStream("src/test/resources/BlacklistStatus.json");
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        URL mockURL = mock(URL.class);
        // when(blacklistURLProvider.getBlacklistURL()).thenReturn(mockURL);
        // when(blacklistURLProvider.getBlacklistURL()).thenReturn(mockURL.toString());

        when(blacklistCheckEmailURLProvider.getBlacklistCheckEmailURL()).thenReturn(mockURL);
        when(mockURL.openStream()).thenReturn(in);

        //Act
        boolean s1 = blacklistService.isEmailValid("stefan6@aaa.com");

        //Assert
        Assertions.assertFalse(s1);
    }
    */

}
