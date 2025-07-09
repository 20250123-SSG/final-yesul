package com.yesul.monitoring.service;

import com.yesul.monitoring.model.dto.DashboardDto;
import org.springframework.stereotype.Service;

@Service
public class MonitoringService {
    public DashboardDto collectMonitoringData() {
        DashboardDto dashboardDto = new DashboardDto();
        return dashboardDto;
    }
}
