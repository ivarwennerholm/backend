
package org.example.backend;

import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Model.ContractCustomer;
import org.example.backend.Repository.ContractCustomerRepository;
import org.example.backend.Service.ContractCustomerService;
import org.example.backend.Service.Impl.ContractCustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContractCustomerServiceTests {

    // private XmlStreamProvider xmlStreamProvider = mock(XmlStreamProvider.class);
    // BookService sut;

    // @Mock
    ContractCustomerRepository contractCustomerRepository;

    // @InjectMocks
    ContractCustomerService contractCustomerService;

    ContractCustomer cc1;
    ContractCustomer cc2;
    ContractCustomer cc3;
    ContractCustomerDto ccdto1;
    ContractCustomerDto ccdto2;
    ContractCustomerDto ccdto3;

    @BeforeEach()
    void init() {
        contractCustomerRepository = mock(ContractCustomerRepository.class);
        contractCustomerService = new ContractCustomerServiceImpl(contractCustomerRepository);
        cc1 = ContractCustomer.builder().
                id(1L).
                customerId(1).
                companyName("Persson Kommanditbolag").
                contactName("Maria Åslund").
                contactTitle("gardener").
                streetAddress("Anderssons Gata 259").
                city("Kramland").
                postalCode(60843).
                country("Sverige").
                phone("076-340-7143").
                fax("1500-16026").
                build();
        cc2 = ContractCustomer.builder().
                id(2L).
                customerId(2).
                companyName("Karlsson-Eriksson").
                contactName("Jörgen Gustafsson").
                contactTitle("philosopher").
                streetAddress("Undre Villagatan 451").
                city("Alingtorp").
                postalCode(28838).
                country("Portugal").
                phone("070-369-5518").
                fax("7805-209976").
                build();
        cc3 = ContractCustomer.builder().
                id(3L).
                customerId(3).
                companyName("Eriksson Group").
                contactName("Anna Karlsson").
                contactTitle("journalist").
                streetAddress("Johanssons Väg 036").
                city("Arlöv").
                postalCode(77616).
                country("Albanien").
                phone("076-904-2433").
                fax("8653-585976").
                build();
        ccdto1 = contractCustomerService.contractCustomerToContractCustomerDto(cc1);
        ccdto2 = contractCustomerService.contractCustomerToContractCustomerDto(cc2);
        ccdto3 = contractCustomerService.contractCustomerToContractCustomerDto(cc3);

        // sut = new BookService(xmlStreamProvider);
        // URL url = new URL("https://axmjqhyyjpat.objectstorage.eu-amsterdam-1.oci.customer-oci.com/n/axmjqhyyjpat/b/aspcodeprod/o/books.xml");
        // return url.openStream();
        // contractCustomerService.getContractCustomers();
    }

    @Test
    void contractCustomerToContractCustomerDtoTest() {
        ContractCustomerDto actual = contractCustomerService.contractCustomerToContractCustomerDto(cc1);
        assertEquals(1L, actual.getId());
        assertEquals(1, actual.getCustomerId());
        assertEquals("Persson Kommanditbolag", actual.getCompanyName());
        assertEquals("Maria Åslund", actual.getContactName());
        assertEquals("gardener", actual.getContactTitle());
        assertEquals("Anderssons Gata 259", actual.getStreetAddress());
        assertEquals("Kramland", actual.getCity());
        assertEquals(60843, actual.getPostalCode());
        assertEquals("Sverige", actual.getCountry());
        assertEquals("076-340-7143", actual.getPhone());
        assertEquals("1500-16026", actual.getFax());
    }

    @Test
    void getByIdTest() {
        when(contractCustomerRepository.findAll()).thenReturn(Arrays.asList(cc1, cc2, cc3));
        Optional<ContractCustomerDto> optional1 = contractCustomerService.getById(1L);
        Optional<ContractCustomerDto> optional2 = contractCustomerService.getById(2L);
        Optional<ContractCustomerDto> optional3 = contractCustomerService.getById(3L);
        ContractCustomerDto actual1 = null;
        ContractCustomerDto actual2 = null;
        ContractCustomerDto actual3 = null;
        if (optional1.isPresent() && optional2.isPresent() && optional3.isPresent()) {
            actual1 = optional1.get();
            actual2 = optional2.get();
            actual3 = optional3.get();
        }
        assertEquals(ccdto1, actual1);
        assertNotEquals(ccdto1, actual2);
        assertEquals(ccdto2, actual2);
        assertNotEquals(ccdto2, actual3);
        assertEquals(ccdto3, actual3);
        assertNotEquals(ccdto3, actual1);
    }

    @Test
    void sortListWithSwedishLettersTest() {
        List<ContractCustomerDto> list = Arrays.asList(ccdto1, ccdto2, ccdto3);
        List<ContractCustomerDto> actualCompanyAsc = contractCustomerService.sortListWithSwedishLetters(list, "asc", "company");
        List<ContractCustomerDto> actualCompanyDesc = contractCustomerService.sortListWithSwedishLetters(list, "desc", "company");
        List<ContractCustomerDto> expectedCompanyAsc = Arrays.asList(ccdto3, ccdto2, ccdto1);
        List<ContractCustomerDto> expectedCompanyDesc = Arrays.asList(ccdto1, ccdto2, ccdto3);
        List<ContractCustomerDto> actualContactAsc = contractCustomerService.sortListWithSwedishLetters(list, "asc", "contact");
        List<ContractCustomerDto> actualContactDesc = contractCustomerService.sortListWithSwedishLetters(list, "desc", "contact");
        List<ContractCustomerDto> expectedContactAsc = Arrays.asList(ccdto3, ccdto2, ccdto1);
        List<ContractCustomerDto> expectedContactDesc = Arrays.asList(ccdto1, ccdto2, ccdto3);
        List<ContractCustomerDto> actualCountryAsc = contractCustomerService.sortListWithSwedishLetters(list, "asc", "country");
        List<ContractCustomerDto> actualCountryDesc = contractCustomerService.sortListWithSwedishLetters(list, "desc", "country");
        List<ContractCustomerDto> expectedCountryAsc = Arrays.asList(ccdto3, ccdto2, ccdto1);
        List<ContractCustomerDto> expectedCountryDesc = Arrays.asList(ccdto1, ccdto2, ccdto3);
        assertEquals(expectedCompanyAsc, actualCompanyAsc);
        assertEquals(expectedCompanyDesc, actualCompanyDesc);
        assertEquals(expectedContactAsc, actualContactAsc);
        assertEquals(expectedContactDesc, actualContactDesc);
        assertEquals(expectedCountryAsc, actualCountryAsc);
        assertEquals(expectedCountryDesc, actualCountryDesc);
    }

    @Test
    void getContractCustomersTest() {
        when(contractCustomerRepository.searchAndSortAsc("Eriksson", "company")).thenReturn(Arrays.asList(cc3, cc2));
        when(contractCustomerRepository.searchAndSortDesc("Eriksson", "company")).thenReturn(Arrays.asList(cc2, cc3));
        when(contractCustomerRepository.searchAndSortAsc("Eriksson", "contact")).thenReturn(Arrays.asList(cc3, cc2));
        when(contractCustomerRepository.searchAndSortDesc("Eriksson", "contact")).thenReturn(Arrays.asList(cc2, cc3));
        when(contractCustomerRepository.searchAndSortAsc("Eriksson", "country")).thenReturn(Arrays.asList(cc3, cc2));
        when(contractCustomerRepository.searchAndSortDesc("Eriksson", "country")).thenReturn(Arrays.asList(cc2, cc3));
        when(contractCustomerRepository.sortAsc("company")).thenReturn(Arrays.asList(cc3, cc2, cc1));
        when(contractCustomerRepository.sortDesc("company")).thenReturn(Arrays.asList(cc1, cc2, cc3));
        when(contractCustomerRepository.sortAsc("contact")).thenReturn(Arrays.asList(cc3, cc2, cc1));
        when(contractCustomerRepository.sortDesc("contact")).thenReturn(Arrays.asList(cc1, cc2, cc3));
        when(contractCustomerRepository.sortAsc("country")).thenReturn(Arrays.asList(cc3, cc2, cc1));
        when(contractCustomerRepository.sortDesc("country")).thenReturn(Arrays.asList(cc1, cc2, cc3));

        List<ContractCustomer> actualSearchCompanyAsc = contractCustomerRepository.searchAndSortAsc("Eriksson", "company");
        List<ContractCustomer> actualSearchCompanyDesc = contractCustomerRepository.searchAndSortDesc("Eriksson", "company");
        List<ContractCustomer> actualSearchContactAsc = contractCustomerRepository.searchAndSortAsc("Eriksson", "contact");
        List<ContractCustomer> actualSearchContactDesc = contractCustomerRepository.searchAndSortDesc("Eriksson", "contact");
        List<ContractCustomer> actualSearchCountryAsc = contractCustomerRepository.searchAndSortAsc("Eriksson", "country");
        List<ContractCustomer> actualSearchCountryDesc = contractCustomerRepository.searchAndSortDesc("Eriksson", "country");
        List<ContractCustomer> actualCompanyAsc = contractCustomerRepository.sortAsc("company");
        List<ContractCustomer> actualCompanyDesc = contractCustomerRepository.sortDesc("company");
        List<ContractCustomer> actualContactAsc = contractCustomerRepository.sortAsc("contact");
        List<ContractCustomer> actualContactDesc = contractCustomerRepository.sortDesc("contact");
        List<ContractCustomer> actualCountryAsc = contractCustomerRepository.sortAsc("country");
        List<ContractCustomer> actualCountryDesc = contractCustomerRepository.sortDesc("country");
        List<ContractCustomer> expectedSearchCompanyAsc = Arrays.asList(cc3, cc2);
        List<ContractCustomer> expectedSearchCompanyDesc = Arrays.asList(cc2, cc3);
        List<ContractCustomer> expectedSearchContactAsc = Arrays.asList(cc3, cc2);
        List<ContractCustomer> expectedSearchCoctantDesc = Arrays.asList(cc2, cc3);
        List<ContractCustomer> expectedSearchCountryAsc = Arrays.asList(cc3, cc2);
        List<ContractCustomer> expectedSearchCountryDesc = Arrays.asList(cc2, cc3);
        List<ContractCustomer> expectedCompanyAsc = Arrays.asList(cc3, cc2, cc1);
        List<ContractCustomer> expectedCompanyDesc = Arrays.asList(cc1, cc2, cc3);
        List<ContractCustomer> expectedContactAsc = Arrays.asList(cc3, cc2, cc1);
        List<ContractCustomer> expectedContactDesc = Arrays.asList(cc1, cc2, cc3);
        List<ContractCustomer> expectedCountryAsc = Arrays.asList(cc3, cc2, cc1);
        List<ContractCustomer> expectedCountryDesc = Arrays.asList(cc1, cc2, cc3);

        assertEquals(expectedSearchCompanyAsc, actualSearchCompanyAsc);
        assertEquals(expectedSearchCompanyDesc, actualSearchCompanyDesc);
        assertEquals(expectedSearchContactAsc, actualSearchContactAsc);
        assertEquals(expectedSearchCoctantDesc, actualSearchContactDesc);
        assertEquals(expectedSearchCountryAsc, actualSearchCountryAsc);
        assertEquals(expectedSearchCountryDesc, actualSearchCountryDesc);
        assertEquals(expectedCompanyAsc, actualCompanyAsc);
        assertEquals(expectedCompanyDesc, actualCompanyDesc);
        assertEquals(expectedContactAsc, actualContactAsc);
        assertEquals(expectedContactDesc, actualContactDesc);
        assertEquals(expectedCountryAsc, actualCountryAsc);
        assertEquals(expectedCountryDesc, actualCountryDesc);
    }

    @Test
    void getContractCustomersMapsCorrectlyTest() throws IOException {
        // Arrange
        // when(xmlStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("books.xml"));

        // Act
        // List<book> result = sut.GetBooks();

        // Assert
        // assertEquals(3, result.size());
        // assertEquals(1, result.get(0).id);
        // assertEquals("Persson Kommanditbolag", result.get(0).companyName);
        // assertEquals("Maria Åslund", result.get(0).contactName);
        // assertEquals("gardener", result.get(0).contactTitle);
        // assertEquals("Anderssons Gata 259", result.get(0).streetAddress);
        // assertEquals("Kramland", result.get(0).city);
        // assertEquals(60843, result.get(0).postalCode);
        // assertEquals("Sverige", result.get(0).country);
        // assertEquals("076-340-7143", result.get(0).phone);
        // assertEquals("1500-16026", result.get(0).fax);
    }

}
