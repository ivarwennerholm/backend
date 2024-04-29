package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class StaticTestController {

    @RequestMapping("/static")
    public String staticPage() {
        return "static.html";
    }
}
