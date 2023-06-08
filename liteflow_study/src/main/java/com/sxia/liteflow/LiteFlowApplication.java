package com.sxia.liteflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sxia.liteflow")
public class LiteFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiteFlowApplication.class, args);
    }
}
