package org.example.backend.Service;

import org.example.backend.DTO.CustomerDto;
import org.example.backend.Model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer customerDtoToCustomer(CustomerDto c);

    CustomerDto customerToCustomerDto(Customer c);

    List<CustomerDto> getAll();

    String addCustomer(CustomerDto c);

    void addCustomerWithoutID(String name, String phone, String email);

    Optional<Customer> getCustomerByNamePhoneAndEmail(String name, String phone, String email);

    Customer getCustomerByEmail(String email);

    String deleteCustomerByName(String name);

    String deleteCustomerById(Long id);

    CustomerDto findCustomerById(Long id);

    String updateCustomer(Long id, String name, String phone);

    String updateForm(CustomerDto customerDto);

    Customer getCustomerById(Long id);

    Customer getLastCustomer();

}
