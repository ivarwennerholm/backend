package org.example.backend.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.BlacklistPersonDto;
import org.example.backend.Service.Impl.BlacklistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            List<BlacklistPersonDto> list = blackService.getAll();
            model.addAttribute("allBlacklist", list);
        } catch (IOException e) {
            model.addAttribute("error",e.getMessage());
        }
        return "allBlacklistCustomers.html";
    }

    @PostMapping("add")
    public String addNewToBlacklist(@RequestParam String email, @RequestParam String name, @RequestParam boolean isOk, Model model){
        try {
            blackService.addNewBlacklistPerson(name,email,isOk);
            model.addAttribute("addSuccessMsg","New person is added to the blacklsit.");
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return getAllBlacklist(model);
    }

    @RequestMapping("blacklistForm")
    public String blacklistForm(){
        return "addNewPersonToBlacklist.html";
    }

    @RequestMapping("updateForm/{email}")
    public String updateForm(@PathVariable String email, Model model){
        try {
            BlacklistPersonDto p = blackService.getBlacklistPerson(email);
            model.addAttribute("person",p);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "UpdateExistingBlacklist.html";
    }

    @PostMapping("update")
    public String updateBlacklist(@RequestParam String email, @RequestParam String name, @RequestParam boolean isOk, Model model){
        try {
            blackService.updateBlacklistedPerson(email,name,isOk);
            model.addAttribute("updateSuccessMsg","Blacklisted record is updated");
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return getAllBlacklist(model);
    }
}
