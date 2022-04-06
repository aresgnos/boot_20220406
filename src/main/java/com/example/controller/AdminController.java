package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    // 127.0.0.1:9090/ROOT/admin
    // 127.0.0.1:9090/ROOT/admin/home

    @GetMapping(value = { "/", "/home" })
    public @ResponseBody String adminGET() {
        return "admin 글자 그대로 나옴";
    }
}
