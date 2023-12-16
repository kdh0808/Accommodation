package com.koreait.bnb.controller;

import com.koreait.bnb.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private SMSService service;

    @GetMapping("/")
    public String get_home(){
        return "home";
    }

}
