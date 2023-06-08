package com.sxia.liteflow.controller;

import cn.hutool.json.JSONUtil;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LiteFlowController {
    @Resource
    private FlowExecutor flowExecutor;

    @GetMapping("/test")
    public String getStr() {
        //配置文件热刷新
        flowExecutor.reloadRule();
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
        Boolean  bln =  response.isSuccess();
        return bln.toString();
    }

}
