package com.sxia.liteflow.controller;

import com.sxia.liteflow.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
@EnableScheduling
@Slf4j
public class ScheduledTask {
    @Autowired
    private RedisService   redisService;

    @Scheduled(cron = "5 * * * * ?")
    public void sendRecord() {
        String redisKey = "redis-stream-01";
        for(int i = 0; i< 10; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("a", "aa1".concat(String.valueOf(i)));
            map.put("b", "bb1".concat(String.valueOf(i)));
            map.put("c", "cc1".concat(String.valueOf(i)));
            map.put("time", String.valueOf(System.currentTimeMillis()));
            String result = redisService.addMap(redisKey, map);
            log.info("返回结果：{}", result);
        }
    }
}
