package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Security.User;
import org.example.backend.Security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/")
@RequiredArgsConstructor
public class PublicController {

    private final UserDetailsService userDetailsService;
    @RequestMapping("")
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("login")
    public String getLoginPage(Model model){
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user",user);
        System.out.println("testing");
        return "login";
//        return "index";
    }

    @PostMapping("login")
    public String getLoginUser(@ModelAttribute User user, Model model){
        System.out.println("testing 2");
        System.out.println(user.getUsername() + " " + user.getPassword() + " " + user.isEnabled());
        model.addAttribute("user",userDetailsService.loadUserByUsername(user.getUsername()));
        return getAdminLogin();
    }

    @GetMapping(path="admin")
    @PreAuthorize("isAuthenticated()")
    String getAdminLogin(){
        return "index";
    }

    @GetMapping(path="confidential")
    @PreAuthorize("hasAuthority('Admin')")
    String getConfidentialPage(){
        return "security/confidential";
    }
}
