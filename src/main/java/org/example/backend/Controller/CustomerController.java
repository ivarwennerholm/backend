package org.example.backend.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.CustomerDto;
import org.example.backend.Model.Customer;
import org.example.backend.Service.CustomerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService cusService;

    private List<CustomerDto> allCustomersList = new ArrayList<>();

    @RequestMapping("all")
    public String getAllCustomers(Model model){
        allCustomersList = cusService.getAll();
        model.addAttribute("allCustomers", allCustomersList);
        return "allCustomers.html";
    }

    @PostMapping("add")
    public String addCustomer(@Valid Customer customer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "addNewCustomer.html";
        }
        cusService.addCustomer(new CustomerDto(customer.getName(),customer.getPhone(),customer.getEmail()));
        return getAllCustomers(model);
    }

    @RequestMapping("registerForm")
    @PreAuthorize("hasAuthority('Admin')")
    public String registerForm(Model model){
        model.addAttribute("customer", new Customer());
        return "addNewCustomer.html";
    }

    @RequestMapping(value = "delete/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteCustomer(@PathVariable Long id, Model model){
        try{
            cusService.deleteCustomerById(id);
            model.addAttribute("success","Customer is delete sucessfully");
        } catch (RuntimeException e){
            model.addAttribute("error",e.getMessage());
        }
        return getAllCustomers(model);
    }

    @RequestMapping("updateForm/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public String updateForm(@PathVariable Long id,
                                 Model model){
        CustomerDto customer = cusService.findCustomerById(id);
        model.addAttribute("customer", customer);
        return "updateCustomer.html";
    }

    @PostMapping("update")
    public String updateCustomer(@Valid Customer customer, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("customer", customer);
            model.addAttribute("id", customer.getId());
            return "updateCustomer.html";
        }
        cusService.updateCustomer(customer.getId(), customer.getName(), customer.getPhone());
        return getAllCustomers(model);
    }

}
