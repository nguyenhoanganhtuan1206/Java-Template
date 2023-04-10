package com.javatemplate.api.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/social")
    public String loginPage() {
        return "index.html";
    }
}
