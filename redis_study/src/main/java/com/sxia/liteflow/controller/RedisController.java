package com.sxia.liteflow.controller;

import com.sxia.liteflow.common.Response;
import com.sxia.liteflow.pojo.JsonParam;
import com.sxia.liteflow.pojo.User;
import com.sxia.liteflow.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/str/{id}")
    public ResponseEntity<Object> getStr(@PathVariable("id") String id) {
        return Response.success(redisService.get(id));
    }


    @PostMapping("/str")
    public ResponseEntity<Object> setStr(@RequestBody User user) {
        redisService.set(user.getId(), user.getName());
        return Response.success("ok");
    }

    /**
     * list 用法
     *
     * @param user
     * @return
     */
    @GetMapping("/list/{key}")
    public ResponseEntity<Object> getList(@PathVariable("key") String key) {
        List<Object> list = redisService.lRange(key, 0, 100);
        List<String> allList = new ArrayList<String>();
        for (Object o : list) {
            if (o instanceof java.util.List) {
                List<String> item = (List<String>) o;
                allList.addAll(item);
            }
        }
        return Response.success(allList);
    }

    @PostMapping("/list")
    public ResponseEntity<Object> setList(@RequestBody JsonParam jsonParam) {
        redisService.lPushAll(jsonParam.getKey(), jsonParam.getList());
        return Response.success("ok");
    }

    /**
     * map 的用法
     *
     * @param key
     * @param hashKey
     * @return
     */
    @GetMapping("/map/{key}:{hashKey}")
    public ResponseEntity<Object> getMap(@PathVariable("key") String key, @PathVariable("hashKey") String hashKey) {
        return Response.success(redisService.hGet(key, hashKey));
    }

    @PostMapping("/map")
    public ResponseEntity<Object> setMap(@RequestBody JsonParam jsonParam) {
//        redisService.hDel(jsonParam.getKey(),jsonParam.getHashKey());
        redisService.hSet(jsonParam.getKey(), jsonParam.getHashKey(), jsonParam.getHashValue());
        return Response.success("ok");
    }

    /**
     * 集合set用法
     *
     * @param key
     * @return
     */
    @GetMapping("/set/{key}")
    public ResponseEntity<Object> getSet(@PathVariable("key") String key) {
        return Response.success(redisService.sMembers(key));
    }

    @PostMapping("/set")
    public ResponseEntity<Object> setSet(@RequestBody JsonParam jsonParam) {
        redisService.sAdd(jsonParam.getKey(), jsonParam.getValues());
        return Response.success("ok");
    }
    /**
     * 排序集合的用法
     */
//    @GetMapping("/zset/{key}")
//    public ResponseEntity<Object> getZSet(@PathVariable("key") String key) {
//        return Response.success(redisService.(key));
//    }
//
//    @PostMapping("/set")
//    public ResponseEntity<Object> setSet(@RequestBody JsonParam jsonParam) {
//        redisService.sAdd(jsonParam.getKey(), jsonParam.getValues());
//        return Response.success("ok");
//    }
}
