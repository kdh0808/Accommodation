package com.koreait.bnb.service;

import com.koreait.bnb.domains.security.SecurityUser;
import com.koreait.bnb.domains.vo.UserVO;
import com.koreait.bnb.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Log4j2
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 유저 회원가입
    public void join_user(UserVO userVO){
        userVO.setPw(passwordEncoder.encode(userVO.getPw()));
        userMapper.join_user(userVO);
    }

    public void update_user(SecurityUser securityUser, UserVO userVO){
        // 기존 유저 정보와 사용자가 입력한 유저 정보를 매칭
        UserVO updateUserVO = set_update_user_information(securityUser.getUserVO(), userVO);
        log.info(updateUserVO);
        // 실제로 update
        userMapper.update_user(updateUserVO);
        // 로그인되어있는 userVO객체를 수정한 UserVO 객체로 수정
        securityUser.setUserVO(updateUserVO);
    }

    // 자신의 정보 수정 시 정보 체크
    private UserVO set_update_user_information(UserVO originalUserVO, UserVO userVO){
        // 사용자가 작성하지 않은 정보 setting
        userVO.setId(originalUserVO.getId());
        userVO.setSocial(originalUserVO.getSocial());
        userVO.setRole(originalUserVO.getRole());

        // 수정하지 않은 정보와 수정한 정보 판단
        String pw = userVO.getPw();
        String email = userVO.getEmail();
        String nickName = userVO.getNickName();
        String tel = userVO.getTel();
        LocalDate birth = userVO.getBirth();

        // 비밀번호를 수정하였다
        if(!pw.isBlank()) {
            // 비밀번호를 encoding 해서 setting
            String encodedPassword = passwordEncoder.encode(userVO.getPw());
            userVO.setPw(encodedPassword);
        }
        // 비밀번호를 수정하지 않았으면 기존 비밀번호 그대로
        else{
            userVO.setPw(originalUserVO.getPw());
        }
        ////////// 수정하지 않은 정보는 기존의 정보와 동일하게 유지
        // 이메일을 수정하지 않았다
        if(email.isBlank()){
            userVO.setEmail(originalUserVO.getEmail());
        }
        // 닉네임을 수정하지 않음
        if(nickName.isBlank()){
            userVO.setNickName(originalUserVO.getNickName());
        }
        // 이메일을 수정하지 않았다
        if(tel.isBlank()){
            userVO.setTel(originalUserVO.getTel());
        }
        // 이메일을 수정하지 않았다
        if(birth == null){
            userVO.setBirth(originalUserVO.getBirth());
        }

        return userVO;
    }
}
