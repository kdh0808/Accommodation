package com.koreait.bnb.mapper;

import com.koreait.bnb.domains.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // 로그인 시 유저를 찾음
    UserVO get_user_by_id(String id);
    // 아이디 찾기 시 사용하는 유저 찾기
    UserVO get_user_by_email(String email);
    // 마이페이지에서 나의 유저 정보 가져오기
    UserVO get_user();

    // 회원가입
    void join_user(UserVO vo);

    // 유저 정보 수정
    void update_user(UserVO vo);
}






