package com.yesul.admin.controller;

import com.yesul.admin.model.dto.AdminLoginLogDto;
import com.yesul.admin.service.AdminService;
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

        // 시스템 모니터링
        model.addAttribute("todayVisitor", todayVisitor);
        model.addAttribute("realTimeUser", realTimeUser);

        return "admin/dashboard";
    }

    @GetMapping("/dashboard/login-log")
    public String loginLogPage(Model model) {
        List<AdminLoginLogDto> adminLoginLog = adminService.getAllLoginLogs(); //형변환 / 뷰 어떻게 보여줄지
        model.addAttribute("adminLoginLog", adminLoginLog);
        return "admin/dashboard/login-log";
    }

}