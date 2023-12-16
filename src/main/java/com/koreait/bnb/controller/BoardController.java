package com.koreait.bnb.controller;

import com.koreait.bnb.domains.dto.RoomDTO;
import com.koreait.bnb.domains.security.SecurityUser;
import com.koreait.bnb.domains.vo.BoardVO;
import com.koreait.bnb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping
    public void get_board(){

    }

    @PostMapping
    public String post_board(
            @AuthenticationPrincipal SecurityUser user,
            BoardVO boardVO
    ){
        // 게시물 작성 시도
        if(boardService.post_board(user, boardVO)){
            // 성공시
            return "redirect:/";
        }else{
            // 실패시
            return "redirect:board?error";
        }
    }

}
