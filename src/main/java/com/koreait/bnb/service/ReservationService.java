package com.koreait.bnb.service;

import com.koreait.bnb.domains.dto.ReservationDTO;
import com.koreait.bnb.domains.enums.ReserveState;
import com.koreait.bnb.domains.vo.ReservationVO;
import com.koreait.bnb.domains.vo.UserVO;
import com.koreait.bnb.mapper.ReservationMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Log4j2
@Service
public class ReservationService {
    @Autowired
    private ReservationMapper reservationMapper;
    //해당 방의 승인 여부 변경 서비스
    public void update_room_state(int no, ReserveState state){
        reservationMapper.update_room_state(no, state);
    }

    // 사용자가 방을 예약하는 서비스
    public boolean reserve_room(ReservationVO reservationVO){
        // 사용자의 총 금액을 재 계산 ( 클라이언트단에서 계산한 것은 믿을 수 없는 정보기 때문 )
        LocalDate startDate = reservationVO.getStartDate();
        LocalDate endDate = reservationVO.getEndDate();
        // 박 수 계산
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        if(days < 1){ // 1박도 안되는 날을 예약했다
            log.info("날짜가 1일도 안됨: " + days);
            return false; //예약 불가!
        }
        // 해당 방이 존재하는지에 대한 여부 조회
        ReservationVO reservedVO = reservationMapper.reservation_room_possible_check(reservationVO);
        if(reservedVO != null){ // 이미 예약된 방이 존재함
            log.info("reservedVO가 존재: " + reservedVO);
            return false;
        }
        ////////// 예약 가능 상태라면 총 가격 계산
        int personCount = reservationVO.getPersonCount();
        int price = reservationVO.getPrice();
        reservationVO.setTotalPrice(price * personCount * (int)days);
        reservationVO.setDays((int)days);
        // 실제 예약
        reservationMapper.reservation_room(reservationVO);
        return true;
    }

    // 해당 사용자의 예약 정보 가져오기 서비스 (일반 유저)
    public List<ReservationDTO> get_reserve_info_by_user(UserVO userVO){
        return reservationMapper.get_reserve_info_by_user(userVO);
    }
    // 해당 사용자의 예약 정보 가져오기 서비스 (판매 유저)
    public List<ReservationDTO> get_reserve_request_info_of_user(UserVO userVO){
        return reservationMapper.get_reserve_request_info_of_user(userVO);
    }

    // 해당 사용자의 판매 정보 가져오기 서비스
    public List<ReservationDTO> get_sale_info_by_user(UserVO userVO){
        return reservationMapper.get_sale_info_by_user(userVO);
    }

}










