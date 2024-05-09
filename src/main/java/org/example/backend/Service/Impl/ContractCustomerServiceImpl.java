package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Model.ContractCustomer;
import org.example.backend.Repository.ContractCustomerRepository;
import org.example.backend.Service.ContractCustomerService;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractCustomerServiceImpl implements ContractCustomerService {
    private final ContractCustomerRepository repo;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    @Override
    public ContractCustomerDto contractCustomerToContractCustomerDto(ContractCustomer cc) {
        return ContractCustomerDto.
                builder().
                id(cc.id).
                customerId(cc.customerId).
                companyName(cc.companyName).
                contactName(cc.contactName).
                contactTitle(cc.contactTitle).
                streetAddress(cc.streetAddress).
                city(cc.city).
                postalCode(cc.postalCode).
                country(cc.country).
                phone(cc.phone).
                fax(cc.fax).
                build();
    }

    @Override
    public Optional<ContractCustomerDto> getById(Long id) {
        return repo.
                findAll().
                stream().
                map(this::contractCustomerToContractCustomerDto).
                filter(cc -> Objects.equals(cc.id, id)).
                findFirst();
    }

    @Override
    public List<ContractCustomerDto> sortListWithSwedishLetters(List<ContractCustomerDto> list, String sort, String cat) {
        Collator collator = Collator.getInstance(new Locale("sv", "SE"));
        Comparator<ContractCustomerDto> comparator;
        switch (cat) {
            case "company":
                comparator = Comparator.comparing(ccdto -> ccdto.companyName, collator);
                break;
            case "contact":
                comparator = Comparator.comparing(ccdto -> ccdto.contactName, collator);
                break;
            case "country":
                comparator = Comparator.comparing(ccdto -> ccdto.country, collator);
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

    @Override
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
