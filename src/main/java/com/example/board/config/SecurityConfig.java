package com.example.board.config;

import com.example.board.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        //static 디렉터리의 하위 파일 목록은 인증 무시(=항상 통과)
        web.ignoring().antMatchers("/css/**", "/js/**","/img/**","/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                //페이지 권한 설정
                .antMatchers("/", "/signup","/login").permitAll()
                .anyRequest().hasRole("MEMBER")
                .and()  //로그인 설정
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")//로그인 성공시에 이동할 url
                .usernameParameter("id")
                .passwordParameter("password")
                .and()  //로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")//로그아웃 성공시의 url
                .invalidateHttpSession(true)
                .and()  //403 예외처리 핸들링
                .exceptionHandling().accessDeniedPage("/denied");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
