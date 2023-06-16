package com.sxia.controller;

import com.sxia.model.Book;
import com.sxia.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("/kafka/")
@Slf4j
public class KafkaController {
    @Resource
    private KafkaService kafkaService;
    @GetMapping("send")
    public void send(@RequestBody Book book) {
        // 可同时可同步及延迟反馈
        kafkaService.send(book);
    }
}
