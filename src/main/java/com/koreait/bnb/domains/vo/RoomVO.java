package com.koreait.bnb.domains.vo;

import com.koreait.bnb.domains.enums.RoomState;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class RoomVO {
    private int no;
    private String userId;
    private String title;
    private int price;
    private String text;
    private RoomState state;

}

