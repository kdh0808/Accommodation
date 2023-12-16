package com.koreait.bnb.domains.dto;

import com.koreait.bnb.domains.vo.ReservationVO;
import com.koreait.bnb.domains.vo.RoomImagesVO;
import com.koreait.bnb.domains.vo.RoomVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class ReservationDTO {
    private int no;
    private ReservationVO reservationVO;
    private RoomVO roomVO;
    private List<RoomImagesVO> roomImagesVOS;
}










