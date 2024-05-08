package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Service.ContractCustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("contractcustomers")
@RequiredArgsConstructor
public class ContractCustomerController {

    private final ContractCustomerService service;

/*
    @GetMapping
    public String getAllContractCustomer(Model model) {
        List<ContractCustomerDto> list = service.getAll();
        model.addAttribute("list", list);
        return "allContractCustomers.html";
    }
*/

    @GetMapping
    public String getAllContractCustomer(@RequestParam(name = "search", required = false) String search, Model model) {
        List<ContractCustomerDto> list;
        if (search != null && !search.isEmpty()) {
            System.out.println("search is not null or empty");
            list = service.findAllByCompanyNameContaining(search);
        } else {
            System.out.println("search is null or empty");
            list = service.getAll();
        }
        model.addAttribute("list", list);
        return "allContractCustomers.html";
    }

    @GetMapping("/{id}")
    public String getContractCustomer(@PathVariable Long id, Model model) {
        Optional<ContractCustomerDto> ccOptional = service.getById(id);
        if (ccOptional.isPresent()) {
            ContractCustomerDto cc = ccOptional.get();
            model.addAttribute("cc", cc);
        } else {
            model.addAttribute("error", "Customer not found");
        }
        return "contractCustomer.html";
    }

    /*@GetMapping("search/{search}")
    public String searchContractCustomers(@PathVariable String search, Model model) {
        model.addAttribute("search", search);
        System.out.println(search);
        *//*List <ContractCustomerDto> list = service.findAllByCompanyNameContaining(search);
        if (!list.isEmpty()) {
            model.addAttribute("list", list);
        } else {
            model.addAttribute("notfound", "No Contract Customer found");
        }*//*
        // return "redirect:/contractcustomers";
        return "redirect:/contractcustomers?search=" + search;
    }*/

}
