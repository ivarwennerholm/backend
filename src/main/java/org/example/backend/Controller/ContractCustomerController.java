package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Service.ContractCustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("contractcustomers")
@RequiredArgsConstructor
public class ContractCustomerController {

    private final ContractCustomerService service;

    @RequestMapping("/")
        public String getAllContractCustomer (Model model){
        List<ContractCustomerDto> list = service.getAll();
            model.addAttribute("list", list);
            return "allContractCustomers.html";
        }

    @RequestMapping("test")
    public List<ContractCustomerDto> test (){
        return service.getAll();
    }

}
