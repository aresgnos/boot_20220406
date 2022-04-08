package com.example.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// 토큰 발행 및 정보추출용
@Service
public class JwtUtil {

    private final String SECURITY_KEY = "yuyu8io9i8uykg";

    // 1000 => 1초
    // final = 상수 = 변경할 수 없음
    private final long VALIDATE_TIME = 1000 * 60 * 60 * 9; // 9시간

    // 토큰 생성(아이디 정보)
    public String generatorToken(String username) {

        Map<String, Object> map = new HashMap<>();
        String token = Jwts.builder()
                .setClaims(map)
                .setSubject(username) // 포함시키고 싶은 내용
                .setIssuedAt(new Date(System.currentTimeMillis())) // 유효한 시간(발행시간)
                .setExpiration(new Date(System.currentTimeMillis() + VALIDATE_TIME))
                .signWith(SignatureAlgorithm.HS256, SECURITY_KEY)
                .compact();

        return token;
    }

    // 정보 추출용 메소드
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    // 토큰에서 아이디 추출
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 토큰 만료시간 추출
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 유효시간 체크
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 토큰이 유효한지 체크
    public boolean isTokenValidation(String token, String uid) {
        String username = extractUsername(token);
        if (username.equals(uid) && isTokenExpired(token)) {
            return true;
        }
        return false;
    }

}
