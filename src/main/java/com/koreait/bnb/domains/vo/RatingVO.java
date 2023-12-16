package com.koreait.bnb.domains.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class RatingVO {
    private String userID;
    private int roomNo;
    private int score;
}
