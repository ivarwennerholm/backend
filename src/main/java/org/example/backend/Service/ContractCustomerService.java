package org.example.backend.Service;

import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Model.ContractCustomer;

import java.util.List;
import java.util.Optional;

public interface ContractCustomerService {
    public ContractCustomerDto contractCustomerToContractCustomerDto(ContractCustomer cc);

    public List<ContractCustomerDto> getAll();

    public Optional<ContractCustomerDto> getById(Long id);

    public List<ContractCustomerDto> getFilteredList(String search, String sort, String cat);
}
