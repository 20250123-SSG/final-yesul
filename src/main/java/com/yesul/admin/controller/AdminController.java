package com.yesul.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {

    @GetMapping("/login")
    public void loginPage(){}

    @GetMapping("/dashboard")
    public String dashboardPage(){ return "admin/dashboard"; }

    // --------------- 레이아웃 -----------------
    @GetMapping("/board")
    public String boardPage(){ return "admin/board/list";}

    @GetMapping("/alcohol")
    public String alcoholPage(){ return "admin/alcohol/list";}

    @GetMapping("/sale")
    public String salePage(){ return "admin/sale/list";}

    @GetMapping("/userInfo")
    public String userInfoPage(){ return "admin/userInfo/list";}

    @GetMapping("/review")
    public String reviewPage(){ return "admin/review/list";}

    @GetMapping("/chat")
    public String chatPage(){ return "admin/chat/list";}

    // --------------- 관리 기능 ----------------

}
