package com.yesul.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {  // 관리자 로그인, Dashboard, 공지사항

    @GetMapping("/login")
    public void loginPage(){}

    @GetMapping("/dashboard")
    public String dashboardPage(){
        return "admin/dashboard";
    }

    @GetMapping("/session-check")
    public String sessionCheck(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 기존 세션 가져오기
        if (session != null) {
            System.out.println("세션 ID: " + session.getId());

            Object context = session.getAttribute("SPRING_SECURITY_CONTEXT");
            System.out.println("SPRING_SECURITY_CONTEXT: " + context);
        }
        return "admin/dashboard";
    }
}