package com.koreait.bnb.config.security;

import com.koreait.bnb.domains.enums.UserSocial;
import com.koreait.bnb.domains.security.SecurityUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("유저의 Social login이 성공했습니다.");
        log.info("로그인한 유저 =>" + authentication.getPrincipal());
        // 로그인한 유저의 정보를 가져온다
        SecurityUser securityUser =(SecurityUser) authentication.getPrincipal();

        // 로그인한 유저가  처음 등록한 경우 추가적인 정보가 필요하다
        if(securityUser.getUserVO().getPw().equals("")){
            response.sendRedirect("/user/moreInfo"); // 비번 등 다른 세부 정보 등록 페이지로 이동
            return;
        }
        // 기존에 가입되어 있는 사용자라면
        response.sendRedirect("/");
    }
}
