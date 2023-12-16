package com.koreait.bnb;

import com.koreait.bnb.mapper.RoomMapper;
import com.koreait.bnb.service.SMSService;
import com.koreait.bnb.service.UserMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KoreaBnbApplicationTests {
    @Autowired
    UserMailService userMailService;


    @Test
    void contextLoads() throws Exception {
    }

}
