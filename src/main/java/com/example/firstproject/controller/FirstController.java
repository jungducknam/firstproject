package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    // 앞서 만든 그리팅 템플릿 페이지를 리턴에 적어서 반환!
    @GetMapping("/hi") //URL 요청 연결 괄호안에 접속할 URL적음
    public String NiceToMeetYou(Model model){
        model.addAttribute("username","JungDuck");
        return "greetings";// templates/greetings.mustache -> 브라우저로 전송!
    }
    @GetMapping("/bye")
    public String SeeYouNext(Model model){
        model.addAttribute("username","cucucuk");
        return "goodbye";
    }
}
