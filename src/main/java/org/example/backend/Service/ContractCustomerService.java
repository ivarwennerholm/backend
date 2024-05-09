package org.example.backend.Service;

import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Model.ContractCustomer;

import java.util.List;
import java.util.Optional;

public interface ContractCustomerService {
    public ContractCustomerDto contractCustomerToContractCustomerDto(ContractCustomer cc);

    public List<ContractCustomerDto> getContractCustomers(String search, String sort, String cat);

    public Optional<ContractCustomerDto> getById(Long id);

    public List<ContractCustomerDto> sortListWithSwedishLetters(List<ContractCustomerDto> list, String sort, String cat);

}
