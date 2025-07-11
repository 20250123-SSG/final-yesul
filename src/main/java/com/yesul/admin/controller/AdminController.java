package com.yesul.admin.controller;

import com.yesul.admin.model.dto.AdminLoginLogDto;
import com.yesul.admin.service.AdminService;
import com.yesul.community.model.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboardPage(Model model) {
        int todayVisitor = adminService.getTodayVisitorCount();
        int realTimeUser = adminService.getRealTimeUserCount();

        List<PostResponseDto> popularPosts = adminService.getPopularPosts();

        // 시스템 모니터링
        model.addAttribute("todayVisitor", todayVisitor);
        model.addAttribute("realTimeUser", realTimeUser);

        // 인기순위
        model.addAttribute("popularPosts", popularPosts);
        //model.addAttribute("popularAlcohol", popularAlcohol);

        // 전체 주류 수, 전체 회원 수

        return "admin/dashboard";
    }

    @GetMapping("/dashboard/login-log")
    public String loginLogPage(Model model) {
        List<AdminLoginLogDto> adminLoginLog = adminService.getAllLoginLogs();
        model.addAttribute("adminLoginLog", adminLoginLog);
        return "admin/dashboard/login-log";
    }

}