package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.dto.MemberDTO;
import com.example.jwt.JwtUtil;
import com.example.mapper.MemberMapper;
import com.example.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// backend만 구현함. 화면구현 X, vue.js 또는 react.js 연동
@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {

    @Autowired
    MemberMapper mMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    // 127.0.0.1:9090/ROOT/api/customer/updatepw
    // 암호 변경 (토큰, 현재 암호, 변경할 암호)
    @RequestMapping(value = "/updatepw", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> UpdatepwPOST(
            @RequestHeader(name = "TOKEN") String token,
            @RequestBody MemberDTO member) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0); // 정상적이지 않을 때
        try {
            String username = jwtUtil.extractUsername(token);

            UserDetails user = userDetailsService.loadUserByUsername(member.getUemail());

            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            if (bcpe.matches(member.getUpw(), user.getPassword())) {
                mMapper.updatePw(username, member.getUpw1());
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 127.0.0.1:9090/ROOT/api/customer/updatemember
    // {"uname":"라로", "uphone":"010-9999-9999"}
    // 회원정보수정 (토큰, 이름, 전화번호 수정)
    @RequestMapping(value = "/updatemember", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateMemberPOST(
            @RequestHeader(name = "TOKEN") String token,
            @RequestBody MemberDTO member) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0); // 정상적이지 않을 때

        int ret = mMapper.updateMemberOne(member);
        if (ret == 1) {
            map.put("status", 200);
        }

        return map;
    }

    // 127.0.0.1:9090/ROOT/api/customer/deletemember
    // {"uemail":"a2"}
    // 회원탈퇴 update 중요 정보 내용만 지우기 (토큰, 현재 암호, 아이디를 제외한 내용 지우기)
    @RequestMapping(value = "/deletemember", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deleteMemberPOST(
            @RequestHeader(name = "TOKEN") String token,
            @RequestBody MemberDTO member) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0); // 정상적이지 않을 때

        int ret = mMapper.deleteMemberOne(member);
        if (ret == 1) {
            map.put("status", 200);
        }

        return map;
    }

    // 127.0.0.1:9090/ROOT/api/customer/mypage
    // 마이페이지
    @RequestMapping(value = "/mypage", method = { RequestMethod.GET }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> customerMypageGET(@RequestHeader(name = "TOKEN") String token) {
        System.out.println("MYPAGE:" + token);

        String username = jwtUtil.extractUsername(token);
        System.out.println(username);

        // 토큰이 있어야 실행됨
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200); // 정상적이지 않을 때
        return map;
    }

    // 127.0.0.1:9090/ROOT/api/customer/login
    // {"uemail":"a1", "upw":"a1"}
    // 로그인
    @RequestMapping(value = "/login", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> customerLoginPost(@RequestBody MemberDTO member) {

        System.out.println(member.toString());

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0); // 정상적이지 않을 때

        // 암호화된 암호
        UserDetails user = userDetailsService.loadUserByUsername(member.getUemail());

        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        // 암호화 되지 않는 것과 암호회 된 것 비교하기
        if (bcpe.matches(member.getUpw(), user.getPassword())) {
            String token = jwtUtil.generatorToken(member.getUemail());
            map.put("status", 200); // 0 -> 200
            map.put("token", token);
        }
        return map;
    }

    // 127.0.0.1:9090/ROOT/api/customer/join
    // {"uemail":"a1", "upw":"a1", "uname":"a1", "uphone":"123123"}
    // 회원가입(고객만)
    @RequestMapping(value = "/join", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> customerJoinPost(@RequestBody MemberDTO member) {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        member.setUpw(bcpe.encode(member.getUpw()));
        member.setUrole("CUSTOMER");

        int ret = mMapper.memberJoin(member);
        Map<String, Object> map = new HashMap<>();

        map.put("status", 0);
        if (ret == 1) {
            map.put("status", 200);
        }
        return map;
    }
}
