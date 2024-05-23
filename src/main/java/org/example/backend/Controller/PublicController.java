package org.example.backend.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class PublicController {
    @RequestMapping("")
    public String getIndexPage(){
        return "index.html";
    }
}
