package com.koreait.bnb.domains.security;

import com.koreait.bnb.domains.vo.UserVO;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
public class SecurityUser extends User implements OAuth2User {
    private UserVO userVO;
    private Map<String, Object> attributes;
    private String accessToken;

    public SecurityUser(UserVO userVO, Collection<? extends GrantedAuthority> authorities) {
        super(userVO.getId(), userVO.getPw(), authorities);
        this.userVO = userVO;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return userVO.getId();
    }
}
