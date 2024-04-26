package org.example.backend1.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend1.DTO.CustomerDto;
import org.example.backend1.Model.Customer;
import org.example.backend1.Repository.CustomerRepository;
import org.example.backend1.Service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService cusService;

    @RequestMapping("getAll")
    public List<CustomerDto> getAllCustomers() {
        return cusService.getAll();
    }

    @PostMapping("add")
    public String addNewCustomer(@RequestBody CustomerDto newCustomer){
        return cusService.addCustomer(newCustomer);
    }

    @RequestMapping("delete")
    public String deleteCustomer(@RequestParam String name){
        return cusService.deleteCustomerByName(name);
    }

    @RequestMapping("update")
    public String updateCustomer(@RequestParam Long id,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String phone){
        return cusService.updateCustomer(id,name,phone);
    }
}
