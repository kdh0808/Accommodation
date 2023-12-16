package com.koreait.bnb.service;

import com.koreait.bnb.domains.security.SecurityUser;
import com.koreait.bnb.domains.vo.BoardVO;
import com.koreait.bnb.domains.vo.RoomImagesVO;
import com.koreait.bnb.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    String SAVE_PATH = "C:\\Users\\사람\\Desktop\\Web19 KDH\\files\\";

    @Autowired
    private BoardMapper boardMapper;

    // 게시물 작성하기
    @Transactional
    public boolean post_board(SecurityUser user, BoardVO boardVO){
        List<MultipartFile> files = boardVO.getImages();
        List<RoomImagesVO> roomImagesVOS;
        if(!check_files(files)) return false;
        try {
            roomImagesVOS = save_files(files);
        }catch (IOException e){
            return false;
        }
        // 게시하는 유저 설정
        boardVO.setUserId(user.getUsername());
        // DB에 저장 - 방 insert
        boardMapper.post_board(boardVO);
        // 방금 insert 했던 방의 이미지들을 insert
        boardMapper.post_images(roomImagesVOS);

        return true;
    }

    private boolean check_files(List<MultipartFile> files){
        List<String> fileNames = new ArrayList<>();

        for (MultipartFile file: files) {
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            if(!contentType.startsWith("image/")){
                return false;
            }
        }

        return true;
    }

    private List<RoomImagesVO> save_files(List<MultipartFile> files) throws IOException {

        List<RoomImagesVO> imagesVOS = new ArrayList<>();
        for (MultipartFile file: files) {
            // 파일에서 원본 이미지 파일명을 토대로 새 이름을 결정
            String originalFileName = file.getOriginalFilename();
            String saveFileName = UUID.randomUUID() + "_" + originalFileName;
            File saveFile = new File(SAVE_PATH, saveFileName);
            // 로컬에 파일 저장
            file.transferTo(saveFile);
            // DB에 저장할 파일 객체 생성 후 이미지명 설정
            RoomImagesVO imagesVO = new RoomImagesVO();
            imagesVO.setRoomImage(saveFileName);
            // 리스트로 넣음
            imagesVOS.add(imagesVO);
        }
        return imagesVOS;
    }
}









