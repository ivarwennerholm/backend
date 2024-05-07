package org.example.backend.Service;

import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Model.ContractCustomer;

import java.util.List;

public interface ContractCustomerService {
    public ContractCustomerDto contractCustomerToContractCustomerDto(ContractCustomer cc);

    public List<ContractCustomerDto> getAll();
}
