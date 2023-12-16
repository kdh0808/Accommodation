package com.koreait.bnb.domains.dto;

import com.koreait.bnb.domains.vo.RatingVO;
import com.koreait.bnb.domains.vo.RoomImagesVO;
import com.koreait.bnb.domains.vo.RoomVO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class RoomDTO {
    private int roomNo;
    private RoomVO roomVO;
    private List<RatingVO> ratingVO;
    private List<RoomImagesVO> roomImagesVO;

}
