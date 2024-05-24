package org.example.backend.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//controller for testing
@Controller
public class TestController{
    @GetMapping(path="admin")
    @PreAuthorize("isAuthenticated()")
    String getAdminPage(){
        return "security/admin.html";
    }
}
