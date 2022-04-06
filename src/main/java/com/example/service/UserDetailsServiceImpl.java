package com.example.service;

import java.util.Collection;

import com.example.dto.MemberDTO;
import com.example.mapper.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 로그인에서 버튼을 누르면 우리가 모르는 컨트롤러를 통과해서 서비스로 이메일이 전달됨.
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    MemberMapper mMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserDetailsService : " + username);
        MemberDTO member = mMapper.memberEmail(username);

        // 권한 정보를 문자열, 배열로 만듦
        // ** 권한은 배열로 들어갔다 ** => 꺼낼 때 0,1,2로 꺼내야함.
        String[] strRole = { member.getUrole() };

        // String 배열 권한을 Collection<GrantedAuthority> 타입으로 변환함
        Collection<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(strRole);

        User user = new User(member.getUemail(), member.getUpw(), roles);
        return user;
    }

}
