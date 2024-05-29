package org.example.backend;

import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Model.ContractCustomer;
import org.example.backend.Repository.ContractCustomerRepository;
import org.example.backend.Service.ContractCustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContractCustomerServiceTests {

    private ContractCustomerRepository repo;
    private ContractCustomerService sut;

    private ContractCustomer cc1;
    private ContractCustomer cc2;
    private ContractCustomer cc3;
    private ContractCustomerDto ccdto1;
    private ContractCustomerDto ccdto2;
    private ContractCustomerDto ccdto3;

    @BeforeEach()
    public void init() {
        repo = mock(ContractCustomerRepository.class);
        sut = new ContractCustomerService(repo);
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
        ccdto1 = sut.contractCustomerToContractCustomerDto(cc1);
        ccdto2 = sut.contractCustomerToContractCustomerDto(cc2);
        ccdto3 = sut.contractCustomerToContractCustomerDto(cc3);
    }

    @Test
    @DisplayName("Conversion between ContractCustomer and ContractCustomerDto should be done correctly")
    @Tag("unit")
    public void contractCustomerToContractCustomerDtoTest() {
        ContractCustomerDto actual = sut.contractCustomerToContractCustomerDto(cc1);
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
    @DisplayName("getById() in service should return correct object")
    @Tag("unit")
    void getByIdTest() {
        when(repo.findAll()).thenReturn(Arrays.asList(cc1, cc2, cc3));
        Optional<ContractCustomerDto> optional1 = sut.getById(1L);
        Optional<ContractCustomerDto> optional2 = sut.getById(2L);
        Optional<ContractCustomerDto> optional3 = sut.getById(3L);
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
    @DisplayName("Sorting lists of ContractCustomers should be done correctly with Swedish letters")
    @Tag("unit")
    void sortListWithSwedishLettersTest() {
        List<ContractCustomerDto> list = Arrays.asList(ccdto1, ccdto2, ccdto3);
        List<ContractCustomerDto> actualCompanyAsc = sut.sortListWithSwedishLetters(list, "asc", "company");
        List<ContractCustomerDto> actualCompanyDesc = sut.sortListWithSwedishLetters(list, "desc", "company");
        List<ContractCustomerDto> expectedCompanyAsc = Arrays.asList(ccdto3, ccdto2, ccdto1);
        List<ContractCustomerDto> expectedCompanyDesc = Arrays.asList(ccdto1, ccdto2, ccdto3);
        List<ContractCustomerDto> actualContactAsc = sut.sortListWithSwedishLetters(list, "asc", "contact");
        List<ContractCustomerDto> actualContactDesc = sut.sortListWithSwedishLetters(list, "desc", "contact");
        List<ContractCustomerDto> expectedContactAsc = Arrays.asList(ccdto3, ccdto2, ccdto1);
        List<ContractCustomerDto> expectedContactDesc = Arrays.asList(ccdto1, ccdto2, ccdto3);
        List<ContractCustomerDto> actualCountryAsc = sut.sortListWithSwedishLetters(list, "asc", "country");
        List<ContractCustomerDto> actualCountryDesc = sut.sortListWithSwedishLetters(list, "desc", "country");
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
    @DisplayName("Getting a list of ContractCustomers from repo should work correctly with searching and sorting")
    @Tag("unit")
    void getContractCustomersTest() {
        when(repo.searchAndSortAsc("Eriksson", "company")).thenReturn(Arrays.asList(cc3, cc2));
        when(repo.searchAndSortDesc("Eriksson", "company")).thenReturn(Arrays.asList(cc2, cc3));
        when(repo.searchAndSortAsc("Eriksson", "contact")).thenReturn(Arrays.asList(cc3, cc2));
        when(repo.searchAndSortDesc("Eriksson", "contact")).thenReturn(Arrays.asList(cc2, cc3));
        when(repo.searchAndSortAsc("Eriksson", "country")).thenReturn(Arrays.asList(cc3, cc2));
        when(repo.searchAndSortDesc("Eriksson", "country")).thenReturn(Arrays.asList(cc2, cc3));
        when(repo.sortAsc("company")).thenReturn(Arrays.asList(cc3, cc2, cc1));
        when(repo.sortDesc("company")).thenReturn(Arrays.asList(cc1, cc2, cc3));
        when(repo.sortAsc("contact")).thenReturn(Arrays.asList(cc3, cc2, cc1));
        when(repo.sortDesc("contact")).thenReturn(Arrays.asList(cc1, cc2, cc3));
        when(repo.sortAsc("country")).thenReturn(Arrays.asList(cc3, cc2, cc1));
        when(repo.sortDesc("country")).thenReturn(Arrays.asList(cc1, cc2, cc3));

        List<ContractCustomer> actualSearchCompanyAsc = repo.searchAndSortAsc("Eriksson", "company");
        List<ContractCustomer> actualSearchCompanyDesc = repo.searchAndSortDesc("Eriksson", "company");
        List<ContractCustomer> actualSearchContactAsc = repo.searchAndSortAsc("Eriksson", "contact");
        List<ContractCustomer> actualSearchContactDesc = repo.searchAndSortDesc("Eriksson", "contact");
        List<ContractCustomer> actualSearchCountryAsc = repo.searchAndSortAsc("Eriksson", "country");
        List<ContractCustomer> actualSearchCountryDesc = repo.searchAndSortDesc("Eriksson", "country");
        List<ContractCustomer> actualCompanyAsc = repo.sortAsc("company");
        List<ContractCustomer> actualCompanyDesc = repo.sortDesc("company");
        List<ContractCustomer> actualContactAsc = repo.sortAsc("contact");
        List<ContractCustomer> actualContactDesc = repo.sortDesc("contact");
        List<ContractCustomer> actualCountryAsc = repo.sortAsc("country");
        List<ContractCustomer> actualCountryDesc = repo.sortDesc("country");
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

}
