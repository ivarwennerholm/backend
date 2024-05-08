package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Model.ContractCustomer;
import org.example.backend.Repository.ContractCustomerRepository;
import org.example.backend.Service.ContractCustomerService;
import org.springframework.stereotype.Service;

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
        List<ContractCustomerDto> all = repo.
                findAll().
                stream().
                map(this::contractCustomerToContractCustomerDto).
                toList();

        for (ContractCustomerDto customerDto : all) {
            System.out.println(customerDto);
        }

        return all;

    }

    @Override
    public Optional<ContractCustomerDto> getById(Long id) {
        System.out.println("Service: CustomerId = " + id);
        System.out.println(repo.findAll().stream().map(this::contractCustomerToContractCustomerDto).toList());
        return repo.
                findAll().
                stream().
                map(this::contractCustomerToContractCustomerDto).
                filter(cc -> Objects.equals(cc.id, id)).
                findFirst();
    }
}
