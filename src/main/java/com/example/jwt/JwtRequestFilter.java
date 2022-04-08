package com.example.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

// 개별적으로 필터가 수행되게 함
@WebFilter(urlPatterns = { "/api/customer/mypage" })
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 토큰 가져오기
            String token = request.getHeader("TOKEN");
            if (token != null) {
                if (token.length() > 0) {
                    // 토큰을 이용해서 아이디 추출하기
                    String username = jwtUtil.extractUsername(token);

                    // 토큰 검증
                    System.out.println("jwtRequestFilter : " + token);
                    System.out.println("username: " + username);

                    // 컨트롤러로 이동
                    filterChain.doFilter(request, response);
                }
            }
            // 토큰 없으면 오류발생
            else {
                throw new Exception("토큰없음");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(-1, "토큰오류");
        }
    }

}
