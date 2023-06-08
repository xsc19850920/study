package com.sxia.liteflow.controller;

import com.sxia.liteflow.common.Response;
import com.sxia.liteflow.pojo.PubParam;
import com.sxia.liteflow.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisPubController {

    @Autowired
    private RedisService redisService;

    /**
     * 消息订阅
     * @param param
     * @return
     */
    @PostMapping("/pubMessage")
    public ResponseEntity<Object> pubMessage(@RequestBody PubParam param){
        // 执行主业务1
        redisService.convertAndSend(param.getChannel(), param.getMessage());
        return Response.success("ok");
    }

}