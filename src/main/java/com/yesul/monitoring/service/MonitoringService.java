package com.yesul.monitoring.service;

import com.yesul.monitoring.model.dto.DashboardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MonitoringService {

    private final RedisTemplate<String, String> redisTemplate;

    public int getTodayVisitorCount() {
        String todayKey = "visitors:" + LocalDate.now();
        Long count = redisTemplate.opsForSet().size(todayKey);
        return count != null ? count.intValue() : 0;
    }
}
