package org.example.backend.Service;

import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Model.ContractCustomer;

import java.util.List;
import java.util.Optional;

public interface ContractCustomerService {
    ContractCustomerDto contractCustomerToContractCustomerDto(ContractCustomer cc);

    List<ContractCustomerDto> getContractCustomers(String search, String sort, String cat);

    Optional<ContractCustomerDto> getById(Long id);

    List<ContractCustomerDto> sortListWithSwedishLetters(List<ContractCustomerDto> list, String sort, String cat);

}
