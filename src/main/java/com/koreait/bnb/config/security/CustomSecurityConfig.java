package com.koreait.bnb.config.security;

import com.koreait.bnb.service.security.CustomUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log4j2
@Configuration
public class CustomSecurityConfig {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.oauth2Login()
                .loginPage("/user/login")
                .successHandler(new CustomSocialLoginSuccessHandler());
        http.formLogin()
                .loginPage("/user/login").defaultSuccessUrl("/")
                .and()
                .logout().logoutUrl("/user/logout").logoutSuccessUrl("/").invalidateHttpSession(true).permitAll()
                .and()
                .rememberMe().userDetailsService(customUserDetailsService)
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60 * 60)
                .and()
                .authorizeRequests()
                .mvcMatchers("/board").hasRole("SELLER")
                .mvcMatchers("/user/mypage/*").authenticated()
                .mvcMatchers("/user/mypage/sale").hasRole("SELLER")
                .mvcMatchers("/rooms/reservation").hasRole("USER")
                .anyRequest().permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

    //정적 자원들을 스프링 시큐리티 적용에서 제외시키겠다
    //예) /css/style.css 호출시
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (webSecurity) -> webSecurity.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }



}
