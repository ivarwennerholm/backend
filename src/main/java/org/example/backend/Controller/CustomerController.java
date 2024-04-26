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

    @RequestMapping("getAll")
    public String getAllCustomers(Model model){
        allCustomersList = cusService.getAll();
        model.addAttribute("fruit","apple");
        model.addAttribute("allCustomers", allCustomersList);
        return "allCustomers.html";
    }

    @RequestMapping("addNewCustomer")
    public String addCustomer(){
        return "addNewCustomer.html";
    }

    @PostMapping("getAll")
    public String submitCustomer(@RequestParam String name,
                                 @RequestParam String phone,
                                 Model model){
        cusService.addCustomer(new CustomerDto(name,phone));
        allCustomersList = cusService.getAll();
        model.addAttribute("allCustomers", allCustomersList);
        return "allCustomers.html";
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
