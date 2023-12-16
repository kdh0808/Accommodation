package com.koreait.bnb.service.security;

import com.koreait.bnb.domains.enums.UserSocial;
import com.koreait.bnb.domains.security.SecurityUser;
import com.koreait.bnb.domains.vo.UserVO;
import com.koreait.bnb.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("로그인한 유저 객체: " + userRequest);

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> params = oAuth2User.getAttributes();
        String userEmail;
        UserSocial userSocial;

        // 로그인한 소셜 로그인에 따라 유저 정보 획득
        switch (clientName){
            case "kakao":
                userEmail = get_kakao_email(params);
                userSocial = UserSocial.KAKAO;
                break;
            default:
                userEmail = get_kakao_email(params);
                userSocial = UserSocial.NORMAL;
        }

        UserVO userVO = userMapper.get_user_by_id(userEmail);
        // 기존에 이메일을 등록한 유저가 있음
        if(userVO != null){
            // 기존 유저를 반환한다
            log.info("기존 유저입니다");
            SecurityUser securityUser = new SecurityUser(userVO, List.of(new SimpleGrantedAuthority(userVO.getRole().toString())));
            securityUser.setAttributes(params);
            securityUser.setAccessToken(userRequest.getAccessToken().getTokenValue());
            return securityUser;
        }

        log.info("유저가 등록되어 있지 않습니다");
        // 등록되어있는 유저가 없다 => 새로 유저를 만들어서 반환하되, 비밀번호는 없는 상태
        UserVO newUser = new UserVO();
        newUser.setId(userEmail);
        newUser.setPw("");
        newUser.setSocial(userSocial);
        newUser.setEmail(userEmail);
        SecurityUser securityUser = new SecurityUser(newUser, List.of(new SimpleGrantedAuthority("USER")));
        securityUser.setAttributes(params);
        securityUser.setAccessToken(userRequest.getAccessToken().getTokenValue());
        return securityUser;
    }


    private String get_kakao_email(Map<String, Object> params){
        log.info("KAKAO로 로그인 하였습니다..");
        Map<String, String> attributes = (Map<String, String>) params.get("kakao_account");
        String email = attributes.get("email");
        log.info("kakao에서 로그인한 유저 이메일: " + email);
        return email;
    }
}
