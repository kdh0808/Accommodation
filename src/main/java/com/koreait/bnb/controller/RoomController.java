package com.koreait.bnb.controller;

import com.koreait.bnb.domains.dto.RoomDTO;
import com.koreait.bnb.domains.enums.ReserveState;
import com.koreait.bnb.domains.security.SecurityUser;
import com.koreait.bnb.domains.vo.ReservationVO;
import com.koreait.bnb.service.ReservationService;
import com.koreait.bnb.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    @Autowired private RoomService roomService;

    @GetMapping
    @ResponseBody
    public List<RoomDTO> get_all_rooms(
            @RequestParam(defaultValue = "all") String sort,
            @RequestParam(defaultValue = "-1") int min,
            @RequestParam(defaultValue = "-1") int max
    ){
        return roomService.get_all_rooms(sort, min, max);
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> get_room_image_file(
            @PathVariable("imageName") String imageName
    ) throws Exception {
        return roomService.get_room_image_file(imageName);
    }

    @GetMapping("/{roomNo}")
    public String room_main(
            @PathVariable("roomNo") int roomNo,
            Model model
    ){
        RoomDTO roomDTO = roomService.find_room_by_roomNo(roomNo);
        model.addAttribute("roomDTO",roomDTO);
        return "/rooms/main";
    }


}





