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
import java.util.stream.Collectors;

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
    public Optional<ContractCustomerDto> getById(Long id) {
        return repo.
                findAll().
                stream().
                map(this::contractCustomerToContractCustomerDto).
                filter(cc -> Objects.equals(cc.id, id)).
                findFirst();
    }

    @Override
    public List<ContractCustomerDto> getContractCustomers(String search, String sort, String cat) {
        List<ContractCustomer> list;
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

        return list.stream()
                .map(this::contractCustomerToContractCustomerDto)
                .collect(Collectors.toList());
    }

}
