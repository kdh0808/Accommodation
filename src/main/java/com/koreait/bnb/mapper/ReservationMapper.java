package com.koreait.bnb.mapper;

import com.koreait.bnb.domains.dto.ReservationDTO;
import com.koreait.bnb.domains.enums.ReserveState;
import com.koreait.bnb.domains.vo.ReservationVO;
import com.koreait.bnb.domains.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReservationMapper {
    // 방 예약 가능 체크
    ReservationVO reservation_room_possible_check(ReservationVO reservationVO);
    // 방 예약
    void reservation_room(ReservationVO reservationVO);
    // 해당 유저의 예약 정보 (일반 유저)
    List<ReservationDTO> get_reserve_info_by_user(UserVO userVO);
    // 해당 유저의 예약 정보 (판매 유저)
    List<ReservationDTO> get_reserve_request_info_of_user(UserVO userVO);
    // 해당 유저의 판매 정보
    List<ReservationDTO> get_sale_info_by_user(UserVO userVO);
    // 방의 예약 요청 상태
    void update_room_state(@Param("no") int no, @Param("state") ReserveState state);
}








