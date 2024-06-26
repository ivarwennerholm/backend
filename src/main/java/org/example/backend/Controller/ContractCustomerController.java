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

    @GetMapping
    protected String getContractCustomers(@RequestParam(name = "search", required = false) String search,
                                         @RequestParam(name = "sort", required = false) String sort,
                                         @RequestParam(name = "cat", required = false) String cat,
                                         Model model) {
        List<ContractCustomerDto> list = service.getContractCustomers(search, sort, cat);
        if (list.isEmpty()) {
            model.addAttribute("message", "No contract customers found");
        }
        model.addAttribute("list", list);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("cat", cat);
        return "allContractCustomers.html";
    }

    @GetMapping("/{id}")
    protected String getContractCustomer(@PathVariable Long id, Model model) {
        Optional<ContractCustomerDto> ccOptional = service.getById(id);
        if (ccOptional.isPresent()) {
            ContractCustomerDto cc = ccOptional.get();
            model.addAttribute("cc", cc);
        } else {
            model.addAttribute("error", "Contract customer not found");
        }
        return "contractCustomer.html";
    }

}
