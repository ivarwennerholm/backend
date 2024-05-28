package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Security.PasswordResetDto;
import org.example.backend.Security.*;
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
        UsernameDto user = new UsernameDto();
        model.addAttribute("user",user);
        return "forgotpassword";
    }

    @PostMapping(path = "forgotpassword")
    String getForgotPasswordUserName(@ModelAttribute UsernameDto userNameDto, Model model){
        System.out.println("username: " + userNameDto.getUsername());
        try {
            User user = userRepository.getUserByUsername(userNameDto.getUsername());
            System.out.println("reset token: " + userServiceImpl.sendEmail(user));
            model.addAttribute("success","reset token has sent");
            return getForgotPasswordPage(model);
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            System.out.println(e.getMessage());
            return getForgotPasswordPage(model);
        }
    }
    @GetMapping(path = "passwordreset/{token}")
    String getPasswordResetPage(@PathVariable String token, Model model){
        PasswordResetDto passwordReset = new PasswordResetDto();
        passwordReset.setToken(token);
        model.addAttribute("passwordReset",passwordReset);
        model.addAttribute("token",token);
        return "passwordResetProcess";
    }

    @PostMapping(path = "passwordreset")
    String getNewPassword(@ModelAttribute PasswordResetDto passwordReset, Model model){
        System.out.println("password reset");
        System.out.println(passwordReset.getMail());
        System.out.println(passwordReset.getNewPassword());
        try {
            userServiceImpl.updatePassword(passwordReset);
            System.out.println("password reset successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "index";
    }
}
