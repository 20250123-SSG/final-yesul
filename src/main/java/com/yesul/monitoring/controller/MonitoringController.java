package com.yesul.monitoring.controller;

import com.yesul.monitoring.service.MonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class MonitoringController {

    private final MonitoringService monitoringService;

    @ResponseBody
    @GetMapping("/dashboard")
    public String dashboardPage(Model model) {
        int todayVisitor = monitoringService.getTodayVisitorCount();
        int realTimeUser = monitoringService.getRealTimeUserCount();

        model.addAttribute("todayVisitor", todayVisitor);
        model.addAttribute("realTimeUser", realTimeUser);

        return "admin/dashboard";
    }

}