package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.service.UserDetailsServiceImpl;

@Configuration // 환경설정파일
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        // 1. 직접 만든 detailService 객체 가져오기
        @Autowired
        UserDetailsServiceImpl detailsService;

        // 회원가입시 암호화 했던 방법의 객체생성
        // 2. 암호화 방법 객체 생성, @Bean은 서버 구동시 자동으로 객체 생성됨
        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // 3. 직접만든 detailsService에 암호화 방법 적용
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(detailsService)
                                .passwordEncoder(bCryptPasswordEncoder());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                // super.configure(http);

                // 페이지별 접근 권한 설정
                http.authorizeRequests()
                                .antMatchers("/admin", "/admin/**")
                                .hasAuthority("ADMIN")
                                .antMatchers("/seller", "/seller/**")
                                .hasAnyAuthority("ADMIN", "SELLER")
                                .antMatchers("/customer", "/customer/**")
                                .hasAuthority("CUSTOMER")
                                .anyRequest().permitAll(); // 나머지 요청은 다 허용

                // 로그인 페이지 설정, 단 POST는 직접 만들지 않음
                // login.html과 값을 맞춰야한다.
                http.formLogin()
                                .loginPage("/member/login") // 주소
                                .loginProcessingUrl("/member/loginaction") // action
                                .usernameParameter("uemail") // name값
                                .passwordParameter("upw") // name값
                                .defaultSuccessUrl("/home")
                                .permitAll();

                // 로그아웃 페이지 설정, url에 맞게 POST로 호출하면 됨.
                http.logout()
                                .logoutUrl("/member/logout")
                                .logoutSuccessUrl("/home")
                                // .logoutSuccessHandler(new MyLogoutSuccessHandler())
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .permitAll();

                // 접근 권한 불가 403
                http.exceptionHandling().accessDeniedPage("/page403");

                // h2-console을 사용하기 위해서
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();

                // rest controller 사용
                http.csrf().ignoringAntMatchers("/api/**");
        }
}
