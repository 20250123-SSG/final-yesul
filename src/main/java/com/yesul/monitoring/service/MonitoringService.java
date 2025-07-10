package com.yesul.monitoring.service;

import com.yesul.monitoring.model.dto.DashboardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MonitoringService {

    private final RedisTemplate<String, String> redisTemplate;

    public int getTodayVisitorCount() {
        String todayKey = "visitors:" + LocalDate.now();
        Long count = redisTemplate.opsForSet().size(todayKey);
        return count != null ? count.intValue() : 0;
    }

    public int getRealTimeUserCount() {
        ScanOptions options = ScanOptions.scanOptions() //SCAN 설정
                .match("online-users:*")
                .count(1000)
                .build();

        Cursor<byte[]> cursor = (Cursor<byte[]>) redisTemplate.executeWithStickyConnection(
                connection -> connection.scan(options)
        );

        int count = 0;
        while (cursor != null && cursor.hasNext()) {
            cursor.next();
            count++;
        }

        return count;
    }
}
