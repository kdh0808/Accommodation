package com.koreait.bnb.service.security;

import lombok.extern.log4j.Log4j2;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
@Service
public class CustomOAuth2LogoutService {

    private void create_template(String method, String FQDN, HttpHeaders httpHeaders, JSONObject body){
        RestTemplate restTemplate = new RestTemplate();
        //1-1. 응답 메세지를 받기 위한 HttpComponentsClientHttpRequestFactory 객체 생성 및 RestTemplate에 삽입
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(20000); // 커넥션 타임 아웃 20초
        factory.setReadTimeout(20000);  // Read 타임 아웃 20초
        HttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(50).build(); // 최대 커넥션 수 50
        factory.setHttpClient(httpClient);
        // RestTemplate에 삽입
        restTemplate.setRequestFactory(factory);
        //4. body 데이터와 HTTP의 결합
        HttpEntity<String> entity = new HttpEntity<>(body.toString(), httpHeaders);
        //5. URL과 METHOD 설정 및 6. HTTP 요청
        ResponseEntity<String> response;
        switch (method.toUpperCase()){
            case "GET":
            case "POST":
                response = restTemplate.postForEntity(FQDN, entity, String.class);
                break;
            default:
                response = restTemplate.postForEntity(FQDN, entity, String.class);
        }
        // POST 요청 결과 받아오기
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
    }

    public void kakao_logout(String accessToken){
        //2. HTTP 헤더 생성
        log.info("HEADER 생성");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        log.info("생성된 HEADER => " + httpHeaders);
        JSONObject body = new JSONObject();
        create_template("POST", "https://kapi.kakao.com/v1/user/logout", httpHeaders, body);
    }

}
