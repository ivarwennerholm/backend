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
    public List<ContractCustomerDto> findAllByCompanyNameContaining(String search) {
        return repo.findByCompanyNameContaining(search).
                stream().
                map(this::contractCustomerToContractCustomerDto).
                toList();
    }

}
