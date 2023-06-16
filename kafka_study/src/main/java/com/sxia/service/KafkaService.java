package com.sxia.service;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;

@Service
@Slf4j
public class KafkaService {
    @Resource
    KafkaTemplate kafkaTemplate;

    public void send(Object obj) {
        String sendStr = JSONUtil.toJsonStr(obj);
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("test", sendStr);
        future.addCallback(result -> log.info("生产者成功发送消息到topic:{} partition:{}的消息:{}", result.getRecordMetadata().topic(), result.getRecordMetadata().partition(), sendStr),
                ex -> log.error("生产者发送消失败，原因：{}", ex.getMessage()));
    }
}
