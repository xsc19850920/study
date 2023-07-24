package com.sxia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EasyexcelApplication {
   public static ConfigurableApplicationContext ac;
    public static void main(String[] args) {
         ac = SpringApplication.run(EasyexcelApplication.class, args);
    }
}
