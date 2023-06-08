package com.sxia.springbootcache.service;

import cn.hutool.json.JSONUtil;
import com.sxia.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class userServiceTest {
    @Resource
    UserService userService;
    @Test
    void getEntityById() {
        User user = userService.getEntityById(3);
        System.out.println(JSONUtil.toJsonStr(user));
    }

    @Test
    void deleteEntityById() {
        userService.deleteEntityById(3);
    }

    @Test
    void saveEntity() {
        User user = new User(3,"sxia");
         userService.saveEntity(user);
    }

    @Test
    void saveData() {
        userService.saveData(3,"sxia");
    }
}