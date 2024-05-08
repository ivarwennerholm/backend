package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.BlacklistCustomerDto;
import org.example.backend.Service.Impl.BlacklistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

//    @RequestMapping("add")
//    public String addBlacklist(){
//        addNewBlacklistCustomer;
//        return "b"
//    }
}
