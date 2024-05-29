package org.example.backend.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Model.ContractCustomer;
import org.example.backend.Repository.ContractCustomerRepository;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.backend.BackendApplication.*;

@Service
@RequiredArgsConstructor
public class ContractCustomerService {
    private final ContractCustomerRepository repo;

    public ContractCustomerDto contractCustomerToContractCustomerDto(ContractCustomer cc) {
        return ContractCustomerDto.
                builder().
                id(cc.getId()).
                customerId(cc.getCustomerId()).
                companyName(cc.getCompanyName()).
                contactName(cc.getContactName()).
                contactTitle(cc.getContactTitle()).
                streetAddress(cc.getStreetAddress()).
                city(cc.getCity()).
                postalCode(cc.getPostalCode()).
                country(cc.getCountry()).
                phone(cc.getPhone()).
                fax(cc.getFax()).
                build();
    }

    public Optional<ContractCustomerDto> getById(Long id) {
        return repo.
                findAll().
                stream().
                map(this::contractCustomerToContractCustomerDto).
                filter(cc -> Objects.equals(cc.getId(), id)).
                findFirst();
    }

    public List<ContractCustomerDto> sortListWithSwedishLetters(List<ContractCustomerDto> list, String sort, String cat) {
        Collator collator = Collator.getInstance(new Locale("sv", "SE"));
        Comparator<ContractCustomerDto> comparator;
        switch (cat) {
            case "company":
                comparator = Comparator.comparing(ContractCustomerDto::getCompanyName, collator);
                break;
            case "contact":
                comparator = Comparator.comparing(ContractCustomerDto::getContactName, collator);
                break;
            case "country":
                comparator = Comparator.comparing(ContractCustomerDto::getCountry, collator);
                break;
            default:
                throw new IllegalArgumentException(ANSI_RED + "Invalid sort criteria: " + sort + ANSI_RESET);
        }
        if (sort.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }
        return list.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<ContractCustomerDto> getContractCustomers(String search, String sort, String cat) {
        List<ContractCustomer> list;
        List<ContractCustomerDto> output;

        if (search != null && !search.isEmpty()) {
            if (sort != null && sort.equals("desc")) {
                list = repo.searchAndSortDesc(search, cat);
            } else if (sort != null && sort.equals("asc")) {
                list = repo.searchAndSortAsc(search, cat);
            } else {
                list = repo.search(search);
            }
        } else {
            if (sort != null && sort.equals("desc")) {
                list = repo.sortDesc(cat);
            } else if (sort != null && sort.equals("asc")) {
                list = repo.sortAsc(cat);
            } else {
                list = repo.findAll();
            }
        }
        output = list.stream()
                .map(this::contractCustomerToContractCustomerDto)
                .collect(Collectors.toList());
        if (sort != null && cat != null) {
            output = sortListWithSwedishLetters(output, sort, cat);
        }
        return output;
    }

}
