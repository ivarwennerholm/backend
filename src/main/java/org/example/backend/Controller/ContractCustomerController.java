package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Service.ContractCustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("contractcustomers")
@RequiredArgsConstructor
public class ContractCustomerController {

    private final ContractCustomerService service;

    @GetMapping
    public String getAllContractCustomer(Model model) {
        List<ContractCustomerDto> list = service.getAll();
        model.addAttribute("list", list);
        return "allContractCustomers.html";
    }

    @GetMapping("/{id}")
    public String getContractCustomer(@PathVariable Long id, Model model) {
        return null;
    }

}
