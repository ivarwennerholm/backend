package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.CustomerDto;
import org.example.backend.Model.Booking;
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
        return Customer.builder().id(c.getId()).name(c.getName()).phone(c.getPhone()).email(c.getEmail()).build();
    }

    @Override
    public CustomerDto customerToCustomerDto(Customer c) {
        return CustomerDto.builder().id(c.getId()).name(c.getName()).phone(c.getPhone()).email(c.getEmail()).build();
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
    public void addCustomerWithoutID(String name, String phone, String email) {
        Customer customer = Customer.builder().name(name).phone(phone).email(email).build();
        customerRepo.save(customer);
    }

    @Override
    public Customer getCustomerByNameAndPhone(String name, String phone) {
        return customerRepo.getCustomerByNameAndPhone(name, phone);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepo.findAll().stream().filter(k -> k.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public String deleteCustomerByName(String name) {
        Customer c = customerRepo.findByName(name);
        customerRepo.delete(c);
        return "delete customer " + c.getName();
    }

    @Override
    public String deleteCustomerById(Long id) {
        Customer c = customerRepo.findById(id).get();
        List<Booking> bList = c.getBookingList();
        if (bList.size()==0){
            customerRepo.delete(c);
        } else {
            throw new RuntimeException("This customer has existing booking(s)");
        }
        return "delete customer";
    }

    @Override
    public CustomerDto findCustomerById(Long id) {
        return customerToCustomerDto(customerRepo.findById(id).get());
    }

    @Override
    public String updateCustomer(Long id, String name, String phone) {
        Customer c = customerRepo.findById(id).get();
        if (!name.isEmpty()){
            c.setName(name);
        }
        if (!phone.isEmpty()){
            c.setPhone(phone);
        }
        customerRepo.save(c);
        Customer x = customerRepo.findById(id).get();
        return "update customer " + x.getName() + " " + x.getPhone() ;
    }

    @Override
    public String updateForm(CustomerDto customerDto) {
        customerRepo.save(customerDtoToCustomer(customerDto));
        return "updated customer";
    }

    @Override
    public Customer getCustomerById(Long id) {
        return null;
    }
}
