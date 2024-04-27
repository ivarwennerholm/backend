package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.CustomerDto;
import org.example.backend.Model.Customer;
import org.example.backend.Repository.CustomerRepository;
import org.example.backend.Service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepo;
    @Override
    public Customer customerDtoToCustomer(CustomerDto c) {
        return Customer.builder().name(c.getName()).phone(c.getPhone()).build();
    }

    @Override
    public CustomerDto customerToCustomerDto(Customer c) {
        return CustomerDto.builder().id(c.getId()).name(c.getName()).phone(c.getPhone()).build();
    }


    @Override
    public List<CustomerDto> getAll() {
        return customerRepo.findAll().stream().map(k -> customerToCustomerDto(k)).toList();
    }

    @Override
    public String addCustomer(CustomerDto c) {
        customerRepo.save(customerDtoToCustomer(c));
        return "added new customer";
    }

    @Override
    public String deleteCustomerByName(String name) {
        Customer c = customerRepo.findByName(name);
        customerRepo.delete(c);
        return "delete customer";
    }

    @Override
    public String deleteCustomerById(Long id) {
        Customer c = customerRepo.findById(id).get();
        customerRepo.delete(c);
        return "delete customer";
    }

    @Override
    public CustomerDto findCustomerById(Long id) {
        return customeerToCustomerDto(customerRepo.findById(id).get());
    }

    @Override
    public String updateCustomer(Long id, String name, String phone) {
        Customer c = customerRepo.findById(id).get();
        if (name != null){
            c.setName(name);
        }
        if (phone != null){
            c.setPhone(phone);
        }
        customerRepo.save(c);
        return "update customer";
    }
}
