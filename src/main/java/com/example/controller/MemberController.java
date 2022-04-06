package com.example.controller;

import com.example.dto.MemberDTO;
import com.example.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    MemberService mService;

    @GetMapping(value = "/join")
    public String joinGET() {
        return "join";
    }

    @PostMapping(value = "/joinaction")
    public String joinPOST(@ModelAttribute MemberDTO member) {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        member.setUpw(bcpe.encode(member.getUpw()));
        member.setUrole("SELLER");

        mService.memberInsertOne(member);

        return "redirect:/";

    }

    @GetMapping(value = "/login")
    public String loginGET() {
        return "login";
    }
}
