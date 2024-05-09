package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Model.ContractCustomer;
import org.example.backend.Repository.ContractCustomerRepository;
import org.example.backend.Service.ContractCustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractCustomerServiceImpl implements ContractCustomerService {
    private final ContractCustomerRepository repo;

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
    public List<ContractCustomerDto> getAll() {
        return repo.
                findAll().
                stream().
                map(this::contractCustomerToContractCustomerDto).
                toList();
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
    public List<ContractCustomerDto> getFilteredList(String search, String sort, String cat) {
        List<ContractCustomer> list = new ArrayList<>();
        if (search != null && !search.isEmpty()) {
            if (sort == null || cat == null) {
                list = repo.search(search);
                System.out.println("repo.search(" + search+ ") called");
            } else {
                if (sort.equals("desc")) {
                    if (cat.equals("company")) {
                        list = repo.searchAndSortDesc(search, "company");
                        System.out.println("repo.searchAndSortDesc(" + search + ", \"company\") called"); // FOR TESTING ONLY
                    } else if (cat.equals("contact")) {
                        list = repo.searchAndSortDesc(search, "contact");
                        System.out.println("repo.searchAndSortDesc(" + search + ", \"contact\") called"); // FOR TESTING ONLY
                    } else if (cat.equals("country")) {
                        list = repo.searchAndSortDesc(search, "country");
                        System.out.println("repo.searchAndSortDesc(" + search + ", \"country\") called"); // FOR TESTING ONLY
                    }
                } else if (sort.equals("asc")) {
                    if (cat.equals("company")) {
                        list = repo.searchAndSortAsc(search, "company");
                        System.out.println("repo.searchAndSortAsc(" + search + ", \"company\") called"); // FOR TESTING ONLY
                    } else if (cat.equals("contact")) {
                        list = repo.searchAndSortAsc(search, "contact");
                        System.out.println("repo.searchAndSortAsc(" + search + ", \"contact\") called"); // FOR TESTING ONLY
                    } else if (cat.equals("country")) {
                        list = repo.searchAndSortAsc(search, "country");
                        System.out.println("repo.searchAndSortAsc(" + search + ", \"country\") called"); // FOR TESTING ONLY
                    }
                }
            }
        } else {
            if (sort == null || cat == null) {
                list = repo.findAll();
            } else {
                if (sort.equals("desc")) {
                    if (cat.equals("company")) {
                        list = repo.sortDesc("company");
                        System.out.println("repo.sortDesc(\"company\") called"); // FOR TESTING ONLY
                    } else if (cat.equals("contact")) {
                        list = repo.sortDesc("contact");
                        System.out.println("repo.sortDesc(\"contact\") called"); // FOR TESTING ONLY
                    } else if (cat.equals("country")) {
                        list = repo.sortDesc("country");
                        System.out.println("repo.sortDesc(\"country\") called"); // FOR TESTING ONLY
                    }
                } else if (sort.equals("asc")) {
                    if (cat.equals("company")) {
                        list = repo.sortAsc("company");
                        System.out.println("repo.sortAsc(\"company\") called"); // FOR TESTING ONLY
                    } else if (cat.equals("contact")) {
                        list = repo.sortAsc("contact");
                        System.out.println("repo.sortAsc(\"contact\") called"); // FOR TESTING ONLY
                    } else if (cat.equals("country")) {
                        list = repo.sortAsc("country");
                        System.out.println("repo.sortAsc(\"country\") called"); // FOR TESTING ONLY
                    }
                }
            }
        }
        return list.
                stream().
                map(this::contractCustomerToContractCustomerDto).
                toList();
    }

}
