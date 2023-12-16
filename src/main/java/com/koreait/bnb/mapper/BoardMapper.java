package com.koreait.bnb.mapper;

import com.koreait.bnb.domains.vo.BoardVO;
import com.koreait.bnb.domains.vo.RoomImagesVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    void post_board(BoardVO boardVO);
    void post_images(List<RoomImagesVO> roomImagesVOS);



}
