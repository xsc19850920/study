package com.sxia.kafka.listener;

import com.sxia.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class KafakaListener {
    @KafkaListener(topics = "test", groupId = "kafka-test", topicPartitions = {})
    public void processMessage(String content) {
        log.info("收到消息 -> " + content);
    }
}
