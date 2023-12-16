package com.koreait.bnb.controller;

import com.koreait.bnb.domains.enums.ReserveState;
import com.koreait.bnb.domains.security.SecurityUser;
import com.koreait.bnb.domains.vo.ReservationVO;
import com.koreait.bnb.service.ReservationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    // 방 예약 버튼 눌렀을 시
    @PostMapping
    public boolean reserve_room(
            @AuthenticationPrincipal SecurityUser securityUser,
            @Validated ReservationVO reservationVO,
            BindingResult bindingResult
    ){
        log.info(reservationVO);
        if(bindingResult.hasErrors()){
            log.info("bindingResult에러!");
            return false;
        }
        if(securityUser == null){
            log.info("비 로그인 상태!");
            return false;
        }
        // 로그인되어있는 유저를 등록
        reservationVO.setUserId(securityUser.getUsername());
        // 해당 유저가 해당 방을 예약 시도 후, 성공 여부 반환
        return reservationService.reserve_room(reservationVO);
    }

    // 판매자가 예약번호 예약요청 승인 / 취소 선택
    @PutMapping("/{no}")
    public boolean update_room_state(
            @PathVariable int no,
            @RequestParam ReserveState state){
        ReserveState reserveState;
        switch (state){
            case ACCEPTED:
                reserveState = ReserveState.ACCEPTED;
                break;
            case CANCELED:
                reserveState = ReserveState.CANCELED;
                break;
            default:
                return false;
        }
        reservationService.update_room_state(no, reserveState);
        return true;
    }
}
