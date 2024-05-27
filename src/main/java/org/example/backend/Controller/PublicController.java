package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Security.UserRepository;
import org.example.backend.Security.UserServiceImpl;
import org.example.backend.Security.UsernameDto;
import org.example.backend.Security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/")
@RequiredArgsConstructor
public class PublicController {

    private final UserServiceImpl userServiceImpl;

    private final UserRepository userRepository;

    @RequestMapping("")
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("login")
    public String getLoginPage(Model model){
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("login")
    public String getLoginUser(@ModelAttribute User user, Model model){
        model.addAttribute("user", userServiceImpl.loadUserByUsername(user.getUsername()));
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

    @GetMapping(path = "forgotpassword")
    String getForgotPasswordPage(Model model){
        UsernameDto userxx = new UsernameDto();
        model.addAttribute("user",userxx);
        return "forgotpassword";
    }

    @PostMapping(path = "forgotpassword")
    String getForgotPasswordUserName(@ModelAttribute UsernameDto userNameDto, Model model){
        System.out.println("username" + userNameDto.getUsername());

        User user = userRepository.getUserByUsername(userNameDto.getUsername());
//        String output = "";
//        if (user!=null){
//            output = userServiceImpl.sendEmail(user);
//            System.out.println(output);
//        }
        System.out.println("reset token: " + userServiceImpl.sendEmail(user));
        return "index";
    }
}
