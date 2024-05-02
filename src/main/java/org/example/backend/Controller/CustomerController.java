package org.example.backend.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.CustomerDto;
import org.example.backend.Model.Customer;
import org.example.backend.Service.CustomerService;
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


    /*
    @PostMapping("add")
    public String addCustomer(@Valid @RequestBody CustomerDto customerDto,
                              BindingResult bindingResult,
                              Model model) {
        // Check if there are validation errors
        if (bindingResult.hasErrors()) {
            // If there are validation errors, return to the form with error messages
            return "Error"; // Return to the form page with errors
        } else {
            // If there are no validation errors, proceed with adding the customer
            cusService.addCustomer(customerDto);
            return getAllCustomers(model); // Return to the page showing all customers
        }
    }
     */

    /*
    @PostMapping("add1")
    public String addCustomer2(@Valid @RequestParam String name,
                              @Valid @RequestParam String phone,
                                 Model model){
        cusService.addCustomer(new CustomerDto(name,phone));
        return getAllCustomers(model);
    }
     */

    @PostMapping("add")
    public String addCustomer(@Valid Customer customer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "addNewCustomer.html";
        }
        cusService.addCustomer(new CustomerDto(customer.getName(),customer.getPhone()));
        return getAllCustomers(model);
    }

    @RequestMapping("registerForm")
    public String registerForm(Model model){
        model.addAttribute("customer", new Customer());
        return "addNewCustomer.html";
    }

    @RequestMapping(value = "delete/{id}")
    public String deleteCustomer(@PathVariable Long id, Model model){
        cusService.deleteCustomerById(id);
        return getAllCustomers(model);
    }

    /*
    @PostMapping("updateForm/{id}")
    public String updateForm(@Valid Customer customer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "updateCustomer.html";
        }
        CustomerDto customer = cusService.findCustomerById(customer.getId());
        model.addAttribute("customer", customer);
        return "updateCustomer.html";
    }
     */

    @PostMapping("updateForm7/{id}")
    public String updateForm7(@Valid Customer customer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "updateCustomer.html";
        }
        cusService.updateForm(new CustomerDto(customer.getName(),customer.getPhone()));
        return getAllCustomers(model);
        //CustomerDto customer = cusService.findCustomerById(id);
        //model.addAttribute("customer", customer);
        //return getAllCustomers(model);
    }

    @RequestMapping("updateForm3/{id}")
    public String updateForm3(@PathVariable Long id,
                             Model model){
        CustomerDto customer = cusService.findCustomerById(id);
        model.addAttribute("customer", customer);
        return "updateCustomer.html";
    }

    @RequestMapping("updateForm/{id}")
    public String updateForm(@PathVariable Long id,
                                 Model model){
        CustomerDto customer = cusService.findCustomerById(id);
        model.addAttribute("customer", customer);
        return "updateCustomer.html";
    }

    @PostMapping("update/")
    public String updateCustomer(@Valid Customer customer, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("customer", customer);
            model.addAttribute("id", customer.getId());
            return "updateCustomer.html";
        }
        cusService.updateCustomer(customer.getId(), customer.getName(), customer.getPhone());
        return getAllCustomers(model);
    }

    /* TODO: CAN THIS BE DELETED?
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
    */
}
