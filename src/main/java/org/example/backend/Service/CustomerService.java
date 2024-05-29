package org.example.backend.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.CustomerDto;
import org.example.backend.Model.Booking;
import org.example.backend.Model.Customer;
import org.example.backend.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepo;

    public Customer customerDtoToCustomer(CustomerDto c) {
        return Customer.builder().id(c.getId()).name(c.getName()).phone(c.getPhone()).email(c.getEmail()).build();
    }

    public CustomerDto customerToCustomerDto(Customer c) {
        return CustomerDto.builder().id(c.getId()).name(c.getName()).phone(c.getPhone()).email(c.getEmail()).build();
    }

    public List<CustomerDto> getAll() {
        return customerRepo.findAll().stream().map(k -> customerToCustomerDto(k)).toList();
    }

    public String addCustomer(CustomerDto c) {
        customerRepo.save(customerDtoToCustomer(c));
        return "added new customer";
    }

    public void addCustomerWithoutID(String name, String phone, String email) {
        Customer customer = Customer.builder().name(name).phone(phone).email(email).build();
        customerRepo.save(customer);
    }

    public Optional<Customer> getCustomerByNamePhoneAndEmail(String name, String phone, String email) {
        return customerRepo.getCustomerByNamePhoneAndEmail(name, phone, email);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepo.findAll().stream().filter(k -> k.getEmail().equals(email)).findFirst().orElse(null);
    }

    public String deleteCustomerByName(String name) {
        Customer c = customerRepo.findByName(name);
        customerRepo.delete(c);
        return "delete customer " + c.getName();
    }

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

    public CustomerDto findCustomerById(Long id) {
        return customerToCustomerDto(customerRepo.findById(id).get());
    }

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

    public String updateForm(CustomerDto customerDto) {
        customerRepo.save(customerDtoToCustomer(customerDto));
        return "updated customer";
    }

    public Customer getCustomerById(Long id) {
        return null;
    }

    public Customer getLastCustomer() {
        return customerRepo.getLastCustomer();
    }

}
