package com.koreait.bnb.domains.vo;

import com.koreait.bnb.domains.enums.RoomState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardVO {
    private int no;
    private String userId;
    private String title;
    private int price;
    private String text;
    private List<MultipartFile> images;
}
