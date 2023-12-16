package com.koreait.bnb.service;

import com.koreait.bnb.domains.dto.RoomDTO;
import com.koreait.bnb.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.util.List;

@Service
public class RoomService {
    String IMAGE_FILE_PATH = "C:\\Users\\사람\\Desktop\\Web19 KDH\\files\\";

    @Autowired
    private RoomMapper roomMapper;

    public List<RoomDTO> get_all_rooms(String sort, int min, int max){
        return roomMapper.get_all_rooms(sort, min, max);
    }

    public RoomDTO find_room_by_roomNo(int roomNo){
        return roomMapper.find_room_by_roomNo(roomNo);
    }


    public ResponseEntity<Resource> get_room_image_file(String fileName) throws Exception{
        Resource resource = new FileSystemResource(IMAGE_FILE_PATH + fileName);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        return ResponseEntity.ok().headers(httpHeaders).body(resource);
    }
}
