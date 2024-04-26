package org.example.backend1.Service;

import org.example.backend1.DTO.CustomerDto;
import org.example.backend1.Model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {
    public Customer customerDtoToCustomer(CustomerDto c);

    public CustomerDto customeerToCustomerDto(Customer c);

    public List<CustomerDto> getAll();

    public String addCustomer(CustomerDto c);

    public String deleteCustomerByName(String name);

    public String updateCustomer(Long id, String name, String phone);
}
