package com.koreait.bnb.mapper;

import com.koreait.bnb.domains.dto.RoomDTO;
import com.koreait.bnb.domains.vo.RoomVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoomMapper {
    List<RoomDTO> get_all_rooms(
            @Param("sort") String sort,
            @Param("min") int min,
            @Param("max") int max
    );
    RoomDTO find_room_by_roomNo(int roomNo);
}
