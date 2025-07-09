package com.yesul.monitoring.controller;

import com.yesul.monitoring.model.dto.DashboardDto;
import com.yesul.monitoring.service.MonitoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class MonitoringController {

    private final MonitoringService monitoringService;

    @GetMapping("/dashboard")
    public String dashboardPage(Model model) {
        DashboardDto data = monitoringService.collectMonitoringData();
        model.addAttribute("dashboardData", data);
        return "admin/dashboard";
    }

}