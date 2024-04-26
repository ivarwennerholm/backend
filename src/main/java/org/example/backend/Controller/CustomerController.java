package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.CustomerDto;
import org.example.backend.Service.CustomerService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@RestController
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
    public String addCustomer(@RequestParam String name,
                                 @RequestParam String phone,
                                 Model model){
        cusService.addCustomer(new CustomerDto(name,phone));
        return getAllCustomers(model);
    }

    @RequestMapping("registerForm")
    public String registerForm(){
        return "addNewCustomer.html";
    }

    @RequestMapping("delete/{id}")
    public String deleteCustomer(@PathVariable Long id, Model model){
        cusService.deleteCustomerById(id);
        return getAllCustomers(model);
    }

    @RequestMapping("updateForm/{id}")
    public String updateForm(@PathVariable Long id,
                                 Model model){
        CustomerDto customer = cusService.findCustomerById(id);
        model.addAttribute("customer", customer);
        return "updateCustomer.html";
    }

    @PostMapping("update/{id}")
    public String updateCustomer(@PathVariable Long id,
                                 @RequestParam(required = false) String newName,
                                 @RequestParam(required = false) String newPhone,
                                 Model model){
        CustomerDto c = cusService.findCustomerById(id);
        if (!newName.isEmpty()){
            c.setName(newName);
        }
        if (!newPhone.isEmpty()){
            c.setPhone(newPhone);
        }
        cusService.updateCustomer(id,c.getName(),c.getPhone());
        return getAllCustomers(model);
    }


//    @RequestMapping("getAll")
//    public List<CustomerDto> getAllCustomers() {
//        return cusService.getAll();
//    }
//
//    @PostMapping("add")
//    public String addNewCustomer(@RequestBody CustomerDto newCustomer){
//        return cusService.addCustomer(newCustomer);
//    }
//
//    @RequestMapping("delete")
//    public String deleteCustomer(@RequestParam String name){
//        return cusService.deleteCustomerByName(name);
//    }
//
//    @RequestMapping("update")
//    public String updateCustomer(@RequestParam Long id,
//                                 @RequestParam(required = false) String name,
//                                 @RequestParam(required = false) String phone){
//        return cusService.updateCustomer(id,name,phone);
//    }
}
