package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Model.ContractCustomer;
import org.example.backend.Repository.ContractCustomerRepository;
import org.example.backend.Service.ContractCustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractCustomerServiceImpl implements ContractCustomerService {
    private final ContractCustomerRepository repo;

    @Override
    public ContractCustomerDto contractCustomerToContractCustomerDto(ContractCustomer cc) {
        return ContractCustomerDto.
                builder().
                id(cc.id).
                companyName(cc.companyName).
                contactName(cc.contactName).
                country(cc.country).
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
}
