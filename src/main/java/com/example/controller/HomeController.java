package com.example.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 127.0.0.1:9090/ROOT/
    // 127.0.0.1:9090/ROOT/home
    // 127.0.0.1:9090/ROOT/main
    @GetMapping(value = { "/", "/home", "/main" })
    public String homeGET(
            Model model,
            @AuthenticationPrincipal User user) { // Detailservice에서 만든걸 꺼냄
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping(value = "/page403")
    public String page403GET() {
        return "page403";
    }
}
