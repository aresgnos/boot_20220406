package com.example.service;

import com.example.dto.MemberDTO;

import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    // 회원가입
    public int memberInsertOne(MemberDTO member);

    // 회원탈퇴
    public int memberDeleteOne(String email);

}
