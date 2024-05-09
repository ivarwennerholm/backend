package org.example.backend.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.BlacklistCustomerDto;
import org.example.backend.Service.Impl.BlacklistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("blacklist")
@RequiredArgsConstructor
public class BlacklistController {

    private final BlacklistService blackService;

    @RequestMapping("all")
    public String getAllBlacklist(Model model){
        try {
            List<BlacklistCustomerDto> list = blackService.getAll();
            model.addAttribute("allBlacklist", list);
        } catch (IOException e) {
            model.addAttribute("error",e.getMessage());
        }
        return "allBlacklistCustomers.html";
    }

    @PostMapping("add")
    public String addNewToBlacklist(@RequestParam String email, @RequestParam String name, @RequestParam boolean isOk, Model model){
        System.out.println(email + " " + name + " " + isOk);
        try {
            blackService.addNewBlacklistCustomer(name,email,isOk);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return getAllBlacklist(model);
    }
}
