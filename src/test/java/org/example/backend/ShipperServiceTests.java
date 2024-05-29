package org.example.backend;

import org.example.backend.Model.Shipper;
import org.example.backend.Repository.ShipperRepository;
import org.example.backend.Service.ShipperService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShipperServiceTests {

    @Mock
    private ShipperRepository shipRepo;

    @InjectMocks
    private ShipperService shipService;

    @BeforeEach
    public void setUp(){
        Shipper s1 = new Shipper(1L, 1L, "birgitta.olsson@hotmail.com", "Svensson-Karlsson", "Erik Östlund", "painter",
                "Järnvägsallén 955", "Gävhult", "07427", "Sverige", "070-569-3764","2634-25376");
        Shipper s2 = new Shipper(2L, 2L, "lars.aslund@hotmail.com", "Änglund, Svensson AB", "Nils Östlund", "newsreader",
                "Åsas Gata 15", "Kristby", "84699", "Sverige", "073-820-0856","8238-27759");
        Shipper s3 = new Shipper(3L, 3L, "karin.ostlund@yahoo.com", "Karlsson Gruppen", "Anders Johansson", "dentist",
                "Östlunds Gata 0", "Faltuna", "85980", "Sverige", "076-869-7192","7430-900849");

        when(shipRepo.findAll()).thenReturn(Arrays.asList(s1,s2,s3));
    }

    @Test
    @Tag("unit")
    public void whenGetAllShouldReturnCorrectly() {

        // Act
        List<Shipper> list = shipService.getAllShippers();

        // Assert
        verify(shipRepo,times(1)).findAll();
        Assertions.assertEquals(3,list.size());
    }

}
